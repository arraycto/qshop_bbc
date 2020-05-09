package co.lq.modules.shop.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2019-10-18
 */
@Data
public class SystemGroupDataQueryCriteria {
    // 精确
    @Query
    private String groupName;
}
