package co.lq.modules.shop.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author billy
* @date 2020-03-27
*/
@Entity
@Data
@Table(name="store_product_consult")
public class StoreProductConsult implements Serializable {

    /** 咨询编号 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 商品编号 */
    @Column(name = "product_id")
    private Long productId;

    /** 商品名称 */
    @Column(name = "product_name")
    private String productName;

    /** 咨询发布者会员编号(0：游客) */
    @Column(name = "uid",nullable = false)
    @NotNull
    private Long uid;

    /** 会员名称 */
    @Column(name = "member_name")
    private String memberName;

    /** 店铺编号 */
    @Column(name = "store_id")
    private Long storeId;

    /** 咨询发布者邮箱 */
    @Column(name = "email")
    private String email;

    /** 咨询内容 */
    @Column(name = "consult_content")
    private String consultContent;

    /** 咨询添加时间 */
    @Column(name = "consult_addtime")
    private Timestamp consultAddtime;

    /** 咨询回复内容 */
    @Column(name = "consult_reply")
    private String consultReply;

    /** 咨询回复时间 */
    @Column(name = "consult_reply_time")
    private Timestamp consultReplyTime;

    /** 0表示不匿名 1表示匿名 */
    @Column(name = "isanonymous")
    private Integer isanonymous;

    @Column(name = "is_del")
    private Integer isDel;

    @Column(name = "pic")
    private String pic;

    @Column(name = "attr")
    private String attr;

    @Column(name = "stars")
    private Integer stars;

    @Column(name = "oid")
    private Long oid;

    /** 1 商品 2 订单 */
    @Column(name = "type")
    private Integer type;

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

    public void copy(StoreProductConsult source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}