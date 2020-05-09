package co.lq.modules.shop.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

/**
 * @author billy
 * @date 2020-03-11
 */
@Entity
@Data
@Table(name = "member_shared_product")
public class MemberSharedProduct implements Serializable {

    /** 分销商品id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long       id;

    /** 商品id */
    @Column(name = "mer_id")
    private Integer    merId;

    /** 会员价格 */
    @Column(name = "vip_price")
    private BigDecimal vipPrice;

    /** 所属店铺 */
    @Column(name = "store_id")
    private Long       storeId;

    /** 上下架状态;0-下架,1-上架 */
    @Column(name = "status", nullable = false)
    @NotNull
    private Integer    status;

    /** 添加时间 */
    @Column(name = "add_time")
    private Timestamp  addTime;

    /** 更新时间 */
    @Column(name = "modify_time")
    private Timestamp  modifyTime;

    /** 逻辑删除 */
    @Column(name = "deleted", nullable = false)
    @NotNull
    private Integer    deleted;

    public void copy(MemberSharedProduct source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
