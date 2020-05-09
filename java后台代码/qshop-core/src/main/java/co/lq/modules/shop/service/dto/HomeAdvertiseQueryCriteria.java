package co.lq.modules.shop.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2020-03-13
 */
@Data
public class HomeAdvertiseQueryCriteria {

    @Query
    private Integer deleted;

    @Query
    private Long    storeId;
}
