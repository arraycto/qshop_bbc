package co.lq.modules.shop.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import co.lq.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 门店自提
 * </p>
 *
 * @author billy
 * @since 2020-03-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SystemGroupData对象", description = "门店自提")
public class SystemStore extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;

    @ApiModelProperty(value = "门店名称")
    private String            name;

    @ApiModelProperty(value = "简介")
    private String            introduction;

    @ApiModelProperty(value = "手机号码")
    private String            phone;

    @ApiModelProperty(value = "省市区")
    private String            address;

    @ApiModelProperty(value = "详细地址")
    private String            detailedAddress;

    @ApiModelProperty(value = "门店logo")
    private String            image;

    @ApiModelProperty(value = "纬度")
    private String            latitude;

    @ApiModelProperty(value = "经度")
    private String            longitude;

    @ApiModelProperty(value = "核销有效日期")
    private String            validTime;

    @ApiModelProperty(value = "每日营业开关时间")
    private String            dayTime;

    @ApiModelProperty(value = "添加时间")
    private Integer           addTime;

    @ApiModelProperty(value = "是否显示")
    private Integer           isShow;

    @ApiModelProperty(value = "是否删除")
    private Integer           isDel;

    private Date              dayTimeEnd;

    private Date              dayTimeStart;

    private Date              validTimeEnd;

    private Date              validTimeStart;

    @ApiModelProperty(name = "店铺id")
    private Long              storeId;

}
