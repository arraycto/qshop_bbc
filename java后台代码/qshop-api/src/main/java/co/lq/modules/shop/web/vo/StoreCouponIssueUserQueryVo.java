package co.lq.modules.shop.web.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 优惠券前台用户领取记录表 查询结果对象
 * </p>
 *
 * @author billy
 * @date 2019-10-27
 */
@Data
@ApiModel(value = "StoreCouponIssueUserQueryVo对象", description = "优惠券前台用户领取记录表查询参数")
public class StoreCouponIssueUserQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long              id;

    @ApiModelProperty(value = "领取优惠券用户ID")
    private Long              uid;

    @ApiModelProperty(value = "优惠券前台领取ID")
    private Long              issueCouponId;

    @ApiModelProperty(value = "领取时间")
    private Integer           addTime;

}
