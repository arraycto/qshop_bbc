package co.lq.modules.manage.web.param;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @ClassName OrderPriceParam
 * @Author billy
 * @Date 2019/11/26
 **/
@Data
public class OrderDeliveryParam implements Serializable {
    @NotBlank(message = "订单编号错误")
    private String orderId;
    @NotBlank(message = "快递单号必填")
    private String deliveryId;
    @NotBlank(message = "快递公司必填")
    private String deliveryName;
    @NotBlank(message = "快递方式必填")
    private String deliveryType;
}
