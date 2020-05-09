package co.lq.modules.activity.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.activity.domain.UserExtract;
import co.lq.modules.activity.repository.UserExtractRepository;
import co.lq.modules.activity.service.UserExtractService;
import co.lq.modules.activity.service.dto.UserExtractDTO;
import co.lq.modules.activity.service.dto.UserExtractQueryCriteria;
import co.lq.modules.activity.service.mapper.UserExtractMapper;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-11-14
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserExtractServiceImpl implements UserExtractService {

    private final UserExtractRepository userExtractRepository;

    private final UserExtractMapper     userExtractMapper;

    public UserExtractServiceImpl(UserExtractRepository userExtractRepository, UserExtractMapper userExtractMapper) {
        this.userExtractRepository = userExtractRepository;
        this.userExtractMapper = userExtractMapper;
    }

    @Override
    public Map<String, Object> queryAll(UserExtractQueryCriteria criteria, Pageable pageable) {
        Page<UserExtract> page = userExtractRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(userExtractMapper::toDto));
    }

    @Override
    public List<UserExtractDTO> queryAll(UserExtractQueryCriteria criteria) {
        return userExtractMapper.toDto(userExtractRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public UserExtractDTO findById(Long id) {
        Optional<UserExtract> userExtract = userExtractRepository.findById(id);
        ValidationUtil.isNull(userExtract, "UserExtract", "id", id);
        return userExtractMapper.toDto(userExtract.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserExtractDTO create(UserExtract resources) {
        return userExtractMapper.toDto(userExtractRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserExtract resources) {
        Optional<UserExtract> optionalUserExtract = userExtractRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalUserExtract, "UserExtract", "id", resources.getId());
        UserExtract userExtract = optionalUserExtract.get();
        userExtract.copy(resources);
        userExtractRepository.save(userExtract);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        userExtractRepository.deleteById(id);
    }
}
