package co.lq.modules.shop.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2020-01-09
 */
@Data
public class MaterialGroupQueryCriteria {

    @Query
    private Long storeId;
}
