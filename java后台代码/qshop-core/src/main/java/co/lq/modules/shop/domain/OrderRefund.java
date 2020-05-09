package co.lq.modules.shop.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

/**
 * @author billy
 * @date 2020-03-29
 */
@Entity
@Data
@Table(name = "order_refund")
public class OrderRefund implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long       id;

    /** 订单id */
    @Column(name = "order_return_id")
    private Long       orderReturnId;

    /** 订单编号 */
    @Column(name = "order_id")
    private String     orderId;

    /** 申请状态：0->待处理；1->退货中；2->已完成；3->已拒绝 */
    @Column(name = "status")
    private Integer    status;

    /** 订单金额 */
    @Column(name = "order_amount")
    private BigDecimal orderAmount;

    /** 退款金额 */
    @Column(name = "return_amount")
    private BigDecimal returnAmount;

    /** 实退金额 */
    @Column(name = "real_amount")
    private BigDecimal realAmount;

    /** 申请退款原因 */
    @Column(name = "reason")
    private String     reason;

    /** 处理人员 */
    @Column(name = "handle_man")
    private String     handleMan;

    /** 所属店铺 */
    @Column(name = "store_id", nullable = false)
    private Long       storeId;

    // 店铺id
    @OneToOne
    @JoinColumn(name = "store_id", insertable = false, updatable = false)
    private Shop       shop;

    /** 添加时间 */
    @Column(name = "add_time", nullable = false)
    private Timestamp  addTime;

    /** 更新时间 */
    @Column(name = "modify_time", nullable = false)
    private Timestamp  modifyTime;

    /** 逻辑删除 */
    @Column(name = "deleted", nullable = false)
    private Integer    deleted;

    @Column(name = "verify_status", nullable = false)
    private Integer    verifyStatus;

    @Column(name = "refund_time", nullable = false)
    private Timestamp  refundTime;

    public void copy(OrderRefund source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
