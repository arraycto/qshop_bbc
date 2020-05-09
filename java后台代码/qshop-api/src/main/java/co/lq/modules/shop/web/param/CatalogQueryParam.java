package co.lq.modules.shop.web.param;

import co.lq.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 类目查询条件
 *
 * @author songbin
 * @since 2020年3月11日 下午5:40:45
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CatalogQueryParam对象", description = "商品分类表查询参数")
public class CatalogQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
