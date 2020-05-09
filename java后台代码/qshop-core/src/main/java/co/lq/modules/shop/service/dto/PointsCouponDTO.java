package co.lq.modules.shop.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author billy
* @date 2020-03-27
*/
@Data
public class PointsCouponDTO implements Serializable {

    /** 编号 */
    private Long id;

    /** 有效期(天) */
    private Integer validDay;

    /** 最小积分 */
    private Integer minIntegration;

    /** 最大积分 */
    private Integer maxIntegration;

    /** 备注 */
    private String remark;

    /** 创建时间 */
    private Timestamp createTime;

    /** 所属店铺 */
    private Long storeId;

    /** 添加时间 */
    private Timestamp addTime;

    /** 更新时间 */
    private Timestamp modifyTime;

    /** 逻辑删除 */
    private Integer deleted;

    private Integer count;
}