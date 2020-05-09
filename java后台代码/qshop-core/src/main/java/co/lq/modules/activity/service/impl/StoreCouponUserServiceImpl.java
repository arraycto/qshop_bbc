package co.lq.modules.activity.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.activity.domain.StoreCouponUser;
import co.lq.modules.activity.repository.StoreCouponUserRepository;
import co.lq.modules.activity.service.StoreCouponUserService;
import co.lq.modules.activity.service.dto.StoreCouponUserDTO;
import co.lq.modules.activity.service.dto.StoreCouponUserQueryCriteria;
import co.lq.modules.activity.service.mapper.StoreCouponUserMapper;
import co.lq.modules.shop.service.UserService;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-11-10
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StoreCouponUserServiceImpl implements StoreCouponUserService {

    private final StoreCouponUserRepository storeCouponUserRepository;

    private final StoreCouponUserMapper     storeCouponUserMapper;

    private final UserService               userService;

    public StoreCouponUserServiceImpl(StoreCouponUserRepository storeCouponUserRepository,
                                      StoreCouponUserMapper storeCouponUserMapper, UserService userService) {
        this.storeCouponUserRepository = storeCouponUserRepository;
        this.storeCouponUserMapper = storeCouponUserMapper;
        this.userService = userService;
    }

    @Override
    public Map<String, Object> queryAll(StoreCouponUserQueryCriteria criteria, Pageable pageable) {
        Page<StoreCouponUser> page = storeCouponUserRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        //List<StoreCouponUserDTO> storeOrderDTOS = new ArrayList<>();
        List<StoreCouponUserDTO> storeOrderDTOS = storeCouponUserMapper.toDto(page.getContent());
        for (StoreCouponUserDTO couponUserDTO : storeOrderDTOS) {
            couponUserDTO.setNickname(userService.findById(couponUserDTO.getUid()).getNickname());
        }
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", storeOrderDTOS);
        map.put("totalElements", page.getTotalElements());

        return map;
        //return PageUtil.toPage(page.map(storeCouponUserMapper::toDto));
    }

    @Override
    public List<StoreCouponUserDTO> queryAll(StoreCouponUserQueryCriteria criteria) {
        return storeCouponUserMapper.toDto(storeCouponUserRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public StoreCouponUserDTO findById(Long id) {
        Optional<StoreCouponUser> storeCouponUser = storeCouponUserRepository.findById(id);
        ValidationUtil.isNull(storeCouponUser, "StoreCouponUser", "id", id);
        return storeCouponUserMapper.toDto(storeCouponUser.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreCouponUserDTO create(StoreCouponUser resources) {
        return storeCouponUserMapper.toDto(storeCouponUserRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreCouponUser resources) {
        Optional<StoreCouponUser> optionalStoreCouponUser = storeCouponUserRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalStoreCouponUser, "StoreCouponUser", "id", resources.getId());
        StoreCouponUser storeCouponUser = optionalStoreCouponUser.get();
        storeCouponUser.copy(resources);
        storeCouponUserRepository.save(storeCouponUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        storeCouponUserRepository.deleteById(id);
    }
}
