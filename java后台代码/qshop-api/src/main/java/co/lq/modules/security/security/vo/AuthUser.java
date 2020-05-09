package co.lq.modules.security.security.vo;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * @author billy
 * @date 2020/01/12
 */
@Getter
@Setter
public class AuthUser {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String code;

    private String uuid = "";

    private String spread;

    @Override
    public String toString() {
        return "{username=" + username + ", password= ******}";
    }
}
