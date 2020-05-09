package co.lq.modules.activity.web.dto;

import java.util.List;

import lombok.Data;

@Data
public class SeckillConfigDTO {

    private List<SeckillTimeDTO> seckillTime;

    private String               lovely;

    private Integer              seckillTimeIndex;
}
