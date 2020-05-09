package co.lq.modules.manage.web.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @ClassName ChartDataDTO
 * @Author billy
 * @Date 2019/11/25
 **/
@Data
public class ChartDataDTO implements Serializable {
    private Double num;
    private String time;
}
