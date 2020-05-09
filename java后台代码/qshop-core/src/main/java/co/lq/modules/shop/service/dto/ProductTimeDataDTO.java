package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 商品统计
 *
 * @author songbin
 * @since 2020年4月7日 下午4:14:02
 */
@Data
public class ProductTimeDataDTO implements Serializable {
    private static final long serialVersionUID = 1;

    private Long              productId;
    private String            productInfo;
    private Integer           salesCount;
    private BigDecimal        salesAmount;
}
