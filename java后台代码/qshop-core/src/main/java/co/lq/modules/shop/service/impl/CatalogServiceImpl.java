package co.lq.modules.shop.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import co.lq.modules.shop.domain.Catalog;
import co.lq.modules.shop.repository.CatalogRepository;
import co.lq.modules.shop.service.CatalogService;
import co.lq.modules.shop.service.dto.CatalogDTO;
import co.lq.modules.shop.service.dto.CatalogQueryCriteria;
import co.lq.modules.shop.service.mapper.CatalogMapper;
import co.lq.utils.FileUtil;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

/**
 * @author billy
 * @date 2020-03-10
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository catalogRepository;

    private final CatalogMapper     catalogMapper;

    public CatalogServiceImpl(CatalogRepository catalogRepository, CatalogMapper catalogMapper) {
        this.catalogRepository = catalogRepository;
        this.catalogMapper = catalogMapper;
    }

    @Override
    public Map<String, Object> queryAll(CatalogQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<Catalog> page = catalogRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(catalogMapper::toDto));
    }

    @Override
    public List<CatalogDTO> queryAll(CatalogQueryCriteria criteria) {
        criteria.setDeleted(0);
        return catalogMapper.toDto(catalogRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public CatalogDTO findById(Long id) {
        Catalog catalog = catalogRepository.findById(id).orElseGet(Catalog::new);
        ValidationUtil.isNull(catalog.getId(), "Catalog", "id", id);
        CatalogDTO catalogDTO = catalogMapper.toDto(catalog);
        List<Long> catalogIdList = new ArrayList<>();
        while (catalog.getPid() > 0) {
            catalogIdList.add(catalog.getPid());
            Optional<Catalog> optionalCatalog = catalogRepository.findById(catalog.getPid());
            catalog = optionalCatalog.get();
        }
        Collections.reverse(catalogIdList);
        catalogDTO.setCatalogIdList(catalogIdList);

        return catalogDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CatalogDTO create(Catalog resources) {
        resources.setDeleted(0);
        resources.setCreateTime(new Date());
        resources.setModifyTime(new Date());
        if (resources.getCatalogIdList() != null && resources.getCatalogIdList().size() > 0) {
            resources.setPid(resources.getCatalogIdList().get(0));
        } else {
            resources.setPid(0L);
        }
        return catalogMapper.toDto(catalogRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Catalog resources) {
        Catalog catalog = catalogRepository.findById(resources.getId()).orElseGet(Catalog::new);
        ValidationUtil.isNull(catalog.getId(), "Catalog", "id", resources.getId());
        if (resources.getCatalogIdList() != null && resources.getCatalogIdList().size() > 0) {
            resources.setPid(resources.getCatalogIdList().get(0));
        }
        catalog.copy(resources);
        catalog.setModifyTime(new Date());
        catalogRepository.save(catalog);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            Catalog catalog = catalogRepository.findById(id).orElseGet(Catalog::new);
            catalog.setModifyTime(new Date());
            catalog.setDeleted(1);
            catalogRepository.save(catalog);
        }
    }

    @Override
    public void download(List<CatalogDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CatalogDTO qshopCatalog : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("类目名称", qshopCatalog.getName());
            map.put("类目关键字，以JSON数组格式", qshopCatalog.getKeywords());
            map.put("类目广告语介绍", qshopCatalog.getDescs());
            map.put("父类目ID", qshopCatalog.getPid());
            map.put("类目图标", qshopCatalog.getIconUrl());
            map.put("类目图片", qshopCatalog.getPicUrl());
            map.put(" level", qshopCatalog.getLevel());
            map.put("排序", qshopCatalog.getSortOrder());
            map.put("创建时间", qshopCatalog.getCreateTime());
            map.put("更新时间", qshopCatalog.getModifyTime());
            map.put("逻辑删除", qshopCatalog.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public Object buildTree(List<CatalogDTO> catalogDTOS) {
        Set<CatalogDTO> trees = new LinkedHashSet<>();
        Set<CatalogDTO> cates = new LinkedHashSet<>();
        List<String> deptNames = catalogDTOS.stream().map(CatalogDTO::getName).collect(Collectors.toList());

        Boolean isChild;
        for (CatalogDTO deptDTO : catalogDTOS) {
            isChild = false;
            if ("0".equals(deptDTO.getPid().toString())) {
                trees.add(deptDTO);
            }
            for (CatalogDTO it : catalogDTOS) {
                if (it.getPid().equals(deptDTO.getId())) {
                    isChild = true;
                    if (deptDTO.getChildren() == null) {
                        deptDTO.setChildren(new ArrayList<CatalogDTO>());
                    }
                    deptDTO.getChildren().add(it);
                }
            }
            if (isChild)
                cates.add(deptDTO);
            else if (!deptNames.contains(catalogRepository.findNameById(deptDTO.getPid())))
                cates.add(deptDTO);
        }

        if (CollectionUtils.isEmpty(trees)) {
            trees = cates;
        }

        Integer totalElements = CollectionUtils.isEmpty(trees) ? catalogDTOS.size() : trees.size();

        Map map = new HashMap();
        map.put("totalElements", totalElements);
        map.put("content", CollectionUtils.isEmpty(trees) ? catalogDTOS : trees);
        return map;
    }
}
