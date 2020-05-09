package co.lq.modules.user.web.param;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @ClassName UserEditParam
 * @Author billy
 * @Date 2020/02/07
 **/
@Data
public class UserEditParam implements Serializable {
    @NotBlank(message = "请上传头像")
    private String avatar;
    @NotBlank(message = "请填写昵称")
    private String nickname;

}
