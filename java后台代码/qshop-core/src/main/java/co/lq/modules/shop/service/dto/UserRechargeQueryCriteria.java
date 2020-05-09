package co.lq.modules.shop.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2020-03-02
 */
@Data
public class UserRechargeQueryCriteria {

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String nickname;

    @Query
    private Long   storeId;
}
