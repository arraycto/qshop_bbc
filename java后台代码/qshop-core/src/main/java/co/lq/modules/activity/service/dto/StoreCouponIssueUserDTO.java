package co.lq.modules.activity.service.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author billy
 * @date 2019-11-09
 */
@Data
public class StoreCouponIssueUserDTO implements Serializable {

    private Long    id;

    // 领取优惠券用户ID
    private Long    uid;

    // 优惠券前台领取ID
    private Long    issueCouponId;

    // 领取时间
    private Integer addTime;

    //所属店铺
    private Long    storeId;
}
