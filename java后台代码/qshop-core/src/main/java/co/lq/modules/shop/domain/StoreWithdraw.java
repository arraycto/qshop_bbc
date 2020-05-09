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
 * @date 2020-04-13
 */
@Entity
@Data
@Table(name = "store_withdraw")
public class StoreWithdraw implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long       id;

    /** 申请金额 */
    @Column(name = "apply_amount")
    private BigDecimal applyAmount;

    /** 审核时间 */
    @Column(name = "verifyTime")
    private Timestamp  verifyTime;

    /** 提现状态 */
    @Column(name = "apply_status")
    private Integer    applyStatus;

    /** 所属店铺 */
    @Column(name = "store_id", nullable = false)
    private Long       storeId;

    // 店铺id
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

    public void copy(StoreWithdraw source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
