package co.lq.modules.shop.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.shop.domain.UserLevel;
import co.lq.modules.shop.repository.UserLevelRepository;
import co.lq.modules.shop.service.UserLevelService;
import co.lq.modules.shop.service.dto.UserLevelDTO;
import co.lq.modules.shop.service.dto.UserLevelQueryCriteria;
import co.lq.modules.shop.service.mapper.UserLevelMapper;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-12-04
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserLevelServiceImpl implements UserLevelService {

    private final UserLevelRepository userLevelRepository;
    private final UserLevelMapper     userLevelMapper;

    public UserLevelServiceImpl(UserLevelRepository userLevelRepository, UserLevelMapper userLevelMapper) {
        this.userLevelRepository = userLevelRepository;
        this.userLevelMapper = userLevelMapper;
    }

    @Override
    public Map<String, Object> queryAll(UserLevelQueryCriteria criteria, Pageable pageable) {
        criteria.setIsDel(0);
        Page<UserLevel> page = userLevelRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(userLevelMapper::toDto));
    }

    @Override
    public List<UserLevelDTO> queryAll(UserLevelQueryCriteria criteria) {
        criteria.setIsDel(0);
        return userLevelMapper.toDto(userLevelRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public UserLevelDTO findById(Long id) {
        Optional<UserLevel> systemUserLevel = userLevelRepository.findById(id);
        ValidationUtil.isNull(systemUserLevel, "UserLevel", "id", id);
        return userLevelMapper.toDto(systemUserLevel.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserLevelDTO create(UserLevel resources) {
        return userLevelMapper.toDto(userLevelRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserLevel resources) {
        Optional<UserLevel> optionalSystemUserLevel = userLevelRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalSystemUserLevel, "UserLevel", "id", resources.getId());
        UserLevel userLevel = optionalSystemUserLevel.get();
        userLevel.copy(resources);
        userLevelRepository.save(userLevel);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        Optional<UserLevel> optionalSystemUserLevel = userLevelRepository.findById(id);
        ValidationUtil.isNull(optionalSystemUserLevel, "UserLevel", "id", id);
        UserLevel userLevel = optionalSystemUserLevel.get();
        userLevel.setIsDel(1);
        userLevelRepository.save(userLevel);
    }
}
