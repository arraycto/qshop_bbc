package co.lq.modules.activity.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2019-11-18
 */
@Data
public class StorePinkQueryCriteria {
    @Query
    private long kId;

    @Query
    private Long storeId;
}
