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

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import co.lq.modules.shop.domain.OrderRefund;
import co.lq.modules.shop.domain.OrderReturnApply;
import co.lq.modules.shop.repository.OrderReturnApplyRepository;
import co.lq.modules.shop.service.OrderRefundService;
import co.lq.modules.shop.service.OrderReturnApplyService;
import co.lq.modules.shop.service.dto.OrderReturnApplyDTO;
import co.lq.modules.shop.service.dto.OrderReturnApplyQueryCriteria;
import co.lq.modules.shop.service.mapper.OrderReturnApplyMapper;
import co.lq.modules.shop.service.param.OrderReturnApplyItem;
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
public class OrderReturnApplyServiceImpl implements OrderReturnApplyService {

    private final OrderReturnApplyRepository orderReturnApplyRepository;

    private final OrderReturnApplyMapper     orderReturnApplyMapper;

    private final OrderRefundService         orderRefundService;

    public OrderReturnApplyServiceImpl(OrderReturnApplyRepository orderReturnApplyRepository,
                                       OrderReturnApplyMapper orderReturnApplyMapper,
                                       OrderRefundService orderRefundService) {
        this.orderReturnApplyRepository = orderReturnApplyRepository;
        this.orderReturnApplyMapper = orderReturnApplyMapper;
        this.orderRefundService = orderRefundService;
    }

    @Override
    public Map<String, Object> queryAll(OrderReturnApplyQueryCriteria criteria, Pageable pageable) {
        Page<OrderReturnApply> page = orderReturnApplyRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(orderReturnApplyMapper::toDto));
    }

    @Override
    public List<OrderReturnApplyDTO> queryAll(OrderReturnApplyQueryCriteria criteria) {
        return orderReturnApplyMapper.toDto(orderReturnApplyRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public OrderReturnApplyDTO findById(Long id) {
        OrderReturnApply orderReturnApply = orderReturnApplyRepository.findById(id).orElseGet(OrderReturnApply::new);
        ValidationUtil.isNull(orderReturnApply.getId(), "OrderReturnApply", "id", id);
        OrderReturnApplyDTO orderReturnApplyDTO = orderReturnApplyMapper.toDto(orderReturnApply);
        if (orderReturnApplyDTO.getCartInfo() != null) {
            OrderReturnApplyItem orderReturnApplyItem = JSONObject.parseObject(orderReturnApplyDTO.getCartInfo(),
                    new TypeReference<OrderReturnApplyItem>() {
                    });
            orderReturnApplyDTO.setOrderReturnApplyItem(orderReturnApplyItem);
        }

        return orderReturnApplyDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderReturnApplyDTO create(OrderReturnApply resources) {
        return orderReturnApplyMapper.toDto(orderReturnApplyRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(OrderReturnApply resources) {
        OrderReturnApply orderReturnApply = orderReturnApplyRepository.findById(resources.getId())
                .orElseGet(OrderReturnApply::new);
        ValidationUtil.isNull(orderReturnApply.getId(), "OrderReturnApply", "id", resources.getId());
        orderReturnApply.copy(resources);
        orderReturnApplyRepository.save(orderReturnApply);

        if (resources.getStatus() == 2) {
            OrderRefund orderRefund = new OrderRefund();
            orderRefund.setOrderId(orderReturnApply.getOrderId());
            orderRefund.setOrderReturnId(resources.getId());
            orderRefund.setStatus(0);
            orderRefund.setOrderAmount(orderReturnApply.getOrderAmount());
            orderRefund.setRealAmount(orderReturnApply.getReturnAmount());
            orderRefund.setReturnAmount(orderReturnApply.getReturnAmount());
            orderRefund.setReason(orderReturnApply.getReason());
            orderRefund.setHandleMan(resources.getHandleMan());
            orderRefund.setStoreId(orderReturnApply.getStoreId());
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            orderRefund.setAddTime(timestamp);
            orderRefund.setModifyTime(timestamp);
            orderRefund.setDeleted(0);
            orderRefund.setVerifyStatus(0);
            orderRefundService.create(orderRefund);
        }
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            orderReturnApplyRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<OrderReturnApplyDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (OrderReturnApplyDTO orderReturnApply : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("订单id", orderReturnApply.getOid());
            map.put("收货地址表id", orderReturnApply.getAddressId());
            map.put("订单编号", orderReturnApply.getOrderId());
            map.put("会员用户名", orderReturnApply.getMemberUsername());
            map.put("退款金额", orderReturnApply.getReturnAmount());
            map.put("退货人姓名", orderReturnApply.getReturnName());
            map.put("退货人电话", orderReturnApply.getReturnPhone());
            map.put("申请状态：0->待处理；1->退货中；2->已完成；3->已拒绝", orderReturnApply.getStatus());
            map.put("原因", orderReturnApply.getReason());
            map.put("描述", orderReturnApply.getDescription());
            map.put("凭证图片，以逗号隔开", orderReturnApply.getProofPics());
            map.put("处理备注", orderReturnApply.getHandleNote());
            map.put("处理人员", orderReturnApply.getHandleMan());
            map.put("收货人", orderReturnApply.getReceiveMan());
            map.put("收货时间", orderReturnApply.getReceiveTime());
            map.put("收货备注", orderReturnApply.getReceiveNote());
            map.put("所属店铺", orderReturnApply.getStoreId());
            map.put("添加时间", orderReturnApply.getAddTime());
            map.put("更新时间", orderReturnApply.getModifyTime());
            map.put("逻辑删除", orderReturnApply.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
