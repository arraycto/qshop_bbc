package co.lq.modules.shop.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author billy
* @date 2020-03-27
*/
@Data
public class PointsSignRuleDTO implements Serializable {

    /** 编号 */
    private Long id;

    /** 连续签到 */
    private Integer contineuCount;

    /** 赠送积分 */
    private Integer donateIntegrtion;

    /** 所属店铺 */
    private Long storeId;

    /** 添加时间 */
    private Timestamp addTime;

    /** 更新时间 */
    private Timestamp modifyTime;

    /** 逻辑删除 */
    private Integer deleted;
}