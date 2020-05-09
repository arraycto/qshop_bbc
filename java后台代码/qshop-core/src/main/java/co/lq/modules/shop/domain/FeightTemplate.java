package co.lq.modules.shop.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

/**
 * @author billy
 * @date 2020-03-27
 */
@Entity
@Data
@Table(name = "feight_template")
public class FeightTemplate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long       id;

    /** 名称 */
    @Column(name = "name")
    private String     name;

    /** 计费类型:0->按重量；1->按件数 */
    @Column(name = "charge_type")
    private Integer    chargeType;

    /** 首重kg */
    @Column(name = "first_weight")
    private BigDecimal firstWeight;

    /** 首费（元） */
    @Column(name = "first_fee")
    private BigDecimal firstFee;

    /** 后重量 */
    @Column(name = "continue_weight")
    private BigDecimal continueWeight;

    /** 后费用 */
    @Column(name = "continme_fee")
    private BigDecimal continmeFee;

    /** 目的地（省、市） */
    @Column(name = "dest")
    private String     dest;

    /** 所属店铺 */
    @Column(name = "store_id")
    private Long       storeId;

    /** 所属店铺 */
    @OneToOne
    @JoinColumn(name = "store_id", insertable = false, updatable = false)
    private Shop       shop;

    /** 添加时间 */
    @Column(name = "add_time")
    private Timestamp  addTime;

    /** 更新时间 */
    @Column(name = "modify_time")
    private Timestamp  modifyTime;

    /** 逻辑删除 */
    @Column(name = "deleted")
    private Integer    deleted;

    public void copy(FeightTemplate source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
