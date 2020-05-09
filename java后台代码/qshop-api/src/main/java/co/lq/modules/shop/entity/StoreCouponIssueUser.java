package co.lq.modules.shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import co.lq.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 优惠券前台用户领取记录表
 * </p>
 *
 * @author billy
 * @since 2019-10-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StoreCouponIssueUser对象", description = "优惠券前台用户领取记录表")
public class StoreCouponIssueUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;

    @ApiModelProperty(value = "领取优惠券用户ID")
    private Long              uid;

    @ApiModelProperty(value = "优惠券前台领取ID")
    private Long              issueCouponId;

    @ApiModelProperty(value = "领取时间")
    private Integer           addTime;

}
