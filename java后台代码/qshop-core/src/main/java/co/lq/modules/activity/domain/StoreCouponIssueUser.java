package co.lq.modules.activity.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

/**
 * @author billy
 * @date 2019-11-09
 */
@Entity
@Data
@Table(name = "store_coupon_issue_user")
public class StoreCouponIssueUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long    id;

    // 领取优惠券用户ID
    @Column(name = "uid")
    private Long    uid;

    // 优惠券前台领取ID
    @Column(name = "issue_coupon_id")
    private Long    issueCouponId;

    // 领取时间
    @Column(name = "add_time")
    private Integer addTime;

    // 店铺id
    @Column(name = "store_id", nullable = false)
    private Long    storeId;

    public void copy(StoreCouponIssueUser source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
