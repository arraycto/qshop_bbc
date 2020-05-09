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
 * @date 2019-11-03
 */
@Entity
@Data
@Table(name = "store_product_reply")
public class StoreProductReply implements Serializable {

    // 评论ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long    id;

    // 用户ID
    @Column(name = "uid", nullable = false)
    private Long    uid;

    // 订单ID
    @Column(name = "oid", nullable = false)
    private Long    oid;

    // 唯一id
    @Column(name = "`unique`", nullable = false)
    private String  unique;

    // 产品id
    @Column(name = "product_id", nullable = false)
    private Long    productId;

    // 某种商品类型(普通商品、秒杀商品）
    @Column(name = "reply_type", nullable = false)
    private String  replyType;

    // 商品分数
    @Column(name = "product_score", nullable = false)
    private Integer productScore;

    // 服务分数
    @Column(name = "service_score", nullable = false)
    private Integer serviceScore;

    // 评论内容
    @Column(name = "comment", nullable = false)
    private String  comment;

    // 评论图片
    @Column(name = "pics", nullable = false)
    private String  pics;

    // 评论时间
    @Column(name = "add_time", nullable = false)
    private Integer addTime;

    // 管理员回复内容
    @Column(name = "merchant_reply_content")
    private String  merchantReplyContent;

    // 管理员回复时间
    @Column(name = "merchant_reply_time")
    private Integer merchantReplyTime;

    // 0未删除1已删除
    @Column(name = "is_del", nullable = false)
    private Integer isDel;

    // 0未回复1已回复
    @Column(name = "is_reply", nullable = false)
    private Integer isReply;

    /** 店铺id */
    @Column(name = "store_id")
    private Long    storeId;

    public void copy(StoreProductReply source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
