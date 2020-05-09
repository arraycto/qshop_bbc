package co.lq.modules.shop.service.dto;

import java.util.Map;

import lombok.Data;

/**
 * @ClassName ProductFormatDTO
 * @Author billy
 * @Date 2019/10/12
 **/

@Data
public class ProductFormatDTO {

    private Double              price;

    private Double              cost;

    private int                 sales;

    private String              pic;

    // private Map<String, List<Map<String,String>>> detail;

    //private List<Map<String, String>> detail;
    private Map<String, String> detail;
    private Boolean             check;

}
