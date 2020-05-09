package co.lq.modules.shop.web.param;

import co.lq.common.web.param.OrderQueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 店铺查询条件
 *
 * @author songbin
 * @since 2020年3月12日 下午9:06:11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StoreQueryParam对象", description = "店铺表查询参数")
public class StoreQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long              uid;

    /**
     * 店铺id
     */
    private Long              id;

    /**
     * 收藏输
     */
    private Integer           collect;

    /**
     * 点击数量
     */
    private Integer           hit;

    /**
     * 商品数量
     */
    private Integer           goodsCount;
}
