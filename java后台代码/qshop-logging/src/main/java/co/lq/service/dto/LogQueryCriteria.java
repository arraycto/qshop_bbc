package co.lq.service.dto;

import java.sql.Timestamp;
import java.util.List;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * 日志查询类
 *
 * @author billy
 * @date 2019-6-4 09:23:07
 */
@Data
public class LogQueryCriteria {

    @Query(blurry = "username,description,address,requestIp,method,params")
    private String          blurry;

    @Query
    private String          logType;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;

    @Query
    private Integer         type;

    @Query
    private Long            storeId;
}
