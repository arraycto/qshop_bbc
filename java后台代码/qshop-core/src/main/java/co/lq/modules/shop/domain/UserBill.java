package co.lq.modules.shop.domain;

import java.io.Serializable;
import java.math.BigDecimal;

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
 * @date 2019-11-06
 */
@Entity
@Data
@Table(name = "user_bill")
public class UserBill implements Serializable {

    // 用户账单id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long       id;

    //用户uid
    @Column(name = "uid", nullable = false)
    private Long       uid;

    // 关联id
    @Column(name = "link_id", nullable = false)
    private String     linkId;

    // 0 = 支出 1 = 获得
    @Column(name = "pm", nullable = false)
    private Integer    pm;

    // 账单标题
    @Column(name = "title", nullable = false)
    private String     title;

    // 明细种类
    @Column(name = "category", nullable = false)
    private String     category;

    // 明细类型
    @Column(name = "type", nullable = false)
    private String     type;

    // 明细数字
    @Column(name = "number", nullable = false)
    private BigDecimal number;

    // 剩余
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    // 备注
    @Column(name = "mark", nullable = false)
    private String     mark;

    // 添加时间
    @Column(name = "add_time", nullable = false)
    private Integer    addTime;

    // 0 = 带确定 1 = 有效 -1 = 无效
    @Column(name = "status", nullable = false)
    private Integer    status;

    // 店铺id
    @Column(name = "store_id", nullable = false)
    private Long       storeId;

    public void copy(UserBill source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
