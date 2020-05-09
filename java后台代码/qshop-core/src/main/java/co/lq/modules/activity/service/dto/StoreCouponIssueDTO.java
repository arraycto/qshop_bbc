package co.lq.modules.activity.service.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author billy
 * @date 2019-11-09
 */
@Data
public class StoreCouponIssueDTO implements Serializable {

    private Long    id;

    // 优惠券ID
    private Long    cid;

    private String  cname;

    // 优惠券领取开启时间
    private Integer startTime;

    // 优惠券领取结束时间
    private Integer endTime;

    private Date    startTimeDate;

    private Date    endTimeDate;

    // 优惠券领取数量
    private Integer totalCount;

    // 优惠券剩余领取数量
    private Integer remainCount;

    // 是否无限张数
    private Integer isPermanent;

    // 1 正常 0 未开启 -1 已无效
    private Integer status;

    private Integer isDel;

    // 优惠券添加时间
    private Integer addTime;

    //所属店铺
    private Long    storeId;
}
