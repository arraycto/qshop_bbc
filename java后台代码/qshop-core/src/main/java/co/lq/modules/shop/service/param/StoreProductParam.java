package co.lq.modules.shop.service.param;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 砍价，拼团，秒杀等商品参数
 *
 * @author songbin
 * @since 2020年4月20日 下午4:47:59
 */
@Data
public class StoreProductParam implements Serializable {

    private static final long serialVersionUID = 1;

    private Long              id;

    private String            name;

    private BigDecimal        price;

    private Integer           stock;
}
