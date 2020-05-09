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
 * 用户任务完成记录表
 * </p>
 *
 * @author billy
 * @since 2019-12-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserTaskFinish对象", description = "用户任务完成记录表")
public class UserTaskFinish extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;

    @ApiModelProperty(value = "任务id")
    private Long              taskId;

    @ApiModelProperty(value = "用户id")
    private Long              uid;

    @ApiModelProperty(value = "是否有效")
    private Integer           status;

    @ApiModelProperty(value = "添加时间")
    private Integer           addTime;

}
