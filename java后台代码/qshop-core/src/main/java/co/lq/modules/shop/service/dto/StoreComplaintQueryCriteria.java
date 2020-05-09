package co.lq.modules.shop.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2020-03-11
 */
@Data
public class StoreComplaintQueryCriteria {

    @Query
    private Integer deleted;

    @Query
    private Long    storeId;

    @Query
    private Long    id;
}
