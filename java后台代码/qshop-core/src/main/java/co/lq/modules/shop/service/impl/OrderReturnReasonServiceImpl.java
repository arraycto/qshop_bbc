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

import co.lq.modules.shop.domain.OrderReturnReason;
import co.lq.modules.shop.repository.OrderReturnReasonRepository;
import co.lq.modules.shop.service.OrderReturnReasonService;
import co.lq.modules.shop.service.dto.OrderReturnReasonDTO;
import co.lq.modules.shop.service.dto.OrderReturnReasonQueryCriteria;
import co.lq.modules.shop.service.mapper.OrderReturnReasonMapper;
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
public class OrderReturnReasonServiceImpl implements OrderReturnReasonService {

    private final OrderReturnReasonRepository orderReturnReasonRepository;

    private final OrderReturnReasonMapper     orderReturnReasonMapper;

    public OrderReturnReasonServiceImpl(OrderReturnReasonRepository orderReturnReasonRepository,
                                        OrderReturnReasonMapper orderReturnReasonMapper) {
        this.orderReturnReasonRepository = orderReturnReasonRepository;
        this.orderReturnReasonMapper = orderReturnReasonMapper;
    }

    @Override
    public Map<String, Object> queryAll(OrderReturnReasonQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<OrderReturnReason> page = orderReturnReasonRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(orderReturnReasonMapper::toDto));
    }

    @Override
    public List<OrderReturnReasonDTO> queryAll(OrderReturnReasonQueryCriteria criteria) {
        criteria.setDeleted(0);
        return orderReturnReasonMapper.toDto(orderReturnReasonRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public OrderReturnReasonDTO findById(Long id) {
        OrderReturnReason orderReturnReason = orderReturnReasonRepository.findById(id)
                .orElseGet(OrderReturnReason::new);
        ValidationUtil.isNull(orderReturnReason.getId(), "OrderReturnReason", "id", id);
        return orderReturnReasonMapper.toDto(orderReturnReason);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderReturnReasonDTO create(OrderReturnReason resources) {
        resources.setDeleted(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setAddTime(timestamp);
        resources.setModifyTime(timestamp);
        return orderReturnReasonMapper.toDto(orderReturnReasonRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(OrderReturnReason resources) {
        OrderReturnReason orderReturnReason = orderReturnReasonRepository.findById(resources.getId())
                .orElseGet(OrderReturnReason::new);
        ValidationUtil.isNull(orderReturnReason.getId(), "OrderReturnReason", "id", resources.getId());
        orderReturnReason.copy(resources);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        orderReturnReason.setModifyTime(timestamp);
        orderReturnReasonRepository.save(orderReturnReason);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            OrderReturnReason orderReturnReason = orderReturnReasonRepository.findById(id)
                    .orElseGet(OrderReturnReason::new);
            ValidationUtil.isNull(orderReturnReason.getId(), "OrderReturnReason", "id", id);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            orderReturnReason.setModifyTime(timestamp);
            orderReturnReason.setDeleted(1);
            orderReturnReasonRepository.save(orderReturnReason);
        }
    }

    @Override
    public void download(List<OrderReturnReasonDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (OrderReturnReasonDTO orderReturnReason : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("退货类型", orderReturnReason.getName());
            map.put(" sort", orderReturnReason.getSort());
            map.put("状态：0->不启用；1->启用", orderReturnReason.getStatus());
            map.put("添加时间", orderReturnReason.getAddTime());
            map.put("更新时间", orderReturnReason.getModifyTime());
            map.put("逻辑删除", orderReturnReason.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
