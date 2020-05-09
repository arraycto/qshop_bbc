package co.lq.modules.shop.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2020-03-28
 */
@Data
public class StoreVertifyRecordQueryCriteria {
    @Query
    private Long    StoreId;

    @Query
    private Integer deleted;
}
