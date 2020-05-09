package co.lq.modules.activity.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2019-11-14
 */
@Data
public class UserExtractQueryCriteria {

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String realName;

    @Query
    private Long   storeId;
}
