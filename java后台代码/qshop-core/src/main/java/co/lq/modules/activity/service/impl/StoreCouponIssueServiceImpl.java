package co.lq.modules.activity.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.activity.domain.StoreCouponIssue;
import co.lq.modules.activity.repository.StoreCouponIssueRepository;
import co.lq.modules.activity.service.StoreCouponIssueService;
import co.lq.modules.activity.service.dto.StoreCouponIssueDTO;
import co.lq.modules.activity.service.dto.StoreCouponIssueQueryCriteria;
import co.lq.modules.activity.service.mapper.StoreCouponIssueMapper;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-11-09
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StoreCouponIssueServiceImpl implements StoreCouponIssueService {

    private final StoreCouponIssueRepository storeCouponIssueRepository;

    private final StoreCouponIssueMapper     storeCouponIssueMapper;

    public StoreCouponIssueServiceImpl(StoreCouponIssueRepository storeCouponIssueRepository,
                                       StoreCouponIssueMapper storeCouponIssueMapper) {
        this.storeCouponIssueRepository = storeCouponIssueRepository;
        this.storeCouponIssueMapper = storeCouponIssueMapper;
    }

    @Override
    public Map<String, Object> queryAll(StoreCouponIssueQueryCriteria criteria, Pageable pageable) {
        criteria.setIsDel(0);
        Page<StoreCouponIssue> page = storeCouponIssueRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(storeCouponIssueMapper::toDto));
    }

    @Override
    public List<StoreCouponIssueDTO> queryAll(StoreCouponIssueQueryCriteria criteria) {
        return storeCouponIssueMapper.toDto(storeCouponIssueRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public StoreCouponIssueDTO findById(Long id) {
        Optional<StoreCouponIssue> storeCouponIssue = storeCouponIssueRepository.findById(id);
        ValidationUtil.isNull(storeCouponIssue, "StoreCouponIssue", "id", id);
        return storeCouponIssueMapper.toDto(storeCouponIssue.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreCouponIssueDTO create(StoreCouponIssue resources) {
        return storeCouponIssueMapper.toDto(storeCouponIssueRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreCouponIssue resources) {
        Optional<StoreCouponIssue> optionalStoreCouponIssue = storeCouponIssueRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalStoreCouponIssue, "StoreCouponIssue", "id", resources.getId());
        StoreCouponIssue storeCouponIssue = optionalStoreCouponIssue.get();
        storeCouponIssue.copy(resources);
        storeCouponIssueRepository.save(storeCouponIssue);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        storeCouponIssueRepository.deleteById(id);
    }
}
