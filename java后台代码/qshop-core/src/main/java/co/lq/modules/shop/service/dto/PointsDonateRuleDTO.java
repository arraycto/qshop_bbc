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
public class PointsDonateRuleDTO implements Serializable {

    /** 编号 */
    private Long id;

    /** 赠送类别 */
    private String donateType;

    /** 赠送条件 */
    private BigDecimal donateCondtion;

    /** 赠送积分 */
    private Integer donateIntegration;

    /** 所属店铺 */
    private Long storeId;

    /** 添加时间 */
    private Timestamp addTime;

    /** 更新时间 */
    private Timestamp modifyTime;

    /** 逻辑删除 */
    private Integer deleted;
}