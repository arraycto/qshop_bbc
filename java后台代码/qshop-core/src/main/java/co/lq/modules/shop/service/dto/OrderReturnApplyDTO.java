package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import co.lq.modules.shop.service.param.OrderReturnApplyItem;
import lombok.Data;

/**
 * @author billy
 * @date 2020-03-27
 */
@Data
public class OrderReturnApplyDTO implements Serializable {

    private Long                 id;

    /** 订单id */
    private Long                 oid;

    /** 收货地址表id */
    private Long                 addressId;

    /** 订单编号 */
    private String               orderId;

    /** 会员用户名 */
    private String               memberUsername;

    /** 退款金额 */
    private BigDecimal           returnAmount;

    /** 退货人姓名 */
    private String               returnName;

    /** 退货人电话 */
    private String               returnPhone;

    /** 申请状态：0->待处理；1->退货中；2->已完成；3->已拒绝 */
    private Integer              status;

    /** 原因 */
    private String               reason;

    /** 描述 */
    private String               description;

    /** 凭证图片，以逗号隔开 */
    private String               proofPics;

    /** 处理备注 */
    private String               handleNote;

    /** 处理人员 */
    private String               handleMan;

    /** 收货人 */
    private String               receiveMan;

    /** 收货时间 */
    private Timestamp            receiveTime;

    /** 收货备注 */
    private String               receiveNote;

    /** 所属店铺 */
    private Long                 storeId;

    /** 所属店铺 */
    private String               shopName;

    /** 添加时间 */
    private Timestamp            addTime;

    /** 更新时间 */
    private Timestamp            modifyTime;

    /** 逻辑删除 */
    private Integer              deleted;

    /** 订单金额 */
    private BigDecimal           orderAmount;

    /** 退货明细 **/
    private String               cartInfo;

    private OrderReturnApplyItem orderReturnApplyItem;
}
