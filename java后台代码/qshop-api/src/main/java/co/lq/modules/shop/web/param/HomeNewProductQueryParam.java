package co.lq.modules.shop.web.param;

import co.lq.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 首页推荐商品表 查询参数对象
 * </p>
 *
 * @author billy
 * @date 2020-04-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "HomeNewProductQueryParam对象", description = "首页推荐商品表查询参数")
public class HomeNewProductQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
