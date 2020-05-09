package co.lq.modules.shop.web.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 每个购物车商品的购买汇总
 *
 * @author songbin
 * @since 2020年4月25日 下午2:23:09
 */
@Data
public class CartGoodsDTO implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 商品id
     */
    private Long              productId;

    /**
     * 商品名称
     */
    private String            productName;

    /**
     * 购买件数
     */
    private Integer           num;

    /**
     * 购买的总金额
     */
    private BigDecimal        amount;

}
