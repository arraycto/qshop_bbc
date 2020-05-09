package co.lq.modules.activity.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.activity.domain.StoreBargainUser;
import co.lq.modules.activity.repository.StoreBargainUserRepository;
import co.lq.modules.activity.service.StoreBargainUserService;
import co.lq.modules.activity.service.dto.StoreBargainUserDTO;
import co.lq.modules.activity.service.dto.StoreBargainUserQueryCriteria;
import co.lq.modules.activity.service.mapper.StoreBargainUserMapper;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * 砍价结果
 *
 * @author songbin
 * @since 2020年3月22日 下午3:59:12
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StoreBargainUserServiceImpl implements StoreBargainUserService {

    private final StoreBargainUserRepository storeBargainUserRepository;
    private final StoreBargainUserMapper     storeBargainUserMapper;

    public StoreBargainUserServiceImpl(StoreBargainUserRepository storeBargainUserRepository,
                                       StoreBargainUserMapper storeBargainUserMapper) {
        this.storeBargainUserRepository = storeBargainUserRepository;
        this.storeBargainUserMapper = storeBargainUserMapper;
    }

    @Override
    public Map<String, Object> queryAll(StoreBargainUserQueryCriteria criteria, Pageable pageable) {
        Page<StoreBargainUser> page = storeBargainUserRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(storeBargainUserMapper::toDto));
    }

    @Override
    public List<StoreBargainUserDTO> queryAll(StoreBargainUserQueryCriteria criteria) {
        return storeBargainUserMapper.toDto(storeBargainUserRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public StoreBargainUserDTO findById(Long id) {
        Optional<StoreBargainUser> storeBargain = storeBargainUserRepository.findById(id);
        ValidationUtil.isNull(storeBargain, "StoreBargainUser", "id", id);
        return storeBargainUserMapper.toDto(storeBargain.get());
    }

    @Override
    public StoreBargainUserDTO create(StoreBargainUser resources) {
        return storeBargainUserMapper.toDto(storeBargainUserRepository.save(resources));
    }

    @Override
    public void update(StoreBargainUser resources) {
        Optional<StoreBargainUser> optionalStoreBargainUser = storeBargainUserRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalStoreBargainUser, "StoreBargainUser", "id", resources.getId());
        StoreBargainUser storeBargainUser = optionalStoreBargainUser.get();
        storeBargainUser.copy(resources);
        storeBargainUserRepository.save(storeBargainUser);
    }

    @Override
    public void delete(Long id) {
        Optional<StoreBargainUser> optionalStoreBargain = storeBargainUserRepository.findById(id);
        ValidationUtil.isNull(optionalStoreBargain, "StoreBargain", "id", id);
        StoreBargainUser storeBargainUser = optionalStoreBargain.get();
        storeBargainUser.setIsDel(1);
        storeBargainUserRepository.save(storeBargainUser);
    }
}
