package co.lq.modules.shop.web.param;

import co.lq.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 店铺入驻信息
 *
 * @author songbin
 * @since 2020年3月31日 下午2:11:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StoreQueryParam对象", description = "店铺入驻信息表查询参数")
public class StoreSettleQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long              storeId;
}
