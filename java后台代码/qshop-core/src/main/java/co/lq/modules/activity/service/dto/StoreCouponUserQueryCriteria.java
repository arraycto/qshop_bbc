package co.lq.modules.activity.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2019-11-10
 */
@Data
public class StoreCouponUserQueryCriteria {

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String couponTitle;

    @Query
    private Long   storeId;
}
