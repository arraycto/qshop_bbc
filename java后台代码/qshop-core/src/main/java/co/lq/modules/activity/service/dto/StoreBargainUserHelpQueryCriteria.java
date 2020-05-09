package co.lq.modules.activity.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * 砍价记录
 *
 * @author songbin
 * @since 2020年3月22日 下午4:40:37
 */
@Data
public class StoreBargainUserHelpQueryCriteria {
    @Query
    private Long storeId;
}
