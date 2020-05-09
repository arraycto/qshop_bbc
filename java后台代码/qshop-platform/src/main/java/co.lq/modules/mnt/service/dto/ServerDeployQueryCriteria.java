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
public class ServerDeployQueryCriteria {

    /**
     * 模糊
     */
    @Query(blurry = "name,ip,account")
    private String          blurry;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
