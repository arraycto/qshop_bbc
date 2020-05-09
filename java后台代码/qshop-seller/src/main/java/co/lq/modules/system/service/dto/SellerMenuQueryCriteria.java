package co.lq.modules.system.service.dto;

import java.sql.Timestamp;
import java.util.List;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy 公共查询类
 */
@Data
public class SellerMenuQueryCriteria {

    @Query(blurry = "name,path,component")
    private String          blurry;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;

    @Query
    private Boolean         hidden;
}
