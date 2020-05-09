package co.lq.modules.wechat.web.param;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName BindPhoneParam
 * @Author billy
 * @Date 2020/2/7
 **/
@Getter
@Setter
public class BindPhoneParam {
    @NotBlank(message = "验证码必填")
    private String captcha;

    @NotBlank(message = "手机号必填")
    private String phone;
}
