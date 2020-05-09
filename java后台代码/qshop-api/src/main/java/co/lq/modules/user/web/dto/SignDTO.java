package co.lq.modules.user.web.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @ClassName SignDTO
 * @Author billy
 * @Date 2019/12/5
 **/
@Data
public class SignDTO implements Serializable {
    private String  addTime;
    private String  title;
    private Integer number;
}
