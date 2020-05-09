package co.lq.modules.shop.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.shop.domain.CategoryAttr;
import co.lq.modules.shop.repository.CategoryAttrRepository;
import co.lq.modules.shop.service.CategoryAttrService;
import co.lq.modules.shop.service.dto.CategoryAttrDTO;
import co.lq.modules.shop.service.dto.CategoryAttrQueryCriteria;
import co.lq.modules.shop.service.mapper.CategoryAttrMapper;
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
//@CacheConfig(cacheNames = "qshopStoreCategoryAttr")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CategoryAttrServiceImpl implements CategoryAttrService {

    private final CategoryAttrRepository categoryAttrRepository;

    private final CategoryAttrMapper     categoryAttrMapper;

    public CategoryAttrServiceImpl(CategoryAttrRepository categoryAttrRepository,
                                   CategoryAttrMapper categoryAttrMapper) {
        this.categoryAttrRepository = categoryAttrRepository;
        this.categoryAttrMapper = categoryAttrMapper;
    }

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(CategoryAttrQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<CategoryAttr> page = categoryAttrRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(categoryAttrMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<CategoryAttrDTO> queryAll(CategoryAttrQueryCriteria criteria) {
        criteria.setDeleted(0);
        return categoryAttrMapper.toDto(categoryAttrRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public CategoryAttrDTO findById(Long id) {
        CategoryAttr categoryAttr = categoryAttrRepository.findById(id).orElseGet(CategoryAttr::new);
        ValidationUtil.isNull(categoryAttr.getId(), "StoreCategoryAttr", "id", id);
        return categoryAttrMapper.toDto(categoryAttr);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public CategoryAttrDTO create(CategoryAttr resources) {
        resources.setDeleted(0);
        resources.setAddTime(new Date());
        resources.setModifyTime(new Date());
        return categoryAttrMapper.toDto(categoryAttrRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(CategoryAttr resources) {
        CategoryAttr storeCategoryAttr = categoryAttrRepository.findById(resources.getId())
                .orElseGet(CategoryAttr::new);
        ValidationUtil.isNull(storeCategoryAttr.getId(), "CategoryAttr", "id", resources.getId());
        resources.setModifyTime(new Date());
        storeCategoryAttr.copy(resources);
        categoryAttrRepository.save(storeCategoryAttr);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            CategoryAttr storeCategoryAttr = categoryAttrRepository.findById(id).orElseGet(CategoryAttr::new);
            storeCategoryAttr.setDeleted(1);
            storeCategoryAttr.setModifyTime(new Date());
            categoryAttrRepository.save(storeCategoryAttr);
        }
    }

    @Override
    public void download(List<CategoryAttrDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CategoryAttrDTO categoryAttr : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("分类id", categoryAttr.getCategory());
            map.put("属性名称", categoryAttr.getAttrName());
            map.put("属性数量", categoryAttr.getAttrValue());
            map.put("是否显示", categoryAttr.getIsShow());
            map.put("添加时间", categoryAttr.getAddTime());
            map.put("更新时间", categoryAttr.getModifyTime());
            map.put("逻辑删除", categoryAttr.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
