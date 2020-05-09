package co.lq.modules.shop.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import co.lq.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 店铺表
 *
 * @author songbin
 * @since 2020年3月12日 下午8:48:41
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Store对象", description = "平台店铺表")
public class Store extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "平台类目表ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;

    @ApiModelProperty(value = "注册类型")
    private Integer           registerType;

    @ApiModelProperty(value = "尝试时间")
    private Timestamp         tryTime;

    @ApiModelProperty(value = "过期时间")
    private Timestamp         expireTime;

    @ApiModelProperty(value = "联系电话")
    private String            contactMobile;

    @ApiModelProperty(value = "是否精品推荐")
    private Integer           isChecked;

    @ApiModelProperty(value = "类目图片")
    private String            servicePhone;

    @ApiModelProperty(value = "纬度")
    private String            addressLat;

    @ApiModelProperty(value = "联系人名")
    private String            contactName;

    @ApiModelProperty(value = "是否星标")
    private Integer           isStar;

    @ApiModelProperty(value = "图标")
    private String            logo;

    @ApiModelProperty(value = "地址细节")
    private String            addressDetail;

    @ApiModelProperty(value = "名称")
    private String            name;

    @ApiModelProperty(value = "用户id")
    private Long              uid;

    @ApiModelProperty(value = "联系QQ")
    private String            contactQq;

    @ApiModelProperty(value = "精度")
    private String            addressLng;

    @ApiModelProperty(value = "店铺二维码")
    private String            contactQrcode;

    @ApiModelProperty(value = "描述")
    private String            description;

    @ApiModelProperty(value = "关注次数")
    private Integer           collect;

    @ApiModelProperty(value = "点击次数")
    private Integer           hit;

    @ApiModelProperty(value = "店铺商品数")
    private Integer           goodsCount;

    @ApiModelProperty(value = "店铺会员数")
    private Integer           memberCount;

    @ApiModelProperty(value = "店铺订单数")
    private Integer           orderCount;

    @ApiModelProperty(value = "店铺营业额")
    private BigDecimal        payAmount;

    @ApiModelProperty(value = "精品店铺标识,0:否，1:是")
    private Integer           isBoutique;

    @ApiModelProperty(value = "创建时间")
    private Timestamp         addTime;

    @ApiModelProperty(value = "修改时间")
    private Timestamp         modifyTime;

    @ApiModelProperty(value = "逻辑删除")
    private Integer           deleted;

    @ApiModelProperty(value = "正面身份证")
    private String            ownerIdentity1;

    @ApiModelProperty(value = "反面身份证")
    private String            ownerIdentity2;
}
