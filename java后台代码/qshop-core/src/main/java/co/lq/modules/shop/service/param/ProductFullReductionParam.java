package co.lq.modules.shop.service.param;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 商品基础促销规则配置
 *
 * @author songbin
 * @since 2020年4月10日 下午4:38:44
 */
@Data
public class ProductFullReductionParam implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 满多少价格
     */
    private BigDecimal        fullPrice;

    /**
     * 减多少价格
     */
    private BigDecimal        reducePrice;
}
