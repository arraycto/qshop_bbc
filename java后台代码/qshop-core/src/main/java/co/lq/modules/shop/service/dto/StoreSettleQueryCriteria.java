package co.lq.modules.shop.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2020-03-28
 */
@Data
public class StoreSettleQueryCriteria {
    @Query
    private Long    storeId;

    @Query
    private Integer deleted;

    @Query
    private String  companyName;

    @Query
    private Integer status;

    @Query
    private Integer closed;
}
