package co.lq.modules.shop.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2020-03-03
 */
@Data
public class SystemStoreQueryCriteria {
    @Query
    private Long storeId;
}
