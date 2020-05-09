package co.lq.modules.shop.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * @author billy
 * @date 2020-03-11
 */
@Data
public class MemberSharedProductDTO implements Serializable {

    /** 分销商品id */
    private Long       id;

    /** 商品id */
    private Integer    merId;

    /** 会员价格 */
    private BigDecimal vipPrice;

    /** 所属店铺 */
    private Long       storeId;

    /** 上下架状态;0-下架,1-上架 */
    private Integer    status;

    /** 逻辑删除 */
    private Integer    deleted;
}
