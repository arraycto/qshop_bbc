package co.lq.modules.shop.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2020-04-02
 */
@Data
public class GroupActivityQueryCriteria {

    @Query
    private Long    storeId;

    @Query
    private Integer deleted;
}
