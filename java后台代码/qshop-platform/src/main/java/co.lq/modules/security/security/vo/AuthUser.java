package co.lq.modules.security.security.vo;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * @author billy
 * @date 2018-11-30
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

    @Override
    public String toString() {
        return "{username=" + username + ", password= ******}";
    }
}
