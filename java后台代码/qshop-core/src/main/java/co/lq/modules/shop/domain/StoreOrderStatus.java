package co.lq.modules.shop.domain;

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
 * @date 2019-11-02
 */
@Entity
@Data
@Table(name = "store_order_status")
public class StoreOrderStatus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long    id;

    // 订单id
    @Column(name = "oid", nullable = false)
    private Long    oid;

    // 操作类型
    @Column(name = "change_type", nullable = false)
    private String  changeType;

    // 操作备注
    @Column(name = "change_message", nullable = false)
    private String  changeMessage;

    // 操作时间
    @Column(name = "change_time", nullable = false)
    private Integer changeTime;

    public void copy(StoreOrderStatus source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
