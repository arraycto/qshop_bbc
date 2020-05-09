package co.lq.modules.shop.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2019-10-03
 */
@Data
public class CategoryQueryCriteria {

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String  cateName;

    @Query
    private Integer deleted;
}
