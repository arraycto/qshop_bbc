package co.lq.modules.shop.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

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
 * @date 2020-03-27
 */
@Entity
@Data
@Table(name = "order_return_apply")
public class OrderReturnApply implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long       id;

    /** 订单id */
    @Column(name = "oid")
    private Long       oid;

    /** 收货地址表id */
    @Column(name = "address_id")
    private Long       addressId;

    /** 订单编号 */
    @Column(name = "order_id")
    private String     orderId;

    /** 会员用户名 */
    @Column(name = "member_username")
    private String     memberUsername;

    /** 退款金额 */
    @Column(name = "return_amount")
    private BigDecimal returnAmount;

    /** 退货人姓名 */
    @Column(name = "return_name")
    private String     returnName;

    /** 退货人电话 */
    @Column(name = "return_phone")
    private String     returnPhone;

    /** 申请状态：0->待处理；1->退货中；2->已完成；3->已拒绝 */
    @Column(name = "status")
    private Integer    status;

    /** 原因 */
    @Column(name = "reason")
    private String     reason;

    /** 描述 */
    @Column(name = "description")
    private String     description;

    /** 凭证图片，以逗号隔开 */
    @Column(name = "proof_pics")
    private String     proofPics;

    /** 处理备注 */
    @Column(name = "handle_note")
    private String     handleNote;

    /** 处理人员 */
    @Column(name = "handle_man")
    private String     handleMan;

    /** 收货人 */
    @Column(name = "receive_man")
    private String     receiveMan;

    /** 收货时间 */
    @Column(name = "receive_time")
    private Timestamp  receiveTime;

    /** 收货备注 */
    @Column(name = "receive_note")
    private String     receiveNote;

    /** 所属店铺 */
    @Column(name = "store_id")
    private Long       storeId;

    /** 添加时间 */
    @Column(name = "add_time", nullable = false)
    private Timestamp  addTime;

    /** 更新时间 */
    @Column(name = "modify_time", nullable = false)
    private Timestamp  modifyTime;

    /** 逻辑删除 */
    @Column(name = "deleted", nullable = false)
    private Integer    deleted;

    /** 订单金额 */
    @Column(name = "order_amount")
    private BigDecimal orderAmount;

    /**
     * 退货明细
     */
    @Column(name = "cart_info")
    private String     cartInfo;

    public void copy(OrderReturnApply source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
