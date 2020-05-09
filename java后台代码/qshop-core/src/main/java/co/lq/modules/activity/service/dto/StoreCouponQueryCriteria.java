package co.lq.modules.activity.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2019-11-09
 */
@Data
public class StoreCouponQueryCriteria {
    @Query
    private Integer isDel;

    @Query
    private Long    storeId;
}
