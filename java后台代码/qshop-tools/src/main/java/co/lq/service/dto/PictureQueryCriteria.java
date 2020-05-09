package co.lq.service.dto;

import java.sql.Timestamp;
import java.util.List;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * sm.ms图床
 *
 * @author billy
 * @date 2019-6-4 09:52:09
 */
@Data
public class PictureQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String          filename;

    @Query(type = Query.Type.INNER_LIKE)
    private String          username;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
