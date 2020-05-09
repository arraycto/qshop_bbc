package co.lq.modules.activity.web.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName TopCountDTO
 * @Author billy
 * @Date 2019/12/21
 **/
@Data
@Builder
public class TopCountDTO implements Serializable {
    private Integer lookCount;
    private Integer shareCount;
    private Integer userCount;

}
