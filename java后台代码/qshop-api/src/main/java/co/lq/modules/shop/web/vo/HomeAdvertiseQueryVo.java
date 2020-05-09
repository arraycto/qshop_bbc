package co.lq.modules.shop.web.vo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 广告
 *
 * @author songbin
 * @since 2020年3月13日 下午10:23:03
 */
@Data
@ApiModel(value = "HomeAdvertiseQueryVo对象", description = "广告表查询参数")
public class HomeAdvertiseQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "广告表ID")
    private Integer           id;

    @ApiModelProperty(value = "广告名称")
    private String            name;

    @ApiModelProperty(value = "轮播位置")
    private Integer           type;

    @ApiModelProperty(value = "图片")
    private String            pic;

    @ApiModelProperty(value = "开始时间")
    private Date              startTime;

    @ApiModelProperty(value = "结束时间")
    private Date              endTime;

    @ApiModelProperty(value = "状态")
    private Integer           status;

    @ApiModelProperty(value = "地址")
    private String            url;

    @ApiModelProperty(value = "备注")
    private String            note;

    @ApiModelProperty(value = "排序")
    private String            sort;

    @ApiModelProperty(value = "店铺id")
    private Long              storeId;
}
