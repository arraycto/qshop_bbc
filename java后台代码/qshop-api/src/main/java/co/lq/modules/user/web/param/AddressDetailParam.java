package co.lq.modules.user.web.param;

import java.io.Serializable;

import lombok.Data;

/**
 * @ClassName AddressDetailParam
 * @Author billy
 * @Date 2019/10/28
 **/
@Data
public class AddressDetailParam implements Serializable {
    private String city;
    private String district;
    private String province;
}
