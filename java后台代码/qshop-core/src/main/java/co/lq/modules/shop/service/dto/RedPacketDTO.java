package co.lq.modules.shop.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author billy
* @date 2020-03-27
*/
@Data
public class RedPacketDTO implements Serializable {

    /** 红包编号 */
    private Long id;

    /** 发红包的用户id */
    private Long userId;

    /** 红包金额 */
    private BigDecimal amount;

    /** 发红包日期 */
    private Timestamp sendDate;

    /** 红包总数 */
    private Integer total;

    /** 单个红包的金额 */
    private BigDecimal unitAmount;

    /** 红包剩余个数 */
    private Integer stock;

    /** 红包类型 */
    private Integer type;

    /** 备注 */
    private String note;

    /** 所属店铺 */
    private Long storeId;

    /** 添加时间 */
    private Timestamp addTime;

    /** 更新时间 */
    private Timestamp modifyTime;

    /** 逻辑删除 */
    private Integer deleted;
}