package co.lq.modules.activity.web.dto;

import java.io.Serializable;
import java.util.List;

import co.lq.modules.activity.web.vo.StoreCombinationQueryVo;
import co.lq.modules.activity.web.vo.StorePinkQueryVo;
import co.lq.modules.user.web.vo.UserQueryVo;
import lombok.Data;

/**
 * @ClassName PinkInfoDTO
 * @Author billy
 * @Date 2019/11/20
 **/
@Data
public class PinkInfoDTO implements Serializable {
    private Integer                 count;
    private String                  currentPinkOrder;
    private Integer                 isOk     = 0;
    private List<StorePinkQueryVo>  pinkAll;
    private Integer                 pinkBool = 0;
    private StorePinkQueryVo        pinkT;
    private StoreCombinationQueryVo storeCombination;
    private String                  storeCombinationHost;
    private Integer                 userBool = 0;
    private UserQueryVo             userInfo;

}
