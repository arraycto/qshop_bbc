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

import co.lq.modules.shop.domain.CmsSubjectComment;
import co.lq.modules.shop.repository.CmsSubjectCommentRepository;
import co.lq.modules.shop.service.CmsSubjectCommentService;
import co.lq.modules.shop.service.dto.CmsSubjectCommentDTO;
import co.lq.modules.shop.service.dto.CmsSubjectCommentQueryCriteria;
import co.lq.modules.shop.service.mapper.CmsSubjectCommentMapper;
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
public class CmsSubjectCommentServiceImpl implements CmsSubjectCommentService {

    private final CmsSubjectCommentRepository cmsSubjectCommentRepository;

    private final CmsSubjectCommentMapper     cmsSubjectCommentMapper;

    public CmsSubjectCommentServiceImpl(CmsSubjectCommentRepository cmsSubjectCommentRepository,
                                        CmsSubjectCommentMapper cmsSubjectCommentMapper) {
        this.cmsSubjectCommentRepository = cmsSubjectCommentRepository;
        this.cmsSubjectCommentMapper = cmsSubjectCommentMapper;
    }

    @Override
    public Map<String, Object> queryAll(CmsSubjectCommentQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<CmsSubjectComment> page = cmsSubjectCommentRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(cmsSubjectCommentMapper::toDto));
    }

    @Override
    public List<CmsSubjectCommentDTO> queryAll(CmsSubjectCommentQueryCriteria criteria) {
        criteria.setDeleted(0);
        return cmsSubjectCommentMapper.toDto(cmsSubjectCommentRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public CmsSubjectCommentDTO findById(Long id) {
        CmsSubjectComment cmsSubjectComment = cmsSubjectCommentRepository.findById(id)
                .orElseGet(CmsSubjectComment::new);
        ValidationUtil.isNull(cmsSubjectComment.getId(), "CmsSubjectComment", "id", id);
        return cmsSubjectCommentMapper.toDto(cmsSubjectComment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CmsSubjectCommentDTO create(CmsSubjectComment resources) {
        resources.setDeleted(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setAddTime(timestamp);
        resources.setModifyTime(timestamp);
        return cmsSubjectCommentMapper.toDto(cmsSubjectCommentRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CmsSubjectComment resources) {
        CmsSubjectComment cmsSubjectComment = cmsSubjectCommentRepository.findById(resources.getId())
                .orElseGet(CmsSubjectComment::new);
        ValidationUtil.isNull(cmsSubjectComment.getId(), "CmsSubjectComment", "id", resources.getId());
        cmsSubjectComment.copy(resources);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        cmsSubjectComment.setModifyTime(timestamp);
        cmsSubjectCommentRepository.save(cmsSubjectComment);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            CmsSubjectComment cmsSubjectComment = cmsSubjectCommentRepository.findById(id)
                    .orElseGet(CmsSubjectComment::new);
            ValidationUtil.isNull(cmsSubjectComment.getId(), "CmsSubjectComment", "id", id);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            cmsSubjectComment.setModifyTime(timestamp);
            cmsSubjectComment.setDeleted(1);
            cmsSubjectCommentRepository.save(cmsSubjectComment);
        }
    }

    @Override
    public void download(List<CmsSubjectCommentDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CmsSubjectCommentDTO cmsSubjectComment : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("所属专题", cmsSubjectComment.getSubjectId());
            map.put("用户名", cmsSubjectComment.getMemberNickName());
            map.put("用户图标", cmsSubjectComment.getMemberIcon());
            map.put("内容", cmsSubjectComment.getContent());
            map.put("创建时间", cmsSubjectComment.getCreateTime());
            map.put("状态", cmsSubjectComment.getShowStatus());
            map.put("添加时间", cmsSubjectComment.getAddTime());
            map.put("更新时间", cmsSubjectComment.getModifyTime());
            map.put("逻辑删除", cmsSubjectComment.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
