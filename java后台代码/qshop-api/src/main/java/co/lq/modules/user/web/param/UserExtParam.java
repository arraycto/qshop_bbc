package co.lq.modules.user.web.param;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @ClassName UserExtParam
 * @Author billy
 * @Date 2019/11/13
 **/
@Data
public class UserExtParam implements Serializable {

    //支付宝用户名
    private String alipayCode;

    @NotBlank(message = "体现类型不能为空")
    private String extractType;

    @NotBlank(message = "金额不能为空")
    private String money;

    //微信号
    private String weixin;

    //支付宝账号
    private String name;
}
