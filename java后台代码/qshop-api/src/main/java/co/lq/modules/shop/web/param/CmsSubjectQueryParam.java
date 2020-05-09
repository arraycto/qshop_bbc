package co.lq.modules.shop.web.param;

import co.lq.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 专题表 查询参数对象
 * </p>
 *
 * @author billy
 * @date 2020-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CmsSubjectQueryParam对象", description = "专题表查询参数")
public class CmsSubjectQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
