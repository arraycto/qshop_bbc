package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

/**
 * @author billy
 * @date 2020-04-13
 */
@Data
public class StoreWithdrawDTO implements Serializable {

    private Long       id;

    /** 申请金额 */
    private BigDecimal applyAmount;

    /** 审核时间 */
    private Timestamp  verifyTime;

    /** 提现状态 */
    private Integer    applyStatus;

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
}
