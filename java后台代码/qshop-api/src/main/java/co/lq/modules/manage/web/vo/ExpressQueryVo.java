package co.lq.modules.manage.web.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 快递公司表 查询结果对象
 * </p>
 *
 * @author billy
 * @date 2019-12-13
 */
@Data
@ApiModel(value = "ExpressQueryVo对象", description = "快递公司表查询参数")
public class ExpressQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "快递公司id")
    private Integer           id;

    @ApiModelProperty(value = "快递公司简称")
    private String            code;

    @ApiModelProperty(value = "快递公司全称")
    private String            name;

    @ApiModelProperty(value = "排序")
    private Integer           sort;

    @ApiModelProperty(value = "是否显示")
    private Boolean           isShow;

}
