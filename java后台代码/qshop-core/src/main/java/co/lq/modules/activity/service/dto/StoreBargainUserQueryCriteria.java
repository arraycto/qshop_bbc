package co.lq.modules.activity.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * 砍价
 *
 * @author songbin
 * @since 2020年3月22日 下午3:55:14
 */
@Data
public class StoreBargainUserQueryCriteria {
    @Query
    private Long storeId;
}
