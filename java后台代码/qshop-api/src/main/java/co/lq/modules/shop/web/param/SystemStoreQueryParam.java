package co.lq.modules.shop.web.param;

import co.lq.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 门店自提 查询参数对象
 * </p>
 *
 * @author billy
 * @date 2020-03-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SystemStoreQueryParam对象", description = "门店自提查询参数")
public class SystemStoreQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
