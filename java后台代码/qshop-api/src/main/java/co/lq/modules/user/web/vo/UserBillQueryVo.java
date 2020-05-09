package co.lq.modules.user.web.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 用户账单表 查询结果对象
 * </p>
 *
 * @author billy
 * @date 2019-10-27
 */
@Data
@ApiModel(value = "UserBillQueryVo对象", description = "用户账单表查询参数")
public class UserBillQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户账单id")
    private Long              id;

    @ApiModelProperty(value = "0 = 支出 1 = 获得")
    private Integer           pm;

    @ApiModelProperty(value = "账单标题")
    private String            title;

    @ApiModelProperty(value = "明细数字")
    private BigDecimal        number;

    @ApiModelProperty(value = "备注")
    private String            mark;

    @ApiModelProperty(value = "添加时间")
    private Integer           addTime;

}
