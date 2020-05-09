package co.lq.modules.shop.web.param;

import co.lq.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 卖家帐号
 *
 * @author songbin
 * @since 2020年3月31日 下午3:15:52
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SellerUserQueryParam对象", description = "卖家帐号表查询参数")
public class SellerUserQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String            username;

    /**
     * 所属店铺
     */
    private Long              storeId;
}
