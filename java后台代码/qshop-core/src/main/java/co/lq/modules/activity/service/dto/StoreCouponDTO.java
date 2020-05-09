package co.lq.modules.activity.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * @author billy
 * @date 2019-11-09
 */
@Data
public class StoreCouponDTO implements Serializable {

    // 优惠券表ID
    private Long       id;

    // 优惠券名称
    private String     title;

    // 兑换消耗积分值
    private Integer    integral;

    // 兑换的优惠券面值
    private BigDecimal couponPrice;

    // 最低消费多少金额可用优惠券
    private BigDecimal useMinPrice;

    // 优惠券有效期限（单位：天）
    private Integer    couponTime;

    // 排序
    private Integer    sort;

    // 状态（0：关闭，1：开启）
    private Integer    status;

    // 兑换项目添加时间
    private Integer    addTime;

    // 是否删除
    private Integer    isDel;

    //所属店铺
    private Long       storeId;
}
