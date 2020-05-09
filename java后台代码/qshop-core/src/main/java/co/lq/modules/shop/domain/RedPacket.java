package co.lq.modules.shop.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author billy
* @date 2020-03-27
*/
@Entity
@Data
@Table(name="red_packet")
public class RedPacket implements Serializable {

    /** 红包编号 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 发红包的用户id */
    @Column(name = "user_id",nullable = false)
    @NotNull
    private Long userId;

    /** 红包金额 */
    @Column(name = "amount",nullable = false)
    @NotNull
    private BigDecimal amount;

    /** 发红包日期 */
    @Column(name = "send_date")
    private Timestamp sendDate;

    /** 红包总数 */
    @Column(name = "total",nullable = false)
    @NotNull
    private Integer total;

    /** 单个红包的金额 */
    @Column(name = "unit_amount")
    private BigDecimal unitAmount;

    /** 红包剩余个数 */
    @Column(name = "stock")
    private Integer stock;

    /** 红包类型 */
    @Column(name = "type",nullable = false)
    @NotNull
    private Integer type;

    /** 备注 */
    @Column(name = "note")
    private String note;

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

    public void copy(RedPacket source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}