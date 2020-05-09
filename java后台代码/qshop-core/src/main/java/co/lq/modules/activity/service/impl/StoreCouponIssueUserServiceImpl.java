package co.lq.modules.activity.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.activity.domain.StoreCouponIssueUser;
import co.lq.modules.activity.repository.StoreCouponIssueUserRepository;
import co.lq.modules.activity.service.StoreCouponIssueUserService;
import co.lq.modules.activity.service.dto.StoreCouponIssueUserDTO;
import co.lq.modules.activity.service.dto.StoreCouponIssueUserQueryCriteria;
import co.lq.modules.activity.service.mapper.StoreCouponIssueUserMapper;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-11-09
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StoreCouponIssueUserServiceImpl implements StoreCouponIssueUserService {

    private final StoreCouponIssueUserRepository storeCouponIssueUserRepository;

    private final StoreCouponIssueUserMapper     storeCouponIssueUserMapper;

    public StoreCouponIssueUserServiceImpl(StoreCouponIssueUserRepository storeCouponIssueUserRepository,
                                           StoreCouponIssueUserMapper storeCouponIssueUserMapper) {
        this.storeCouponIssueUserRepository = storeCouponIssueUserRepository;
        this.storeCouponIssueUserMapper = storeCouponIssueUserMapper;
    }

    @Override
    public Map<String, Object> queryAll(StoreCouponIssueUserQueryCriteria criteria, Pageable pageable) {
        Page<StoreCouponIssueUser> page = storeCouponIssueUserRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(storeCouponIssueUserMapper::toDto));
    }

    @Override
    public List<StoreCouponIssueUserDTO> queryAll(StoreCouponIssueUserQueryCriteria criteria) {
        return storeCouponIssueUserMapper.toDto(storeCouponIssueUserRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public StoreCouponIssueUserDTO findById(Long id) {
        Optional<StoreCouponIssueUser> storeCouponIssueUser = storeCouponIssueUserRepository.findById(id);
        ValidationUtil.isNull(storeCouponIssueUser, "StoreCouponIssueUser", "id", id);
        return storeCouponIssueUserMapper.toDto(storeCouponIssueUser.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreCouponIssueUserDTO create(StoreCouponIssueUser resources) {
        return storeCouponIssueUserMapper.toDto(storeCouponIssueUserRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreCouponIssueUser resources) {
        Optional<StoreCouponIssueUser> optionalStoreCouponIssueUser = storeCouponIssueUserRepository
                .findById(resources.getId());
        ValidationUtil.isNull(optionalStoreCouponIssueUser, "StoreCouponIssueUser", "id", resources.getId());
        StoreCouponIssueUser storeCouponIssueUser = optionalStoreCouponIssueUser.get();
        storeCouponIssueUser.copy(resources);
        storeCouponIssueUserRepository.save(storeCouponIssueUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        storeCouponIssueUserRepository.deleteById(id);
    }
}
