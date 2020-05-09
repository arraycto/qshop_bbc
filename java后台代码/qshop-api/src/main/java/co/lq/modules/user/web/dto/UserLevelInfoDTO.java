package co.lq.modules.user.web.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @ClassName UserLevelInfoDTO
 * @Author billy
 * @Date 2019/12/7
 **/
@Data
public class UserLevelInfoDTO implements Serializable {
    private Long    id;
    private Integer addTime;
    private Double  discount;
    private Long    levelId;
    private String  name;
    private String  icon;
    private Integer grade;
}
