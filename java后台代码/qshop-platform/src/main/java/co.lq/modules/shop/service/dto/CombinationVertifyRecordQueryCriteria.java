package co.lq.modules.shop.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2020-03-11
 */
@Data
public class CombinationVertifyRecordQueryCriteria {
    @Query
    private Integer deleted;
}
