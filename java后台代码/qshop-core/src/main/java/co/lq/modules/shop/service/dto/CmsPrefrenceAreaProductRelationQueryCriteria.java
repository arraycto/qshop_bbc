package co.lq.modules.shop.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2020-04-11
 */
@Data
public class CmsPrefrenceAreaProductRelationQueryCriteria {

    @Query
    private Long    id;

    @Query
    private Integer deleted;
}
