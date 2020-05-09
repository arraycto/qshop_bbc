package co.lq.service.dto;

import java.sql.Timestamp;
import java.util.List;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2019-6-4 09:54:37
 */
@Data
public class QiniuQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String          key;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
