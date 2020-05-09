package co.lq.modules.shop.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import co.lq.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品属性值表
 * </p>
 *
 * @author billy
 * @since 2019-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StoreProductAttrValue对象", description = "商品属性值表")
public class StoreProductAttrValue extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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
    @TableField(value = "`unique`")
    private String            unique;

    @ApiModelProperty(value = "成本价")
    private BigDecimal        cost;

}
