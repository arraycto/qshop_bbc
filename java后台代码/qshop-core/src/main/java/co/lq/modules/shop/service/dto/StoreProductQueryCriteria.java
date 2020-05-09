package co.lq.modules.shop.service.dto;

import java.util.List;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2019-10-04
 */
@Data
public class StoreProductQueryCriteria {

    @Query
    private Long       id;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String     productName;

    // 精确
    @Query
    private Integer    isDel;

    @Query
    private Integer    isShow;

    @Query
    private Long       storeId;

    @Query
    private Long       storeCateId;

    @Query
    private List<Long> ids;

    @Query
    private Integer    isNew;

    @Query
    private Integer    isGood;

    @Query
    private Integer    isHot;

    @Query
    private Integer    isBest;

    @Query
    private Integer    isBenefit;

    @Query
    private Integer    isPostage;

    // 审核状态
    @Query
    private Integer    verifyStatus;
}
