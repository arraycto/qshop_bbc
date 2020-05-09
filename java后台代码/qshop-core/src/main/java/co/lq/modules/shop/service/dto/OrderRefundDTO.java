package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

/**
 * @author billy
 * @date 2020-03-29
 */
@Data
public class OrderRefundDTO implements Serializable {

    private Long       id;

    /** 订单id */
    private Long       orderReturnId;

    /** 订单编号 */
    private String     orderId;

    /** 申请状态：0->待处理；1->退货中；2->已完成；3->已拒绝 */
    private Integer    status;

    /** 订单金额 */
    private BigDecimal orderAmount;

    /** 退款金额 */
    private BigDecimal returnAmount;

    /** 实退金额 */
    private BigDecimal realAmount;

    /** 申请退款原因 */
    private String     reason;

    /** 处理人员 */
    private String     handleMan;

    /** 所属店铺 */
    private Long       storeId;

    /** 店铺名称 **/
    private String     shopName;

    /** 添加时间 */
    private Timestamp  addTime;

    /** 更新时间 */
    private Timestamp  modifyTime;

    /** 逻辑删除 */
    private Integer    deleted;

    /** 审核状态 **/
    private Integer    verifyStatus;

    private Timestamp  refundTime;
}
