package co.lq.modules.shop.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2019-11-03
 */
@Data
public class StoreProductReplyQueryCriteria {
    @Query
    private Integer isDel;

    @Query
    private Long    storeId;
}
