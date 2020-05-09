package co.lq.modules.user.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import co.lq.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户充值表
 * </p>
 *
 * @author billy
 * @since 2019-12-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserRecharge对象", description = "用户充值表")
public class UserRecharge extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;

    @ApiModelProperty(value = "充值用户UID")
    private Long              uid;

    @ApiModelProperty(value = "充值用户")
    private String            nickname;

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
