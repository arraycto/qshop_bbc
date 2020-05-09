package co.lq.modules.shop.param;

import java.math.BigDecimal;

import co.lq.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单退货申请明细
 *
 * @author songbin
 * @since 2020年5月5日 下午5:33:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OrderReturnApplyItem对象", description = "订单退货申请明细")
public class OrderReturnApplyItem extends BaseEntity {
    private static final long serialVersionUID = 1;

    @ApiModelProperty(value = "退货商品id")
    private Long              productId;

    @ApiModelProperty(value = "商品图片")
    private String            productPic;

    @ApiModelProperty(value = "商品名称")
    private String            productName;

    @ApiModelProperty(value = "商品品牌")
    private String            productBrand;

    @ApiModelProperty(value = "商品销售属性：颜色：红色；尺码：xl;")
    private String            productAttr;

    @ApiModelProperty(value = "退货数量")
    private Integer           productCount;

    @ApiModelProperty(value = "商品单价")
    private BigDecimal        productPrice;

    @ApiModelProperty(value = "商品实际支付单价")
    private BigDecimal        productRealPrice;
}
