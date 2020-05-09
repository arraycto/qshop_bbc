package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

/**
 * @author billy
 * @date 2020-03-27
 */
@Data
public class FeightTemplateDTO implements Serializable {

    private Long       id;

    /** 名称 */
    private String     name;

    /** 计费类型:0->按重量；1->按件数 */
    private Integer    chargeType;

    /** 首重kg */
    private BigDecimal firstWeight;

    /** 首费（元） */
    private BigDecimal firstFee;

    /** 后重量 */
    private BigDecimal continueWeight;

    /** 后费用 */
    private BigDecimal continmeFee;

    /** 目的地（省、市） */
    private String     dest;

    /** 所属店铺 */
    private Long       storeId;

    private String     shopName;

    /** 添加时间 */
    private Timestamp  addTime;

    /** 更新时间 */
    private Timestamp  modifyTime;

    /** 逻辑删除 */
    private Integer    deleted;
}
