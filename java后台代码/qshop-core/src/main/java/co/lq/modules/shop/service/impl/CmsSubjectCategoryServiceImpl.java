package co.lq.modules.shop.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.shop.domain.CmsSubjectCategory;
import co.lq.modules.shop.repository.CmsSubjectCategoryRepository;
import co.lq.modules.shop.service.CmsSubjectCategoryService;
import co.lq.modules.shop.service.dto.CmsSubjectCategoryDTO;
import co.lq.modules.shop.service.dto.CmsSubjectCategoryQueryCriteria;
import co.lq.modules.shop.service.mapper.CmsSubjectCategoryMapper;
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
 * @date 2020-03-27
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CmsSubjectCategoryServiceImpl implements CmsSubjectCategoryService {

    private final CmsSubjectCategoryRepository cmsSubjectCategoryRepository;

    private final CmsSubjectCategoryMapper     cmsSubjectCategoryMapper;

    public CmsSubjectCategoryServiceImpl(CmsSubjectCategoryRepository cmsSubjectCategoryRepository,
                                         CmsSubjectCategoryMapper cmsSubjectCategoryMapper) {
        this.cmsSubjectCategoryRepository = cmsSubjectCategoryRepository;
        this.cmsSubjectCategoryMapper = cmsSubjectCategoryMapper;
    }

    @Override
    public Map<String, Object> queryAll(CmsSubjectCategoryQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<CmsSubjectCategory> page = cmsSubjectCategoryRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(cmsSubjectCategoryMapper::toDto));
    }

    @Override
    public List<CmsSubjectCategoryDTO> queryAll(CmsSubjectCategoryQueryCriteria criteria) {
        criteria.setDeleted(0);
        return cmsSubjectCategoryMapper.toDto(cmsSubjectCategoryRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public CmsSubjectCategoryDTO findById(Long id) {
        CmsSubjectCategory cmsSubjectCategory = cmsSubjectCategoryRepository.findById(id)
                .orElseGet(CmsSubjectCategory::new);
        ValidationUtil.isNull(cmsSubjectCategory.getId(), "CmsSubjectCategory", "id", id);
        return cmsSubjectCategoryMapper.toDto(cmsSubjectCategory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CmsSubjectCategoryDTO create(CmsSubjectCategory resources) {
        resources.setDeleted(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setAddTime(timestamp);
        resources.setModifyTime(timestamp);
        return cmsSubjectCategoryMapper.toDto(cmsSubjectCategoryRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CmsSubjectCategory resources) {
        CmsSubjectCategory cmsSubjectCategory = cmsSubjectCategoryRepository.findById(resources.getId())
                .orElseGet(CmsSubjectCategory::new);
        ValidationUtil.isNull(cmsSubjectCategory.getId(), "CmsSubjectCategory", "id", resources.getId());
        cmsSubjectCategory.copy(resources);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        cmsSubjectCategory.setModifyTime(timestamp);
        cmsSubjectCategoryRepository.save(cmsSubjectCategory);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            CmsSubjectCategory cmsSubjectCategory = cmsSubjectCategoryRepository.findById(id)
                    .orElseGet(CmsSubjectCategory::new);
            ValidationUtil.isNull(cmsSubjectCategory.getId(), "CmsSubjectCategory", "id", id);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            cmsSubjectCategory.setModifyTime(timestamp);
            cmsSubjectCategory.setDeleted(1);
            cmsSubjectCategoryRepository.save(cmsSubjectCategory);
        }
    }

    @Override
    public void download(List<CmsSubjectCategoryDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CmsSubjectCategoryDTO cmsSubjectCategory : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("标题", cmsSubjectCategory.getName());
            map.put("分类图标", cmsSubjectCategory.getIcon());
            map.put("专题数量", cmsSubjectCategory.getSubjectCount());
            map.put("状态", cmsSubjectCategory.getShowStatus());
            map.put("排序", cmsSubjectCategory.getSort());
            map.put("添加时间", cmsSubjectCategory.getAddTime());
            map.put("更新时间", cmsSubjectCategory.getModifyTime());
            map.put("逻辑删除", cmsSubjectCategory.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
