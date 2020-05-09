package co.lq.modules.shop.web.param;

import co.lq.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 店铺类目关联表 查询参数对象
 * </p>
 *
 * @author billy
 * @date 2020-04-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StoreCatalogRelationQueryParam对象", description = "店铺类目关联表查询参数")
public class StoreCatalogRelationQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
