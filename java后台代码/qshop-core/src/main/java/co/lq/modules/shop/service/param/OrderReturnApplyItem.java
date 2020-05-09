package co.lq.modules.shop.service.param;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 订单退货申请明细
 *
 * @author songbin
 * @since 2020年5月5日 下午6:25:18
 */
@Data
public class OrderReturnApplyItem implements Serializable {
    private static final long serialVersionUID = 1;

    private Long              productId;

    private String            productPic;

    private String            productName;

    private String            productBrand;

    private String            productAttr;

    private Integer           productCount;

    private BigDecimal        productPrice;

    private BigDecimal        productRealPrice;
}
