package co.lq.modules.shop.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.shop.domain.Catalog;
import co.lq.modules.shop.domain.CatalogAttr;
import co.lq.modules.shop.repository.CatalogAttrRepository;
import co.lq.modules.shop.repository.CatalogRepository;
import co.lq.modules.shop.service.CatalogAttrService;
import co.lq.modules.shop.service.dto.CatalogAttrDTO;
import co.lq.modules.shop.service.dto.CatalogAttrQueryCriteria;
import co.lq.modules.shop.service.mapper.CatalogAttrMapper;
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
public class CatalogAttrServiceImpl implements CatalogAttrService {

    private final CatalogAttrRepository catalogAttrRepository;
    private final CatalogRepository     catalogRepository;

    private final CatalogAttrMapper     catalogAttrMapper;

    public CatalogAttrServiceImpl(CatalogAttrRepository catalogAttrRepository, CatalogRepository catalogRepository,
                                  CatalogAttrMapper catalogAttrMapper) {
        this.catalogAttrRepository = catalogAttrRepository;
        this.catalogRepository = catalogRepository;
        this.catalogAttrMapper = catalogAttrMapper;
    }

    @Override
    public Map<String, Object> queryAll(CatalogAttrQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<CatalogAttr> page = catalogAttrRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(catalogAttrMapper::toDto));
    }

    @Override
    public List<CatalogAttrDTO> queryAll(CatalogAttrQueryCriteria criteria) {
        criteria.setDeleted(0);
        return catalogAttrMapper.toDto(catalogAttrRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public CatalogAttrDTO findById(Long id) {
        CatalogAttr catalogAttr = catalogAttrRepository.findById(id).orElseGet(CatalogAttr::new);
        ValidationUtil.isNull(catalogAttr.getId(), "CatalogAttr", "id", id);
        CatalogAttrDTO catalogAttrDTO = catalogAttrMapper.toDto(catalogAttr);
        if (catalogAttrDTO.getCatalogId() != null) {
            Optional<Catalog> optionalCatalog = catalogRepository.findById(catalogAttrDTO.getCatalogId());
            Catalog catalog = optionalCatalog.get();
            List<Long> catalogList = new ArrayList<>();
            catalogList.add(catalog.getId());
            while (catalog.getPid() > 0) {
                optionalCatalog = catalogRepository.findById(catalog.getPid());
                catalog = optionalCatalog.get();
                catalogList.add(catalog.getId());
            }
            Collections.reverse(catalogList);
            catalogAttrDTO.setCatalogList(catalogList);
        }
        return catalogAttrDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CatalogAttrDTO create(CatalogAttr resources) {
        resources.setDeleted(0);
        resources.setAddTime(new Date());
        resources.setModifyTime(new Date());
        return catalogAttrMapper.toDto(catalogAttrRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CatalogAttr resources) {
        CatalogAttr catalogAttr = catalogAttrRepository.findById(resources.getId()).orElseGet(CatalogAttr::new);
        ValidationUtil.isNull(catalogAttr.getId(), "CatalogAttr", "id", resources.getId());
        resources.setModifyTime(new Date());
        catalogAttr.copy(resources);
        catalogAttrRepository.save(catalogAttr);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            CatalogAttr catalogAttr = catalogAttrRepository.findById(id).orElseGet(CatalogAttr::new);
            catalogAttr.setModifyTime(new Date());
            catalogAttr.setDeleted(1);
            catalogAttrRepository.save(catalogAttr);
        }
    }

    @Override
    public void download(List<CatalogAttrDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CatalogAttrDTO qshopCatalogAttr : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(" attrName", qshopCatalogAttr.getAttrName());
            map.put("属性数量", qshopCatalogAttr.getInputList());
            map.put("是否规格", qshopCatalogAttr.getIsSpec());
            map.put(" pic", qshopCatalogAttr.getPic());
            map.put("是否显示", qshopCatalogAttr.getIsShow());
            map.put("添加时间", qshopCatalogAttr.getAddTime());
            map.put("更新时间", qshopCatalogAttr.getModifyTime());
            map.put("逻辑删除", qshopCatalogAttr.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
