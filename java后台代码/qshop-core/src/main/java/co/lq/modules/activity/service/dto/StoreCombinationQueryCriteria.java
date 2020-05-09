package co.lq.modules.activity.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2019-11-18
 */
@Data
public class StoreCombinationQueryCriteria {

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String  title;

    @Query
    private Integer isDel;

    @Query
    private Long    storeId;

    @Query
    private Long    id;
}
