package co.lq.modules.activity.domain;

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
 * @date 2019-11-18
 */
@Entity
@Data
@Table(name = "store_pink")
public class StorePink implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long       id;

    // 用户id
    @Column(name = "uid", nullable = false)
    private Long       uid;

    // 订单id 生成
    @Column(name = "order_id", nullable = false)
    private String     orderId;

    // 订单id  数据库
    @Column(name = "order_id_key", nullable = false)
    private Long       orderIdKey;

    // 购买商品个数
    @Column(name = "total_num", nullable = false)
    private Integer    totalNum;

    // 购买总金额
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    // 拼团产品id
    @Column(name = "cid", nullable = false)
    private Long       cid;

    // 产品id
    @Column(name = "pid", nullable = false)
    private Long       pid;

    // 拼图总人数
    @Column(name = "people", nullable = false)
    private Integer    people;

    // 拼团产品单价
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    // 开始时间
    @Column(name = "add_time", nullable = false)
    private String     addTime;

    @Column(name = "stop_time", nullable = false)
    private String     stopTime;

    // 团长id 0为团长
    @Column(name = "k_id", nullable = false)
    private Long       kId;

    // 是否发送模板消息0未发送1已发送
    @Column(name = "is_tpl", nullable = false)
    private Integer    isTpl;

    // 是否退款 0未退款 1已退款
    @Column(name = "is_refund", nullable = false)
    private Integer    isRefund;

    // 状态1进行中2已完成3未完成
    @Column(name = "status", nullable = false)
    private Integer    status;

    // 店铺id
    @Column(name = "store_id", nullable = false)
    private Long       storeId;

    public void copy(StorePink source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
