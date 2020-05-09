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
 * 砍价用户帮助表
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
@ApiModel(value = "StoreBargainUserHelp对象", description = "砍价用户帮助表")
public class StoreBargainUserHelp extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "砍价用户帮助表ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;

    @ApiModelProperty(value = "帮助的用户id")
    private Long              uid;

    @ApiModelProperty(value = "砍价产品ID")
    private Long              bargainId;

    @ApiModelProperty(value = "用户参与砍价表id")
    private Long              bargainUserId;

    @ApiModelProperty(value = "帮助砍价多少金额")
    private BigDecimal        price;

    @ApiModelProperty(value = "添加时间")
    private Integer           addTime;

}
