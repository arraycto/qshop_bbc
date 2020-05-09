package co.lq.modules.activity.entity;

import java.math.BigDecimal;
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
 * 商品秒杀产品表
 * </p>
 *
 * @author billy
 * @since 2019-12-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StoreSeckill对象", description = "商品秒杀产品表")
public class StoreSeckill extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品秒杀产品表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;

    @ApiModelProperty(value = "商品id")
    private Long              productId;

    @ApiModelProperty(value = "推荐图")
    private String            image;

    @ApiModelProperty(value = "轮播图")
    private String            images;

    @ApiModelProperty(value = "活动标题")
    private String            title;

    @ApiModelProperty(value = "简介")
    private String            info;

    @ApiModelProperty(value = "价格")
    private BigDecimal        price;

    @ApiModelProperty(value = "成本")
    private BigDecimal        cost;

    @ApiModelProperty(value = "原价")
    private BigDecimal        otPrice;

    @ApiModelProperty(value = "返多少积分")
    private BigDecimal        giveIntegral;

    @ApiModelProperty(value = "排序")
    private Integer           sort;

    @ApiModelProperty(value = "库存")
    private Integer           stock;

    @ApiModelProperty(value = "销量")
    private Integer           sales;

    @ApiModelProperty(value = "单位名")
    private String            unitName;

    @ApiModelProperty(value = "邮费")
    private BigDecimal        postage;

    @ApiModelProperty(value = "内容")
    private String            description;

    @ApiModelProperty(value = "开始时间")
    private Integer           startTime;

    @ApiModelProperty(value = "结束时间")
    private Integer           stopTime;

    @ApiModelProperty(value = "添加时间")
    private String            addTime;

    @ApiModelProperty(value = "产品状态")
    private Integer           status;

    @ApiModelProperty(value = "是否包邮")
    private Integer           isPostage;

    @ApiModelProperty(value = "删除 0未删除1已删除")
    private Integer           isDel;

    @ApiModelProperty(value = "最多秒杀几个")
    private Integer           num;

    @ApiModelProperty(value = "显示")
    private Integer           isShow;

    private Date              endTimeDate;

    private Date              startTimeDate;

}
