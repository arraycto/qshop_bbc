package co.lq.modules.shop.domain;

import java.io.Serializable;
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
 * @date 2020-03-27
 */
@Entity
@Data
@Table(name = "order_return_reason")
public class OrderReturnReason implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long      id;

    /** 退货类型 */
    @Column(name = "name")
    private String    name;

    @Column(name = "sort")
    private Integer   sort;

    /** 状态：0->不启用；1->启用 */
    @Column(name = "status")
    private Integer   status;

    /** 添加时间 */
    @Column(name = "add_time", nullable = false)
    @NotNull
    private Timestamp addTime;

    /** 更新时间 */
    @Column(name = "modify_time", nullable = false)
    @NotNull
    private Timestamp modifyTime;

    /** 逻辑删除 */
    @Column(name = "deleted", nullable = false)
    @NotNull
    private Integer   deleted;

    public void copy(OrderReturnReason source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
