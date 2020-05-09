package co.lq.modules.shop.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.modules.order.mapper.StoreOrderMapper;
import co.lq.modules.order.web.vo.StoreOrderQueryVo;
import co.lq.modules.shop.entity.OrderReturnApply;
import co.lq.modules.shop.mapper.OrderReturnApplyMapper;
import co.lq.modules.shop.service.ApiOrderReturnApplyService;
import co.lq.modules.shop.web.param.OrderReturnApplyQueryParam;
import co.lq.modules.shop.web.vo.OrderReturnApplyQueryVo;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 订单退货申请 服务实现类
 * </p>
 *
 * @author billy
 * @since 2020-04-04
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiOrderReturnApplyServiceImpl extends BaseServiceImpl<OrderReturnApplyMapper, OrderReturnApply>
        implements ApiOrderReturnApplyService {

    @Autowired
    private OrderReturnApplyMapper orderReturnApplyMapper;

    @Autowired
    private StoreOrderMapper       storeOrderMapper;

    @Override
    public OrderReturnApplyQueryVo getOrderReturnApplyById(Serializable id) throws Exception {
        OrderReturnApplyQueryVo orderReturnApplyQueryVo = orderReturnApplyMapper.getOrderReturnApplyById(id);
        StoreOrderQueryVo storeOrderQueryVo = storeOrderMapper.getStoreOrderById(orderReturnApplyQueryVo.getOid());
        orderReturnApplyQueryVo.setOrderStatus(storeOrderQueryVo.getStatus());
        return orderReturnApplyMapper.getOrderReturnApplyById(id);
    }

    @Override
    public Paging<OrderReturnApplyQueryVo> getOrderReturnApplyPageList(OrderReturnApplyQueryParam orderReturnApplyQueryParam)
            throws Exception {
        Page page = setPageParam(orderReturnApplyQueryParam, OrderItem.desc("add_time"));
        IPage<OrderReturnApplyQueryVo> iPage = orderReturnApplyMapper.getOrderReturnApplyPageList(page,
                orderReturnApplyQueryParam);
        return new Paging(iPage);
    }

}
