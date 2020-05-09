package co.lq.modules.shop.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2019-11-06
 */
@Data
public class UserBillQueryCriteria {
    private String nickname;
    private String category;
    private String type;
    @Query
    private Long   storeId;
}
