package co.lq.modules.shop.web.vo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 门店自提 查询结果对象
 * </p>
 *
 * @author billy
 * @date 2020-03-04
 */
@Data
@ApiModel(value = "SystemStoreQueryVo对象", description = "门店自提查询参数")
public class SystemStoreQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

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

    @ApiModelProperty(name = "店铺id")
    private Long              storeId;

    public String getLatlng() {
        return latitude + "," + longitude;
    }

    @ApiModelProperty(value = "每日营业开关时间")
    private String  dayTime;

    @ApiModelProperty(value = "添加时间")
    private Integer addTime;

    @ApiModelProperty(name = "结束时间")
    private Date    dayTimeEnd;

    @ApiModelProperty(name = "开始时间")
    private Date    dayTimeStart;

    @ApiModelProperty(name = "有效结束时间")
    private Date    validTimeEnd;

    @ApiModelProperty(name = "有效开始时间")
    private Date    validTimeStart;

}
