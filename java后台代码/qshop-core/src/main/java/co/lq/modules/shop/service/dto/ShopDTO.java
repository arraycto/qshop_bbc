package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

/**
 * @author billy
 * @date 2020-03-10
 */
@Data
public class ShopDTO implements Serializable {

    private Long       id;

    /** 注册类型 */
    private Integer    registerType;

    /** 到期时间 */
    private Timestamp  expireTime;

    /** 尝试时间 */
    private Timestamp  tryTime;

    /** 联系电话 */
    private String     contactMobile;

    /** 是否选中 */
    private Integer    isChecked;

    /** 服务电话 */
    private String     servicePhone;

    /** 地址名 */
    private String     addressLat;

    /** 联系人名 */
    private String     contactName;

    /** 删除时间 */
    private Timestamp  deleteTime;

    private Integer    isStar;

    /** 尝试 */
    private Integer    isTry;

    /** 图标 */
    private String     logo;

    /** 地址细节 */
    private String     addressDetail;

    private String     name;

    private Integer    uid;

    /** 联系QQ */
    private String     contactQq;

    private String     addressLng;

    private Timestamp  lastLoginTime;

    /** 支持电话 */
    private String     supportPhone;

    private String     contactQrcode;

    /** 描述 */
    private String     description;

    private Integer    collect;

    private Integer    hit;

    private Integer    goodsCount;

    private Integer    memberCount;

    private Integer    orderCount;

    private BigDecimal payAmount;

    private Integer    articleCount;

    /** 精品店铺标识,0:否，1:是 */
    private Integer    isBoutique;

    /** 添加时间 */
    private Timestamp  addTime;

    /** 更新时间 */
    private Timestamp  modifyTime;

    /** 逻辑删除 */
    private Integer    deleted;

    /** 正面身份证 **/
    private String     ownerIdentity1;

    /** 反面身份证 **/
    private String     ownerIdentity2;

    private String     shopStatus;

    private String     closedStatus;

    public String getLabel() {
        return name;
    }
}
