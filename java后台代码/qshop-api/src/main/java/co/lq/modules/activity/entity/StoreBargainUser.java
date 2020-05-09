package co.lq.modules.activity.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import co.lq.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 用户参与砍价表
 * </p>
 *
 * @author billy
 * @since 2019-12-21
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "StoreBargainUser对象", description = "用户参与砍价表")
public class StoreBargainUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户参与砍价表ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;

    @ApiModelProperty(value = "用户ID")
    private Long              uid;

    @ApiModelProperty(value = "砍价产品id")
    private Long              bargainId;

    @ApiModelProperty(value = "砍价的最低价")
    private BigDecimal        bargainPriceMin;

    @ApiModelProperty(value = "砍价金额")
    private BigDecimal        bargainPrice;

    @ApiModelProperty(value = "砍掉的价格")
    private BigDecimal        price;

    @ApiModelProperty(value = "状态 1参与中 2 活动结束参与失败 3活动结束参与成功")
    private Integer           status;

    @ApiModelProperty(value = "参与时间")
    private Integer           addTime;

    @ApiModelProperty(value = "是否取消")
    private Integer           isDel;

}
