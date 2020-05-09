package co.lq.modules.activity.web.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName BargainCountDTO
 * @Author billy
 * @Date 2019/12/21
 **/
@Data
@Builder
public class BargainCountDTO implements Serializable {
    private Double  alreadyPrice;
    private Integer count;
    private Integer pricePercent;
    private Integer status;
    private Double  price;       //剩余的砍价金额

}
