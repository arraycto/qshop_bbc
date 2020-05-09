package co.lq.modules.shop.service.param;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 商品规格sku列表
 *
 * @author songbin
 * @since 2020年4月10日 下午4:47:29
 */
@Data
public class SkuStockParam implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 第一个规格值
     */
    private String            sp1;

    /**
     * 第二个规格值
     */
    private String            sp2;

    /**
     * 第三个规格值
     */
    private String            sp3;

    /**
     * 价格
     */
    private BigDecimal        price;

    /**
     * 销量
     */
    private Integer           sales;

    /**
     * 成本
     */
    private BigDecimal        cost;

    /**
     * 条码
     */
    private String            barCode;

    /**
     * 属性图片
     */
    private String[]          pic;

    /**
     * 库存
     */
    private Integer           stock;
}
