package co.lq.modules.shop.web.vo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 店铺保存信息
 *
 * @author songbin
 * @since 2020年3月31日 下午2:36:56
 */
@Data
@ApiModel(value = "StoreVo对象", description = "店铺表保存参数")
public class StoreVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "平台类目表ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;

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

    @ApiModelProperty(value = "状态")
    private Integer           status;

    @ApiModelProperty(value = "用户id")
    private Long              uid;

    @ApiModelProperty(value = "类目id")
    private Long              catalogId;

    @ApiModelProperty(value = "联系QQ")
    private String            contactQq;

    @ApiModelProperty(value = "精度")
    private String            addressLng;

    @ApiModelProperty(value = "店铺二维码")
    private String            contactQrcode;

    @ApiModelProperty(value = "描述")
    private String            description;

    @ApiModelProperty(value = "店铺管理员账号")
    private String            username;

    @ApiModelProperty(value = "店铺管理员密码")
    private String            password;
}
