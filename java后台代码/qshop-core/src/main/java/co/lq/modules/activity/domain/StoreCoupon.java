package co.lq.modules.activity.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

/**
 * @author billy
 * @date 2019-11-09
 */
@Entity
@Data
@Table(name = "store_coupon")
public class StoreCoupon implements Serializable {

    // 优惠券表ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long       id;

    // 优惠券名称
    @Column(name = "title", nullable = false)
    @NotBlank(message = "名称必填")
    private String     title;

    // 兑换消耗积分值
    @Column(name = "integral", nullable = false)
    private Integer    integral;

    // 兑换的优惠券面值
    @Column(name = "coupon_price", nullable = false)
    @Min(value = 1, message = "面值必须大于1")
    private BigDecimal couponPrice;

    // 最低消费多少金额可用优惠券
    @Column(name = "use_min_price", nullable = false)
    @Min(value = 1, message = "最低消费必须大于1")
    private BigDecimal useMinPrice;

    // 优惠券有效期限（单位：天）
    @Column(name = "coupon_time", nullable = false)
    private Integer    couponTime;

    // 排序
    @Column(name = "sort", nullable = false)
    private Integer    sort;

    // 状态（0：关闭，1：开启）
    @Column(name = "status", nullable = false)
    private Integer    status;

    // 兑换项目添加时间
    @Column(name = "add_time", nullable = false)
    private Integer    addTime;

    // 是否删除
    @Column(name = "is_del", nullable = false, insertable = false)
    private Integer    isDel;

    // 店铺id
    @Column(name = "store_id", nullable = false)
    private Long       storeId;

    public void copy(StoreCoupon source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
