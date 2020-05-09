package co.lq.modules.system.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2018-11-23
 */
@Data
public class SellerQueryCriteria implements Serializable {

    @Query
    private Long            id;

    @Query(propName = "id", type = Query.Type.IN, joinName = "sellerDept")
    private Set<Long>       deptIds;

    @Query(blurry = "email,username,nickname")
    private String          blurry;

    @Query
    private Boolean         enabled;

    private Long            deptId;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;

    @Query
    private Long            storeId;
}
