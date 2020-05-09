package co.lq.modules.activity.web.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @ClassName PinkDTO
 * @Author billy
 * @Date 2019/11/19
 **/
@Data
public class PinkDTO implements Serializable {
    private Long    id;
    private Long    uid;
    private Integer people;
    private Double  price;
    private String  stopTime;
    private String  nickname;
    private String  avatar;

    private String  count;
    private String  h;
    private String  i;
    private String  s;

}
