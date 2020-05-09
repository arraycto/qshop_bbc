package co.lq.modules.user.web.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @ClassName TaskFinishDTO
 * @Author billy
 * @Date 2019/12/6
 **/
@Data
public class TaskFinishDTO implements Serializable {
    private String  addTime;
    private String  title;
    private Integer number;
}
