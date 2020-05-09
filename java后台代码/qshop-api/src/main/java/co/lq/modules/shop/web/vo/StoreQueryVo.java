package co.lq.modules.shop.web.vo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 店铺查询
 *
 * @author songbin
 * @since 2020年3月12日 下午8:45:50
 */
@Data
@ApiModel(value = "StoreQueryVo对象", description = "店铺表查询参数")
public class StoreQueryVo implements Serializable {
    private static final long          serialVersionUID = 1L;

    @ApiModelProperty(value = "店铺表ID")
    private Long                       id;

    @ApiModelProperty(value = "用户id")
    private Long                       uid;

    @ApiModelProperty(value = "店铺名称")
    private String                     name;

    @ApiModelProperty(value = "类目id")
    private Long                       catalogId;

    @ApiModelProperty(value = "店铺门头照")
    private String                     logo;

    @ApiModelProperty(value = "正面身份证")
    private String                     ownerIdentity1;

    @ApiModelProperty(value = "反面身份证")
    private String                     ownerIdentity2;

    @ApiModelProperty(value = "联系电话")
    private String                     contactMobile;

    @ApiModelProperty(value = "类目图片")
    private String                     servicePhone;

    @ApiModelProperty(value = "联系人名")
    private String                     contactName;

    @ApiModelProperty(value = "地址细节")
    private String                     addressDetail;

    @ApiModelProperty(value = "状态")
    private Integer                    status;

    @ApiModelProperty(value = "联系QQ")
    private String                     contactQq;

    @ApiModelProperty(value = "精度")
    private String                     addressLng;

    @ApiModelProperty(value = "店铺二维码")
    private String                     contactQrcode;

    @ApiModelProperty(value = "描述")
    private String                     description;

    /**
     * 关注次数
     */
    @ApiModelProperty(value = "关注次数")
    private Integer                    collect;

    /**
     * 点击次数
     */
    @ApiModelProperty(value = "点击次数")
    private Integer                    hit;

    /**
     * 店铺商品数
     */
    @ApiModelProperty(value = "店铺商品数")
    private Integer                    goodsCount;

    /**
     * 店铺会员数
     */
    @ApiModelProperty(value = "店铺会员数")
    private Integer                    memberCount;

    /**
     * 店铺订单数
     */
    @ApiModelProperty(value = "店铺订单数")
    private Integer                    orderCount;

    /**
     * 店铺是否关注 0-未关注，1-关注
     */
    @ApiModelProperty(value = "店铺订单数")
    private Integer                    shopCollect;

    /**
     * 审核状态 0 －待审核， 1-审核通过， 2-审核不通过
     */
    @ApiModelProperty(value = "审核状态")
    private Integer                    verifyStatus;

    /**
     * 关店状态 0- 关店， 1- 存续
     */
    @ApiModelProperty(value = "关店状态")
    private Integer                    closed;

    /**
     * 首页banner
     */
    @ApiModelProperty(value = "首页banner")
    private List<HomeAdvertiseQueryVo> homeAdvertiseQueryVoList;

    @ApiModelProperty(value = "店铺关注字段")
    private Boolean                    isChecked;

}
