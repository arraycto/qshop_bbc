package co.lq.modules.user.web.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 用户任务完成记录表 查询结果对象
 * </p>
 *
 * @author billy
 * @date 2019-12-07
 */
@Data
@ApiModel(value = "UserTaskFinishQueryVo对象", description = "用户任务完成记录表查询参数")
public class UserTaskFinishQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long              id;

    @ApiModelProperty(value = "任务id")
    private Long              taskId;

    @ApiModelProperty(value = "用户id")
    private Long              uid;

    @ApiModelProperty(value = "是否有效")
    private Boolean           status;

    @ApiModelProperty(value = "添加时间")
    private Integer           addTime;

}
