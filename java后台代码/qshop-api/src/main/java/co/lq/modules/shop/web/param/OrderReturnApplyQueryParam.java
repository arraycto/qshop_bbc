package co.lq.modules.shop.web.param;

import co.lq.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单退货申请 查询参数对象
 * </p>
 *
 * @author billy
 * @date 2020-04-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OrderReturnApplyQueryParam对象", description = "订单退货申请查询参数")
public class OrderReturnApplyQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
