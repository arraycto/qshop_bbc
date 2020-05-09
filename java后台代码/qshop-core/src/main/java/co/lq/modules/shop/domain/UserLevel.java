package co.lq.modules.shop.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

/**
 * @author billy
 * @date 2019-12-04
 */
@Entity
@Data
@Table(name = "user_level")
public class UserLevel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long       id;

    // 商户id
    @Column(name = "mer_id", insertable = false)
    private Long       merId;

    // 会员名称
    @Column(name = "name", nullable = false)
    @NotBlank(message = "请填写等级名称")
    private String     name;

    // 购买金额
    @Column(name = "money", insertable = false)
    private BigDecimal money;

    // 有效时间
    @Column(name = "valid_date", nullable = false)
    private Integer    validDate;

    // 是否为永久会员
    @Column(name = "is_forever", nullable = false)
    private Integer    isForever;

    // 是否购买,1=购买,0=不购买
    @Column(name = "is_pay", insertable = false)
    private Integer    isPay;

    // 是否显示 1=显示,0=隐藏
    @Column(name = "is_show", nullable = false)
    @NotNull(message = "请选择显示状态")
    private Integer    isShow;

    // 会员等级
    @Column(name = "grade", nullable = false)
    @NotNull(message = "等级必填")
    private Integer    grade;

    // 享受折扣
    @Column(name = "discount", nullable = false)
    @NotNull(message = "折扣必填")
    private BigDecimal discount;

    // 会员卡背景
    @Column(name = "image", nullable = false)
    @NotBlank(message = "请上传背景")
    private String     image;

    // 会员图标
    @Column(name = "icon", nullable = false)
    @NotBlank(message = "请上传图标")
    private String     icon;

    // 说明
    @Column(name = "`explain`", nullable = false)
    @NotBlank(message = "请填写说明")
    private String     explain;

    // 添加时间
    @Column(name = "add_time", nullable = false)
    private Integer    addTime;

    // 是否删除.1=删除,0=未删除
    @Column(name = "is_del", insertable = false)
    private Integer    isDel;

    // 店铺id
    @Column(name = "store_id", nullable = false)
    private Long       storeId;

    public void copy(UserLevel source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
