package co.lq.modules.shop.web.param;

import co.lq.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 优惠券表 查询参数对象
 * </p>
 *
 * @author billy
 * @date 2019-10-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StoreCouponQueryParam对象", description = "优惠券表查询参数")
public class StoreCouponQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
