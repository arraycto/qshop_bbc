package co.lq.utils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 店铺
 *
 * @author songbin
 * @since 2020年3月12日 下午9:14:27
 */
@Data
public class StoreDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 平台类目表ID
     */
    private Integer           id;

    /**
     * 到期时间
     */
    private Integer           registerType;

    /**
     * 尝试次数
     */
    private Timestamp         tryTime;

    /**
     * 联系电话
     */
    private String            contactMobile;

    /**
     * 是否精品推荐
     */
    private Integer           isChecked;

    /**
     * 类目图片
     */
    private String            servicePhone;

    /**
     * 纬度
     */
    private String            addressLat;

    /**
     * 联系人名
     */
    private String            contactName;

    /**
     * 是否星标
     */
    private Integer           isStar;

    /**
     * 图标
     */
    private String            logo;

    /**
     * 地址细节
     */
    private String            addressDetail;

    /**
     * 名称
     */
    private String            name;

    /**
     * 用户id
     */
    private Integer           uid;

    /**
     * 类目id
     */
    private Long              catalogId;

    /**
     * 联系QQ
     */
    private String            contactQq;

    /**
     * 精度
     */
    private String            addressLng;

    /**
     * 店铺二维码
     */
    private String            contactQrcode;

    /**
     * 描述
     */
    private String            description;

    /**
     * 关注次数
     */
    private Integer           collect;

    /**
     * 点击次数
     */
    private Integer           hit;

    /**
     * 店铺商品数
     */
    private Integer           goodsCount;

    /**
     * 店铺会员数
     */
    private Integer           memberCount;

    /**
     * 店铺订单数
     */
    private Integer           orderCount;

    /**
     * 店铺营业额
     */
    private BigDecimal        payAmount;

    /**
     * 精品店铺标识,0:否，1:是
     */
    private Integer           isBoutique;

    /**
     * 创建时间
     */
    private Timestamp         addTime;

    /**
     * 修改时间
     */
    private Timestamp         modifyTime;

    /**
     * 逻辑删除
     */
    private Integer           deleted;

}
