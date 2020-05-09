package co.lq.modules.shop.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author billy
* @date 2020-03-27
*/
@Data
public class PointsConsumeSettingDTO implements Serializable {

    private Long id;

    /** 每一元需要抵扣的积分数量 */
    private Integer deductionPerAmount;

    /** 每笔订单最高抵用百分比 */
    private Integer maxPercentPerOrder;

    /** 每次使用积分最小单位100 */
    private Integer useUnit;

    /** 是否可以和优惠券同用；0->不可以；1->可以 */
    private Integer couponStatus;

    /** 登录送积分 */
    private Integer login;

    /** 注册送积分 */
    private Integer register;

    /** 签到送积分 */
    private Integer sign;

    /** 下单 送积分比例 */
    private Integer orders;

    /** 所属店铺 */
    private Long storeId;

    /** 添加时间 */
    private Timestamp addTime;

    /** 更新时间 */
    private Timestamp modifyTime;

    /** 逻辑删除 */
    private Integer deleted;
}