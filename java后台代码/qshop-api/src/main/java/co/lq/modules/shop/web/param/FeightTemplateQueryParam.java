package co.lq.modules.shop.web.param;

import co.lq.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 运费模版 查询参数对象
 * </p>
 *
 * @author billy
 * @date 2020-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "FeightTemplateQueryParam对象", description = "运费模版查询参数")
public class FeightTemplateQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
