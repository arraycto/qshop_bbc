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

import co.lq.modules.shop.domain.CmsSubject;
import co.lq.modules.shop.repository.CmsSubjectRepository;
import co.lq.modules.shop.service.CmsSubjectService;
import co.lq.modules.shop.service.dto.CmsSubjectDTO;
import co.lq.modules.shop.service.dto.CmsSubjectQueryCriteria;
import co.lq.modules.shop.service.mapper.CmsSubjectMapper;
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
public class CmsSubjectServiceImpl implements CmsSubjectService {

    private final CmsSubjectRepository cmsSubjectRepository;

    private final CmsSubjectMapper     cmsSubjectMapper;

    public CmsSubjectServiceImpl(CmsSubjectRepository cmsSubjectRepository, CmsSubjectMapper cmsSubjectMapper) {
        this.cmsSubjectRepository = cmsSubjectRepository;
        this.cmsSubjectMapper = cmsSubjectMapper;
    }

    @Override
    public Map<String, Object> queryAll(CmsSubjectQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleleted(0);
        Page<CmsSubject> page = cmsSubjectRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(cmsSubjectMapper::toDto));
    }

    @Override
    public List<CmsSubjectDTO> queryAll(CmsSubjectQueryCriteria criteria) {
        criteria.setDeleleted(0);
        return cmsSubjectMapper.toDto(cmsSubjectRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public CmsSubjectDTO findById(Long id) {
        CmsSubject cmsSubject = cmsSubjectRepository.findById(id).orElseGet(CmsSubject::new);
        ValidationUtil.isNull(cmsSubject.getId(), "CmsSubject", "id", id);
        return cmsSubjectMapper.toDto(cmsSubject);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CmsSubjectDTO create(CmsSubject resources) {
        resources.setDeleted(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setAddTime(timestamp);
        resources.setModifyTime(timestamp);
        return cmsSubjectMapper.toDto(cmsSubjectRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CmsSubject resources) {
        CmsSubject cmsSubject = cmsSubjectRepository.findById(resources.getId()).orElseGet(CmsSubject::new);
        ValidationUtil.isNull(cmsSubject.getId(), "CmsSubject", "id", resources.getId());
        cmsSubject.copy(resources);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        cmsSubject.setModifyTime(timestamp);
        cmsSubjectRepository.save(cmsSubject);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            CmsSubject cmsSubject = cmsSubjectRepository.findById(id).orElseGet(CmsSubject::new);
            ValidationUtil.isNull(cmsSubject.getId(), "CmsSubject", "id", id);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            cmsSubject.setModifyTime(timestamp);
            cmsSubject.setDeleted(1);
            cmsSubjectRepository.save(cmsSubject);
        }
    }

    @Override
    public void download(List<CmsSubjectDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CmsSubjectDTO cmsSubject : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("分类", cmsSubject.getCategoryId());
            map.put("标题", cmsSubject.getTitle());
            map.put("专题主图", cmsSubject.getPic());
            map.put("关联产品数量", cmsSubject.getProductCount());
            map.put("推荐", cmsSubject.getRecommendStatus());
            map.put("收藏量", cmsSubject.getCollectCount());
            map.put("点击量", cmsSubject.getReadCount());
            map.put("评论量", cmsSubject.getCommentCount());
            map.put("画册图片用逗号分割", cmsSubject.getAlbumPics());
            map.put("描述", cmsSubject.getDescription());
            map.put("显示状态：0->不显示；1->显示", cmsSubject.getShowStatus());
            map.put("内容", cmsSubject.getContent());
            map.put("转发数", cmsSubject.getForwardCount());
            map.put("专题分类名称", cmsSubject.getCategoryName());
            map.put(" memberId", cmsSubject.getMemberId());
            map.put("打赏", cmsSubject.getReward());
            map.put(" areaName", cmsSubject.getAreaName());
            map.put(" schoolName", cmsSubject.getSchoolName());
            map.put(" memberName", cmsSubject.getMemberName());
            map.put(" videoSrc", cmsSubject.getVideoSrc());
            map.put(" type", cmsSubject.getType());
            map.put("添加时间", cmsSubject.getAddTime());
            map.put("更新时间", cmsSubject.getModifyTime());
            map.put("逻辑删除", cmsSubject.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
