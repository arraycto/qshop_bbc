package co.lq.modules.shop.web.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 平台类目查询条件
 *
 * @author songbin
 * @since 2020年3月11日 下午5:37:31
 */
@Data
@ApiModel(value = "CagalogQueryVo对象", description = "平台类目表查询参数")
public class CatalogQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品分类表ID")
    private Integer           id;

    @ApiModelProperty(value = "父id")
    private Integer           pid;

    @ApiModelProperty(value = "分类名称")
    private String            name;
}
