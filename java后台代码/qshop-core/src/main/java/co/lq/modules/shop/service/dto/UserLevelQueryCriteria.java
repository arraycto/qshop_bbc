package co.lq.modules.shop.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2019-12-04
 */
@Data
public class UserLevelQueryCriteria {

    @Query
    private Long    storeId;

    @Query
    private Integer isDel;
}
