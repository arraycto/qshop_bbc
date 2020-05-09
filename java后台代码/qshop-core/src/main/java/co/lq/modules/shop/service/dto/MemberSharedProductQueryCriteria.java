package co.lq.modules.shop.service.dto;

import co.lq.annotation.Query;
import lombok.Data;

/**
 * @author billy
 * @date 2020-03-11
 */
@Data
public class MemberSharedProductQueryCriteria {

    /** 商品id */
    @Query
    private Integer merId;

    @Query
    private Long    storeId;

    @Query
    private Integer deleted;
}
