package co.lq.modules.shop.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

/**
 * @author billy
 * @date 2020-03-02
 */
@Entity
@Data
@Table(name = "user_recharge")
public class UserRecharge implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long       id;

    /** 充值用户UID */
    @Column(name = "uid")
    private Long       uid;

    /** 订单号 */
    @Column(name = "order_id", unique = true)
    private String     orderId;

    /** 充值金额 */
    @Column(name = "price")
    private BigDecimal price;

    /** 充值类型 */
    @Column(name = "recharge_type")
    private String     rechargeType;

    /** 是否充值 */
    @Column(name = "paid")
    private Integer    paid;

    /** 充值支付时间 */
    @Column(name = "pay_time")
    private Integer    payTime;

    /** 充值时间 */
    @Column(name = "add_time")
    private Integer    addTime;

    /** 退款金额 */
    @Column(name = "refund_price")
    private BigDecimal refundPrice;

    /** 昵称 */
    @Column(name = "nickname")
    private String     nickname;

    // 店铺id
    @Column(name = "store_id", nullable = false)
    private Long       storeId;

    public void copy(UserRecharge source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
