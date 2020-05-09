package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * @author billy
 * @date 2020-03-02
 */
@Data
public class UserRechargeDto implements Serializable {

    private Long       id;

    /** 充值用户UID */
    private Long       uid;

    /** 订单号 */
    private String     orderId;

    /** 充值金额 */
    private BigDecimal price;

    /** 充值类型 */
    private String     rechargeType;

    /** 是否充值 */
    private Integer    paid;

    /** 充值支付时间 */
    private Integer    payTime;

    /** 充值时间 */
    private Integer    addTime;

    /** 退款金额 */
    private BigDecimal refundPrice;

    /** 昵称 */
    private String     nickname;

    //所属店铺
    private Long       storeId;
}
