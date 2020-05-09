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
 * 平台首页广告
 *
 * @author songbin
 * @since 2020年3月13日 下午8:38:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "HomeAdvertise对象", description = "平台首页广告表")
public class HomeAdvertise extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "平台首页广告表ID")
    @TableId(value = "id", type = IdType.AUTO)
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
