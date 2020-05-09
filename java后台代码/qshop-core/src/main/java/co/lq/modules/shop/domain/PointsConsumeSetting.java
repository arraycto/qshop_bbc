package co.lq.modules.shop.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author billy
* @date 2020-03-27
*/
@Entity
@Data
@Table(name="points_consume_setting")
public class PointsConsumeSetting implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 每一元需要抵扣的积分数量 */
    @Column(name = "deduction_per_amount")
    private Integer deductionPerAmount;

    /** 每笔订单最高抵用百分比 */
    @Column(name = "max_percent_per_order")
    private Integer maxPercentPerOrder;

    /** 每次使用积分最小单位100 */
    @Column(name = "use_unit")
    private Integer useUnit;

    /** 是否可以和优惠券同用；0->不可以；1->可以 */
    @Column(name = "coupon_status")
    private Integer couponStatus;

    /** 登录送积分 */
    @Column(name = "login")
    private Integer login;

    /** 注册送积分 */
    @Column(name = "register")
    private Integer register;

    /** 签到送积分 */
    @Column(name = "sign")
    private Integer sign;

    /** 下单 送积分比例 */
    @Column(name = "orders")
    private Integer orders;

    /** 所属店铺 */
    @Column(name = "store_id")
    private Long storeId;

    /** 添加时间 */
    @Column(name = "add_time",nullable = false)
    @NotNull
    private Timestamp addTime;

    /** 更新时间 */
    @Column(name = "modify_time",nullable = false)
    @NotNull
    private Timestamp modifyTime;

    /** 逻辑删除 */
    @Column(name = "deleted",nullable = false)
    @NotNull
    private Integer deleted;

    public void copy(PointsConsumeSetting source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}