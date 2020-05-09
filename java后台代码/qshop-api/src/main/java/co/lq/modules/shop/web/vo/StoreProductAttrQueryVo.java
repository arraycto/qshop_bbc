package co.lq.modules.shop.web.vo;

import java.io.Serializable;
import java.util.List;

import co.lq.modules.shop.web.dto.AttrValueDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 商品属性表 查询结果对象
 * </p>
 *
 * @author billy
 * @date 2019-10-23
 */
@Data
@ApiModel(value = "StoreProductAttrQueryVo对象", description = "商品属性表查询参数")
public class StoreProductAttrQueryVo implements Serializable {
    private static final long  serialVersionUID = 1L;

    private Long               id;

    @ApiModelProperty(value = "商品ID")
    private Long               productId;

    @ApiModelProperty(value = "属性名")
    private String             attrName;

    @ApiModelProperty(value = "属性值")
    private String             attrValues;

    private List<AttrValueDTO> attrValue;

    private List<String>       attrValueArr;

}
