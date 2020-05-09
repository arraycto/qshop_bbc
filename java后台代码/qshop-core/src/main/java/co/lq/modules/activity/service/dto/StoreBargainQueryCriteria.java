package co.lq.modules.activity.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2019-12-22
 */
@Data
public class StoreBargainQueryCriteria {
    @Query
    private Long storeId;

    @Query
    private Long productId;

    @Query
    private Long id;
}
