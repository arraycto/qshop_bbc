package co.lq.modules.shop.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

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
 * @date 2020-03-10
 */
@Entity
@Data
@Table(name = "store")
public class Shop implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long       id;

    /** 注册类型 */
    @Column(name = "register_type")
    private Integer    registerType;

    /** 到期时间 */
    @Column(name = "expire_time")
    private Timestamp  expireTime;

    /** 尝试时间 */
    @Column(name = "try_time")
    private Timestamp  tryTime;

    /** 联系电话 */
    @Column(name = "contact_mobile")
    private String     contactMobile;

    /** 是否选中 */
    @Column(name = "is_checked")
    private Integer    isChecked;

    /** 服务电话 */
    @Column(name = "service_phone")
    private String     servicePhone;

    /** 地址纬度 */
    @Column(name = "address_lat")
    private String     addressLat;

    /** 联系人名 */
    @Column(name = "contact_name")
    private String     contactName;

    /** 删除时间 */
    @Column(name = "delete_time")
    private Timestamp  deleteTime;

    @Column(name = "is_star")
    private Integer    isStar;

    /** 尝试 */
    @Column(name = "is_try")
    private Integer    isTry;

    /** 图标 */
    @Column(name = "logo")
    private String     logo;

    /** 地址细节 */
    @Column(name = "address_detail")
    private String     addressDetail;

    @Column(name = "name")
    private String     name;

    @Column(name = "uid")
    private Integer    uid;

    /** 联系QQ */
    @Column(name = "contact_qq")
    private String     contactQq;

    @Column(name = "address_lng")
    private String     addressLng;

    @Column(name = "last_login_time")
    private Timestamp  lastLoginTime;

    /** 支持电话 */
    @Column(name = "support_phone")
    private String     supportPhone;

    @Column(name = "contact_qrcode")
    private String     contactQrcode;

    /** 描述 */
    @Column(name = "description")
    private String     description;

    @Column(name = "collect")
    private Integer    collect;

    @Column(name = "hit")
    private Integer    hit;

    @Column(name = "goods_count")
    private Integer    goodsCount;

    @Column(name = "member_count")
    private Integer    memberCount;

    @Column(name = "order_count")
    private Integer    orderCount;

    @Column(name = "pay_amount")
    private BigDecimal payAmount;

    @Column(name = "article_count")
    private Integer    articleCount;

    /** 精品店铺标识,0:否，1:是 */
    @Column(name = "is_boutique", nullable = false)
    private Integer    isBoutique;

    /** 添加时间 */
    @Column(name = "add_time")
    private Timestamp  addTime;

    /** 更新时间 */
    @Column(name = "modify_time")
    private Timestamp  modifyTime;

    /** 逻辑删除 */
    @Column(name = "deleted", nullable = false)
    private Integer    deleted;

    /** 正面身份证 **/
    @Column(name = "owner_identity1")
    private String     ownerIdentity1;

    /** 反面身份证 **/
    @Column(name = "owner_identity2")
    private String     ownerIdentity2;

    public void copy(Shop source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
