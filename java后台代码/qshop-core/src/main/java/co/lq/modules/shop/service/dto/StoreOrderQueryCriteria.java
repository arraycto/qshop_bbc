package co.lq.modules.shop.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2019-10-14
 */
@Data
public class StoreOrderQueryCriteria {

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String  orderId;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String  realName;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String  userPhone;

    @Query
    private Integer paid;

    @Query
    private Integer status;

    @Query
    private Integer refundStatus;

    @Query
    private Integer isDel;

    @Query
    private Long    combinationId;

    @Query
    private Long    seckillId;

    @Query
    private Long    bargainId;

    @Query(propName = "combinationId", type = Query.Type.NOT_EQUAL)
    private Integer newCombinationId;

    @Query(propName = "seckillId", type = Query.Type.NOT_EQUAL)
    private Long    newSeckillId;

    @Query(propName = "bargainId", type = Query.Type.NOT_EQUAL)
    private Long    newBargainId;

    @Query
    private Integer shippingType;

    @Query
    private Long    shopId;

    @Query
    private Integer hidden;
}
