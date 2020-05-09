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
public class OrderRemarkParam implements Serializable {
    @NotBlank(message = "订单编号错误")
    private String orderId;
    @NotBlank(message = "备注必填")
    private String remark;
}
