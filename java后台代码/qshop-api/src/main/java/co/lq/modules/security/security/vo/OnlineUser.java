package co.lq.modules.security.security.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author billy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineUser {

    private Long   id;

    private String userName;

    private String nickName;

    private String browser;

    private String ip;

    private String address;

    private String key;

    private Date   loginTime;

}
