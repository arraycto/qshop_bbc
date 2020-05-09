package co.lq.modules.shop.web.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 商品属性值表 查询结果对象
 * </p>
 *
 * @author billy
 * @date 2019-10-23
 */
@Data
@ApiModel(value = "StoreProductAttrValueQueryVo对象", description = "商品属性值表查询参数")
public class StoreProductAttrValueQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long              id;

    @ApiModelProperty(value = "商品ID")
    private Long              productId;

    @ApiModelProperty(value = "商品属性索引值 (attr_value|attr_value[|....])")
    private String            suk;

    @ApiModelProperty(value = "属性对应的库存")
    private Integer           stock;

    @ApiModelProperty(value = "销量")
    private Integer           sales;

    @ApiModelProperty(value = "属性金额")
    private BigDecimal        price;

    @ApiModelProperty(value = "图片")
    private String            image;

    @ApiModelProperty(value = "唯一值")
    private String            unique;

    @ApiModelProperty(value = "成本价")
    private BigDecimal        cost;

}
