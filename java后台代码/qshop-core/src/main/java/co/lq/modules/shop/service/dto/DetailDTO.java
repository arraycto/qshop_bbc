package co.lq.modules.shop.service.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * @ClassName DetailDTO
 * @Author billy
 * @Date 2019/10/12
 **/
@Data
public class DetailDTO {
    private List<String>                           data;

    //private List<Map<String,List<Map<String,String>>>> res;

    private List<Map<String, Map<String, String>>> res;
}
