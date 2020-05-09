package co.lq.modules.wechat.web.param;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName WxPhoneParam
 * @Author billy
 * @Date 2020/02/07
 **/
@Getter
@Setter
public class WxPhoneParam {
    @NotBlank(message = "code参数缺失")
    private String code;

    private String encryptedData;

    private String iv;
}
