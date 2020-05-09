package co.lq.modules.shop.web.param;

import co.lq.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 广告
 *
 * @author songbin
 * @since 2020年3月13日 下午10:28:46
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "HomeAdvertiseQueryParam对象", description = "广告表查询参数")
public class HomeAdvertiseQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;

    /**
     * 所属店铺
     */
    private Long              storeId;
}
