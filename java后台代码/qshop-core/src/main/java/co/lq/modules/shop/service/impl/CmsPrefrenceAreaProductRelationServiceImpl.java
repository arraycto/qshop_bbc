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

import co.lq.modules.shop.domain.CmsPrefrenceAreaProductRelation;
import co.lq.modules.shop.repository.CmsPrefrenceAreaProductRelationRepository;
import co.lq.modules.shop.service.CmsPrefrenceAreaProductRelationService;
import co.lq.modules.shop.service.dto.CmsPrefrenceAreaProductRelationDTO;
import co.lq.modules.shop.service.dto.CmsPrefrenceAreaProductRelationQueryCriteria;
import co.lq.modules.shop.service.mapper.CmsPrefrenceAreaProductRelationMapper;
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
 * @date 2020-04-11
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CmsPrefrenceAreaProductRelationServiceImpl implements CmsPrefrenceAreaProductRelationService {

    private final CmsPrefrenceAreaProductRelationRepository cmsPrefrenceAreaProductRelationRepository;

    private final CmsPrefrenceAreaProductRelationMapper     cmsPrefrenceAreaProductRelationMapper;

    public CmsPrefrenceAreaProductRelationServiceImpl(CmsPrefrenceAreaProductRelationRepository cmsPrefrenceAreaProductRelationRepository,
                                                      CmsPrefrenceAreaProductRelationMapper cmsPrefrenceAreaProductRelationMapper) {
        this.cmsPrefrenceAreaProductRelationRepository = cmsPrefrenceAreaProductRelationRepository;
        this.cmsPrefrenceAreaProductRelationMapper = cmsPrefrenceAreaProductRelationMapper;
    }

    @Override
    public Map<String, Object> queryAll(CmsPrefrenceAreaProductRelationQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<CmsPrefrenceAreaProductRelation> page = cmsPrefrenceAreaProductRelationRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(cmsPrefrenceAreaProductRelationMapper::toDto));
    }

    @Override
    public List<CmsPrefrenceAreaProductRelationDTO> queryAll(CmsPrefrenceAreaProductRelationQueryCriteria criteria) {
        criteria.setDeleted(0);
        return cmsPrefrenceAreaProductRelationMapper.toDto(cmsPrefrenceAreaProductRelationRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public CmsPrefrenceAreaProductRelationDTO findById(Long id) {
        CmsPrefrenceAreaProductRelation cmsPrefrenceAreaProductRelation = cmsPrefrenceAreaProductRelationRepository
                .findById(id)
                .orElseGet(CmsPrefrenceAreaProductRelation::new);
        ValidationUtil.isNull(cmsPrefrenceAreaProductRelation.getId(), "CmsPrefrenceAreaProductRelation", "id", id);
        return cmsPrefrenceAreaProductRelationMapper.toDto(cmsPrefrenceAreaProductRelation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CmsPrefrenceAreaProductRelationDTO create(CmsPrefrenceAreaProductRelation resources) {
        resources.setDeleted(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setAddTime(timestamp);
        resources.setModifyTime(timestamp);
        return cmsPrefrenceAreaProductRelationMapper.toDto(cmsPrefrenceAreaProductRelationRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CmsPrefrenceAreaProductRelation resources) {
        CmsPrefrenceAreaProductRelation cmsPrefrenceAreaProductRelation = cmsPrefrenceAreaProductRelationRepository
                .findById(resources.getId())
                .orElseGet(CmsPrefrenceAreaProductRelation::new);
        ValidationUtil.isNull(cmsPrefrenceAreaProductRelation.getId(), "CmsPrefrenceAreaProductRelation", "id",
                resources.getId());
        cmsPrefrenceAreaProductRelation.copy(resources);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        cmsPrefrenceAreaProductRelation.setModifyTime(timestamp);
        cmsPrefrenceAreaProductRelationRepository.save(cmsPrefrenceAreaProductRelation);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            CmsPrefrenceAreaProductRelation cmsPrefrenceAreaProductRelation = cmsPrefrenceAreaProductRelationRepository
                    .findById(id)
                    .orElseGet(CmsPrefrenceAreaProductRelation::new);
            ValidationUtil.isNull(cmsPrefrenceAreaProductRelation.getId(), "CmsPrefrenceAreaProductRelation", "id", id);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            cmsPrefrenceAreaProductRelation.setModifyTime(timestamp);
            cmsPrefrenceAreaProductRelation.setDeleted(1);
            cmsPrefrenceAreaProductRelationRepository.save(cmsPrefrenceAreaProductRelation);
        }
    }

    @Override
    public void download(List<CmsPrefrenceAreaProductRelationDTO> all, HttpServletResponse response)
            throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CmsPrefrenceAreaProductRelationDTO cmsPrefrenceAreaProductRelation : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(" prefrenceAreaId", cmsPrefrenceAreaProductRelation.getPrefrenceAreaId());
            map.put(" productId", cmsPrefrenceAreaProductRelation.getProductId());
            map.put("添加时间", cmsPrefrenceAreaProductRelation.getAddTime());
            map.put("更新时间", cmsPrefrenceAreaProductRelation.getModifyTime());
            map.put("逻辑删除", cmsPrefrenceAreaProductRelation.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
