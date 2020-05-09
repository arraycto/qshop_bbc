package co.lq.modules.shop.service.dto;

import java.util.List;

import lombok.Data;

/**
 * @ClassName FromatDetailDTO
 * @Author billy
 * @Date 2019/10/12
 **/

@Data
public class FromatDetailDTO {
    private boolean      attrHidden;

    private String       detailValue;

    private List<String> detail;

    private String       value;

}
