package co.lq.modules.user.web.dto;

import java.io.Serializable;
import java.util.List;

import co.lq.modules.user.web.vo.UserTaskQueryVo;
import lombok.Data;

/**
 * @ClassName TaskDTO
 * @Author billy
 * @Date 2019/12/6
 **/
@Data
public class TaskDTO implements Serializable {
    private List<UserTaskQueryVo> list;
    private Integer               reachCount;
    private List<UserTaskQueryVo> task;
}
