package co.lq.modules.security.rest.param;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @ClassName VerityParam
 * @Author billy
 * @Date 2019/10/25
 **/
@Data
public class VerityParam {
    @NotBlank(message = "手机号必填")
    private String phone;

    private String account;

    private String type;
}
