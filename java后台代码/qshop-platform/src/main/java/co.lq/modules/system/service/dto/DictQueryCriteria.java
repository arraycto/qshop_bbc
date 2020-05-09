package co.lq.modules.system.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy 公共查询类
 */
@Data
public class DictQueryCriteria {

    @Query(blurry = "name,remark")
    private String blurry;
}
