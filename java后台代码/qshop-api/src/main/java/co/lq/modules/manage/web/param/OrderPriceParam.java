package co.lq.modules.manage.web.param;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @ClassName OrderPriceParam
 * @Author billy
 * @Date 2019/11/26
 **/
@Data
public class OrderPriceParam implements Serializable {
    @NotBlank(message = "订单编号错误")
    private String orderId;
    @NotNull(message = "修改价格必填")
    private Double price;
}
