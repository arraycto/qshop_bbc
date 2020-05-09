package co.lq.modules.shop.service.param;

import java.io.Serializable;

import lombok.Data;

/**
 * 商品规格属性值选中的列表
 *
 * @author songbin
 * @since 2020年4月10日 下午4:44:07
 */
@Data
public class ProductAttributeValueParam implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 商品属性id
     */
    private Long              productAttributeId;

    /**
     * 属性名称
     */
    private String            name;

    /**
     * 属性类型（1-规格，2-属性)
     */
    private Integer           type;

    /**
     * 属性值，逗号隔开，例如(128ml,335ml,400ml,450ml,750ml)
     */
    private String            value;
}
