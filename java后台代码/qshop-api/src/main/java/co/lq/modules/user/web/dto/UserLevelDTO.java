package co.lq.modules.user.web.dto;

import java.io.Serializable;
import java.util.List;

import co.lq.modules.user.web.vo.UserLevelQueryVo;
import lombok.Data;

/**
 * @ClassName UserLevelDTO
 * @Author billy
 * @Date 2019/12/6
 **/
@Data
public class UserLevelDTO implements Serializable {
    private List<UserLevelQueryVo> list;
    private TaskDTO                task;
}
