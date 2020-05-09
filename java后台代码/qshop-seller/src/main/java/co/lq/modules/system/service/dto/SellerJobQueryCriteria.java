package co.lq.modules.system.service.dto;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import co.lq.annotation.Query;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author billy
 * @date 2019-6-4 14:49:34
 */
@Data
@NoArgsConstructor
public class SellerJobQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String          name;

    @Query
    private Boolean         enabled;

    @Query(propName = "id", joinName = "sellerDept")
    private Long            deptId;

    @Query(propName = "id", joinName = "sellerDept", type = Query.Type.IN)
    private Set<Long>       deptIds;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;

    @Query
    private Long            storeId;
}
