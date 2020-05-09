package co.lq.modules.shop.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.shop.domain.UserBill;
import co.lq.modules.shop.repository.UserBillRepository;
import co.lq.modules.shop.service.UserBillService;
import co.lq.modules.shop.service.dto.UserBillDTO;
import co.lq.modules.shop.service.dto.UserBillQueryCriteria;
import co.lq.modules.shop.service.mapper.UserBillMapper;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-11-06
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserBillServiceImpl implements UserBillService {

    private final UserBillRepository userBillRepository;

    private final UserBillMapper     userBillMapper;

    public UserBillServiceImpl(UserBillRepository userBillRepository, UserBillMapper userBillMapper) {
        this.userBillRepository = userBillRepository;
        this.userBillMapper = userBillMapper;
    }

    @Override
    public Map<String, Object> queryAll(UserBillQueryCriteria criteria, Pageable pageable) {
        Page<Map> page = userBillRepository.findAllByPageable(criteria.getCategory(), criteria.getType(),
                criteria.getNickname(), pageable);
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", page.getContent());
        map.put("totalElements", page.getTotalElements());
        return map;
    }

    @Override
    public List<UserBillDTO> queryAll(UserBillQueryCriteria criteria) {
        return userBillMapper.toDto(userBillRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public UserBillDTO findById(Long id) {
        Optional<UserBill> userBill = userBillRepository.findById(id);
        ValidationUtil.isNull(userBill, "UserBill", "id", id);
        return userBillMapper.toDto(userBill.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserBillDTO create(UserBill resources) {
        return userBillMapper.toDto(userBillRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserBill resources) {
        Optional<UserBill> optionalUserBill = userBillRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalUserBill, "UserBill", "id", resources.getId());
        UserBill userBill = optionalUserBill.get();
        userBill.copy(resources);
        userBillRepository.save(userBill);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        userBillRepository.deleteById(id);
    }
}
