package co.lq.modules.user.web.dto;

import lombok.Data;

/**
 * @ClassName PromUserDTO
 * @Author billy
 * @Date 2019/11/12
 **/
@Data
public class PromUserDTO {
    private String  avatar;
    private String  nickname;
    private Integer childCount;
    private Integer numberCount;
    private Integer orderCount;
    private Integer uid;
    private String  time;
}
