package co.lq.modules.shop.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2020-03-10
 */
@Data
public class CatalogAttrQueryCriteria {
    @Query
    private Integer deleted;

    @Query
    private Long    catalogId;

    @Query
    private Integer isSpec;
}
