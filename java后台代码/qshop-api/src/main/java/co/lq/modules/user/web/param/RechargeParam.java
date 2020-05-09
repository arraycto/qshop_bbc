package co.lq.modules.user.web.param;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @ClassName RechargeParam
 * @Author billy
 * @Date 2019/12/8
 **/
@Data
public class RechargeParam implements Serializable {
    private String from;

    @NotNull(message = "金额必填")
    @Min(value = 1, message = "充值金额不能低于1")
    private Double price;

    private String orderSn;
}
