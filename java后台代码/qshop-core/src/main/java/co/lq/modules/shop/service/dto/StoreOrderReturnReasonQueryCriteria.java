package co.lq.modules.shop.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2020-03-29
 */
@Data
public class StoreOrderReturnReasonQueryCriteria {

    @Query
    private Integer deleted;

    @Query
    private Long    storeId;
}
