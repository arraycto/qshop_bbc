package co.lq.service.dto;

import java.sql.Timestamp;
import java.util.List;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2019-09-05
 */
@Data
public class LocalStorageQueryCriteria {

    @Query(blurry = "name,suffix,type,operate,size")
    private String          blurry;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
