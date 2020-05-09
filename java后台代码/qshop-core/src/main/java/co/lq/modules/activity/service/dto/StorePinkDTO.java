package co.lq.modules.activity.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * @author billy
 * @date 2019-11-18
 */
@Data
public class StorePinkDTO implements Serializable {

    private Integer    id;

    // 用户id
    private Long       uid;

    // 订单id 生成
    private String     orderId;

    // 订单id  数据库
    private Integer    orderIdKey;

    private String     title;

    private String     nickname;

    private String     avatar;

    private Integer    countPeople;

    // 购买商品个数
    private Integer    totalNum;

    // 购买总金额
    private BigDecimal totalPrice;

    // 拼团产品id
    private long       cid;

    // 产品id
    private long       pid;

    // 拼图总人数
    private Integer    people;

    // 拼团产品单价
    private BigDecimal price;

    // 开始时间
    private String     addTime;

    private String     stopTime;

    // 团长id 0为团长
    private Integer    kId;

    // 是否发送模板消息0未发送1已发送
    private Integer    isTpl;

    // 是否退款 0未退款 1已退款
    private Integer    isRefund;

    // 状态1进行中2已完成3未完成
    private Integer    status;

    //所属店铺
    private Long       storeId;
}
