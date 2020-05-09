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

import co.lq.modules.shop.domain.OrderRefund;
import co.lq.modules.shop.repository.OrderRefundRepository;
import co.lq.modules.shop.service.OrderRefundService;
import co.lq.modules.shop.service.dto.OrderRefundDTO;
import co.lq.modules.shop.service.dto.OrderRefundQueryCriteria;
import co.lq.modules.shop.service.mapper.OrderRefundMapper;
import co.lq.utils.FileUtil;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

/**
 * @author billy
 * @date 2020-03-29
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class OrderRefundServiceImpl implements OrderRefundService {

    private final OrderRefundRepository orderRefundRepository;

    private final OrderRefundMapper     orderRefundMapper;

    public OrderRefundServiceImpl(OrderRefundRepository orderRefundRepository, OrderRefundMapper orderRefundMapper) {
        this.orderRefundRepository = orderRefundRepository;
        this.orderRefundMapper = orderRefundMapper;
    }

    @Override
    public Map<String, Object> queryAll(OrderRefundQueryCriteria criteria, Pageable pageable) {
        Page<OrderRefund> page = orderRefundRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(orderRefundMapper::toDto));
    }

    @Override
    public List<OrderRefundDTO> queryAll(OrderRefundQueryCriteria criteria) {
        return orderRefundMapper.toDto(orderRefundRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public OrderRefundDTO findById(Long id) {
        OrderRefund orderRefund = orderRefundRepository.findById(id).orElseGet(OrderRefund::new);
        ValidationUtil.isNull(orderRefund.getId(), "OrderRefund", "id", id);
        return orderRefundMapper.toDto(orderRefund);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderRefundDTO create(OrderRefund resources) {
        return orderRefundMapper.toDto(orderRefundRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(OrderRefund resources) {
        OrderRefund orderRefund = orderRefundRepository.findById(resources.getId()).orElseGet(OrderRefund::new);
        ValidationUtil.isNull(orderRefund.getId(), "OrderRefund", "id", resources.getId());
        orderRefund.copy(resources);
        orderRefundRepository.save(orderRefund);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            orderRefundRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<OrderRefundDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (OrderRefundDTO orderRefund : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("订单id", orderRefund.getOrderReturnId());
            map.put("订单编号", orderRefund.getOrderId());
            map.put("申请状态：0->待处理；1->退货中；2->已完成；3->已拒绝", orderRefund.getStatus());
            map.put("订单金额", orderRefund.getOrderAmount());
            map.put("退款金额", orderRefund.getReturnAmount());
            map.put("实退金额", orderRefund.getRealAmount());
            map.put("申请退款原因", orderRefund.getReason());
            map.put("处理人员", orderRefund.getHandleMan());
            map.put("所属店铺", orderRefund.getStoreId());
            map.put("添加时间", orderRefund.getAddTime());
            map.put("更新时间", orderRefund.getModifyTime());
            map.put("逻辑删除", orderRefund.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public Boolean refund(Long id) {
        OrderRefund orderRefund = orderRefundRepository.findById(id).orElseGet(OrderRefund::new);
        ValidationUtil.isNull(orderRefund.getId(), "OrderRefund", "id", id);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        orderRefund.setModifyTime(timestamp);
        orderRefund.setRefundTime(timestamp);
        orderRefund.setStatus(2);

        orderRefundRepository.save(orderRefund);

        return true;
    }
}
