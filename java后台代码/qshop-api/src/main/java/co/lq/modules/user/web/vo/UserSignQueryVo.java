package co.lq.modules.user.web.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 签到记录表 查询结果对象
 * </p>
 *
 * @author billy
 * @date 2019-12-05
 */
@Data
@ApiModel(value = "UserSignQueryVo对象", description = "签到记录表查询参数")
public class UserSignQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

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
