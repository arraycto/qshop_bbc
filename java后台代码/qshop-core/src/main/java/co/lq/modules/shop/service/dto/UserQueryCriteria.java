package co.lq.modules.shop.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2019-10-06
 */
@Data
public class UserQueryCriteria {

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String  nickname;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String  phone;

    @Query
    private Integer isPromoter;

    @Query
    private String  userType;

    @Query
    private Long    storeId;
}
