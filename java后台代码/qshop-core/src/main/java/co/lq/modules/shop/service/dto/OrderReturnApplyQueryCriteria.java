package co.lq.modules.shop.service.dto;

import java.sql.Timestamp;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2020-03-27
 */
@Data
public class OrderReturnApplyQueryCriteria {

    @Query
    private Integer   status;

    @Query
    private Long      id;

    @Query
    private Timestamp addTime;

    @Query
    private Timestamp modifyTime;

    @Query
    private String    handleMan;

    @Query
    private Long      storeId;
}
