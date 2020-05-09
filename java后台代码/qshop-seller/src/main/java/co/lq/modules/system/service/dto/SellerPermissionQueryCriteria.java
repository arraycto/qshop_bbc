package co.lq.modules.system.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * 公共查询类
 */
@Data
public class SellerPermissionQueryCriteria {

    // 多字段模糊
    @Query(blurry = "name,alias")
    private String blurry;
}
