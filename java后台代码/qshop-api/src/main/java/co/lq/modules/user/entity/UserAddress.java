package co.lq.modules.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import co.lq.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户地址表
 * </p>
 *
 * @author billy
 * @since 2019-10-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserAddress对象", description = "用户地址表")
public class UserAddress extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户地址id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;

    @ApiModelProperty(value = "用户id")
    private Long              uid;

    @ApiModelProperty(value = "收货人姓名")
    private String            realName;

    @ApiModelProperty(value = "收货人电话")
    private String            phone;

    @ApiModelProperty(value = "收货人所在省")
    private String            province;

    @ApiModelProperty(value = "收货人所在市")
    private String            city;

    @ApiModelProperty(value = "收货人所在区")
    private String            district;

    @ApiModelProperty(value = "收货人详细地址")
    private String            detail;

    @ApiModelProperty(value = "邮编")
    private String            postCode;

    @ApiModelProperty(value = "经度")
    private String            longitude;

    @ApiModelProperty(value = "纬度")
    private String            latitude;

    @ApiModelProperty(value = "是否默认")
    private Integer           isDefault;

    @ApiModelProperty(value = "是否删除")
    private Integer           isDel;

    @ApiModelProperty(value = "添加时间")
    private Integer           addTime;

}
