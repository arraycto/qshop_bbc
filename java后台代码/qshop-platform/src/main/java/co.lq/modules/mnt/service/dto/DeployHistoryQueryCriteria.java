package co.lq.modules.mnt.service.dto;

import java.sql.Timestamp;
import java.util.List;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
@Data
public class DeployHistoryQueryCriteria {

    /**
     * 精确
     */
    @Query(blurry = "appName,ip,deployUser")
    private String          blurry;

    @Query
    private Long            deployId;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> deployDate;
}
