package co.lq.modules.system.service.dto;

import java.sql.Timestamp;
import java.util.List;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy 公共查询类
 */
@Data
public class RoleQueryCriteria {

    @Query(blurry = "name,remark")
    private String          blurry;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
