package co.lq.modules.shop.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author billy
* @date 2020-03-27
*/
@Data
public class StoreProductConsultDTO implements Serializable {

    /** 咨询编号 */
    private Long id;

    /** 商品编号 */
    private Long productId;

    /** 商品名称 */
    private String productName;

    /** 咨询发布者会员编号(0：游客) */
    private Long uid;

    /** 会员名称 */
    private String memberName;

    /** 店铺编号 */
    private Long storeId;

    /** 咨询发布者邮箱 */
    private String email;

    /** 咨询内容 */
    private String consultContent;

    /** 咨询添加时间 */
    private Timestamp consultAddtime;

    /** 咨询回复内容 */
    private String consultReply;

    /** 咨询回复时间 */
    private Timestamp consultReplyTime;

    /** 0表示不匿名 1表示匿名 */
    private Integer isanonymous;

    private Integer isDel;

    private String pic;

    private String attr;

    private Integer stars;

    private Long oid;

    /** 1 商品 2 订单 */
    private Integer type;

    /** 添加时间 */
    private Timestamp addTime;

    /** 更新时间 */
    private Timestamp modifyTime;

    /** 逻辑删除 */
    private Integer deleted;
}