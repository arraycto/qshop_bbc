package co.lq.modules.shop.service.dto;

import java.util.Map;

import lombok.Data;

/**
 * @ClassName StoreOrderCartInfo
 * @Author billy
 * @Date 2019/10/14
 **/

@Data
public class StoreOrderCartInfoDTO {

    private Long                id;

    private Long                oid;

    private Long                cartId;

    private Long                productId;

    private String              cartInfo;

    private String              unique;

    private Map<String, Object> cartInfoMap;

}
