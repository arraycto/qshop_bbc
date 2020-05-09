package co.lq.modules.shop.service;

import java.io.Serializable;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.OrderReturnApply;
import co.lq.modules.shop.web.param.OrderReturnApplyQueryParam;
import co.lq.modules.shop.web.vo.OrderReturnApplyQueryVo;

/**
 * <p>
 * 订单退货申请 服务类
 * </p>
 *
 * @author billy
 * @since 2020-04-04
 */
public interface ApiOrderReturnApplyService extends BaseService<OrderReturnApply> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    OrderReturnApplyQueryVo getOrderReturnApplyById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param orderReturnApplyQueryParam
     * @return
     */
    Paging<OrderReturnApplyQueryVo> getOrderReturnApplyPageList(OrderReturnApplyQueryParam orderReturnApplyQueryParam)
            throws Exception;

}
