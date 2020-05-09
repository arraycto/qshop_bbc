package co.lq.modules.security.rest.param;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @ClassName RegParam
 * @Author billy
 * @Date 2019/10/25
 **/
@Data
public class RegParam {
    @NotBlank(message = "手机号必填")
    private String  account;

    @NotBlank(message = "手机号必填")
    private String  phone;

    @NotBlank
    private String  captcha;

    @NotBlank
    private String  password;

    //todo
    private Integer spread;
}
