package co.lq.modules.shop.service.impl;

import java.io.IOException;
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

import co.lq.exception.EntityExistException;
import co.lq.modules.shop.domain.UserRecharge;
import co.lq.modules.shop.repository.UserRechargeRepository;
import co.lq.modules.shop.service.UserRechargeService;
import co.lq.modules.shop.service.dto.UserRechargeDto;
import co.lq.modules.shop.service.dto.UserRechargeQueryCriteria;
import co.lq.modules.shop.service.mapper.UserRechargeMapper;
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
 * @date 2020-03-02
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserRechargeServiceImpl implements UserRechargeService {

    private final UserRechargeRepository userRechargeRepository;

    private final UserRechargeMapper     userRechargeMapper;

    public UserRechargeServiceImpl(UserRechargeRepository userRechargeRepository,
                                   UserRechargeMapper userRechargeMapper) {
        this.userRechargeRepository = userRechargeRepository;
        this.userRechargeMapper = userRechargeMapper;
    }

    @Override
    public Map<String, Object> queryAll(UserRechargeQueryCriteria criteria, Pageable pageable) {
        Page<UserRecharge> page = userRechargeRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(userRechargeMapper::toDto));
    }

    @Override
    public List<UserRechargeDto> queryAll(UserRechargeQueryCriteria criteria) {
        return userRechargeMapper.toDto(userRechargeRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public UserRechargeDto findById(Long id) {
        UserRecharge userRecharge = userRechargeRepository.findById(id).orElseGet(UserRecharge::new);
        ValidationUtil.isNull(userRecharge.getId(), "UserRecharge", "id", id);
        return userRechargeMapper.toDto(userRecharge);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserRechargeDto create(UserRecharge resources) {
        if (userRechargeRepository.findByOrderId(resources.getOrderId()) != null) {
            throw new EntityExistException(UserRecharge.class, "order_id", resources.getOrderId());
        }
        return userRechargeMapper.toDto(userRechargeRepository.save(resources));
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            userRechargeRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<UserRechargeDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserRechargeDto userRecharge : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("充值用户UID", userRecharge.getUid());
            map.put("订单号", userRecharge.getOrderId());
            map.put("充值金额", userRecharge.getPrice());
            map.put("充值类型", userRecharge.getRechargeType());
            map.put("是否充值", userRecharge.getPaid());
            map.put("充值支付时间", userRecharge.getPayTime());
            map.put("充值时间", userRecharge.getAddTime());
            map.put("退款金额", userRecharge.getRefundPrice());
            map.put("昵称", userRecharge.getNickname());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
