package co.lq.modules.manage.web.param;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @ClassName OrderRefundParam
 * @Author billy
 * @Date 2019/11/26
 **/
@Data
public class OrderRefundParam implements Serializable {
    @NotBlank(message = "订单编号错误")
    private String  orderId;
    @NotNull(message = "退款金额必填")
    private Double  price;
    @NotNull(message = "参数错误")
    private Integer type;
}
