package co.lq.modules.shop.web.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 配置表 查询结果对象
 * </p>
 *
 * @author billy
 * @date 2019-10-19
 */
@Data
@ApiModel(value = "SystemConfigQueryVo对象", description = "配置表查询参数")
public class SystemConfigQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "配置id")
    private Integer           id;

    @ApiModelProperty(value = "字段名称")
    private String            menuName;

    @ApiModelProperty(value = "默认值")
    private String            value;

    @ApiModelProperty(value = "排序")
    private Integer           sort;

    @ApiModelProperty(value = "是否隐藏")
    private Boolean           status;

}
