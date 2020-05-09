package co.lq.modules.security.rest.param;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @ClassName LoginParam
 * @Author billy
 * @Date 2020/01/15
 **/
@Data
public class LoginParam {
    @NotBlank(message = "code参数缺失")
    private String code;

    private String spread;

    private String encryptedData;

    private String iv;
}
