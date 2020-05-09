package co.lq.modules.system.service.dto;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2019-03-25
 */
@Data
public class SellerDeptQueryCriteria {

    @Query(type = Query.Type.IN, propName = "id")
    private Set<Long>       ids;

    @Query(type = Query.Type.INNER_LIKE)
    private String          name;

    @Query
    private Boolean         enabled;

    @Query
    private Long            pid;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;

    @Query
    private Long            storeId;
}
