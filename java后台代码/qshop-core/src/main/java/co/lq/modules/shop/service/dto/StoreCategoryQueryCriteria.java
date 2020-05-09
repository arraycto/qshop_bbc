package co.lq.modules.shop.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2020-03-10
 */
@Data
public class StoreCategoryQueryCriteria {
    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String  cateName;

    @Query
    private Long    storeId;

    @Query
    private Integer deleted;

    @Query
    private Long    id;
}
