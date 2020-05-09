package co.lq.modules.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import co.lq.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 签到记录表
 * </p>
 *
 * @author billy
 * @since 2019-12-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserSign对象", description = "签到记录表")
public class UserSign extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;

    @ApiModelProperty(value = "用户uid")
    private Long              uid;

    @ApiModelProperty(value = "签到说明")
    private String            title;

    @ApiModelProperty(value = "获得积分")
    private Integer           number;

    @ApiModelProperty(value = "剩余积分")
    private Integer           balance;

    @ApiModelProperty(value = "添加时间")
    private Integer           addTime;

}
