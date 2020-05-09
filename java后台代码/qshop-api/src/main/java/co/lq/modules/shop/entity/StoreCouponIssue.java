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
 * 优惠券前台领取表
 * </p>
 *
 * @author billy
 * @since 2019-10-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StoreCouponIssue对象", description = "优惠券前台领取表")
public class StoreCouponIssue extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;

    @ApiModelProperty(value = "优惠券ID")
    private Long              cid;

    @ApiModelProperty(value = "优惠券领取开启时间")
    private Integer           startTime;

    @ApiModelProperty(value = "优惠券领取结束时间")
    private Integer           endTime;

    @ApiModelProperty(value = "优惠券领取数量")
    private Integer           totalCount;

    @ApiModelProperty(value = "优惠券剩余领取数量")
    private Integer           remainCount;

    @ApiModelProperty(value = "是否无限张数")
    private Boolean           isPermanent;

    @ApiModelProperty(value = "1 正常 0 未开启 -1 已无效")
    private Boolean           status;

    private Boolean           isDel;

    @ApiModelProperty(value = "优惠券添加时间")
    private Integer           addTime;

}
