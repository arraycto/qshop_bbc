package co.lq.modules.shop.service.param;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 商品阶梯价
 *
 * @author songbin
 * @since 2020年4月10日 下午4:41:42
 */
@Data
public class ProductLadderParam implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 满足多少数量基数
     */
    private Integer           count;

    /**
     * 上限位置多少
     */
    private Integer           discount;

    /**
     * 价格
     */
    private BigDecimal        price;
}
