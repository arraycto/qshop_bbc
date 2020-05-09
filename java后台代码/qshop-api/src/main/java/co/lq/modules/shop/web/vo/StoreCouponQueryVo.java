package co.lq.modules.shop.web.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 优惠券表 查询结果对象
 * </p>
 *
 * @author billy
 * @date 2019-10-27
 */
@Data
@ApiModel(value = "StoreCouponQueryVo对象", description = "优惠券表查询参数")
public class StoreCouponQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "优惠券表ID")
    private Long              id;

    @ApiModelProperty(value = "优惠券名称")
    private String            title;

    @ApiModelProperty(value = "兑换消耗积分值")
    private Integer           integral;

    @ApiModelProperty(value = "兑换的优惠券面值")
    private BigDecimal        couponPrice;

    @ApiModelProperty(value = "最低消费多少金额可用优惠券")
    private BigDecimal        useMinPrice;

    @ApiModelProperty(value = "优惠券有效期限（单位：天）")
    private Integer           couponTime;

    @ApiModelProperty(value = "排序")
    private Integer           sort;

    @ApiModelProperty(value = "状态（0：关闭，1：开启）")
    private Boolean           status;

    @ApiModelProperty(value = "兑换项目添加时间")
    private Integer           addTime;

    @ApiModelProperty(value = "是否删除")
    private Boolean           isDel;

    @ApiModelProperty(name = "店铺id")
    private Integer           storeId;

}
