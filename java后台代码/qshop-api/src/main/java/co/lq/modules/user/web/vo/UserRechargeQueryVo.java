package co.lq.modules.user.web.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 用户充值表 查询结果对象
 * </p>
 *
 * @author billy
 * @date 2019-12-08
 */
@Data
@ApiModel(value = "UserRechargeQueryVo对象", description = "用户充值表查询参数")
public class UserRechargeQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long              id;

    @ApiModelProperty(value = "充值用户UID")
    private Long              uid;

    @ApiModelProperty(value = "订单号")
    private String            orderId;

    @ApiModelProperty(value = "充值金额")
    private BigDecimal        price;

    @ApiModelProperty(value = "充值类型")
    private String            rechargeType;

    @ApiModelProperty(value = "是否充值")
    private Integer           paid;

    @ApiModelProperty(value = "充值支付时间")
    private Integer           payTime;

    @ApiModelProperty(value = "充值时间")
    private Integer           addTime;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal        refundPrice;

}
