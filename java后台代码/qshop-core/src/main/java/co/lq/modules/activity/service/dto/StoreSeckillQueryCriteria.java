package co.lq.modules.activity.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2019-12-14
 */
@Data
public class StoreSeckillQueryCriteria {

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String title;

    @Query
    private Long   storeId;

    @Query
    private Long   id;
}
