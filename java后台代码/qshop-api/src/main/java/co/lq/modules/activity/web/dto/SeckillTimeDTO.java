package co.lq.modules.activity.web.dto;

import lombok.Data;

@Data
public class SeckillTimeDTO {

    private Long    id;
    /**
     * 00:00
     */
    private String  time;
    /**
     * 状态
     */
    private String  state;

    private Integer status;

    private Integer stop;
}
