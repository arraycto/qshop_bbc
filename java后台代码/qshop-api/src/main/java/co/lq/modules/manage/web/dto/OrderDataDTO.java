package co.lq.modules.manage.web.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @ClassName OrderDataDTO
 * @Author billy
 * @Date 2019/11/25
 **/
@Data
public class OrderDataDTO implements Serializable {
    private Integer count;
    private Double  price;
    private String  time;
}
