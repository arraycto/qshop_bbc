package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 店铺销售统计
 *
 * @author songbin
 * @since 2020年4月30日 下午8:48:11
 */
@Data
public class StoreOrderTimeDataDTO implements Serializable {

    private static final long serialVersionUID = 1;
    /**
     * 店铺id
     */
    private Long              shopId;

    /**
     * 营业额
     */
    private BigDecimal        totalAmount;

    /**
     * 店铺名称
     */
    private String            shopName;
}
