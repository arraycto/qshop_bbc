package co.lq.modules.activity.web.dto;

import java.io.Serializable;

import co.lq.modules.activity.web.vo.StoreBargainQueryVo;
import co.lq.modules.user.web.vo.UserQueryVo;
import lombok.Builder;
import lombok.Data;

/**
 * @ClassName BargainDTO
 * @Author billy
 * @Date 2019/12/21
 **/
@Data
@Builder
public class BargainDTO implements Serializable {
    private StoreBargainQueryVo bargain;
    private UserQueryVo         userInfo;
    private Integer             bargainSumCount;//砍价支付成功订单数量
}
