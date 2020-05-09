package co.lq.modules.shop.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2020-04-13
 */
@Data
public class StoreWithdrawQueryCriteria {
    @Query
    private Integer deleted;

    @Query
    private Long    id;

    @Query
    private Long    storeId;
}
