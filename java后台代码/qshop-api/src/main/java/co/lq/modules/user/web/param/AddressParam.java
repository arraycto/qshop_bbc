package co.lq.modules.user.web.param;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @ClassName AddressParam
 * @Author billy
 * @Date 2019/10/28
 **/
@Data
public class AddressParam implements Serializable {
    private String             id;
    @NotBlank
    private String             real_name;
    private String             post_code;
    private String             is_default;
    @NotBlank
    private String             detail;
    @NotBlank
    private String             phone;
    private AddressDetailParam address;
}
