package co.lq.modules.shop.web.param;

import co.lq.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 配置表 查询参数对象
 * </p>
 *
 * @author billy
 * @date 2019-10-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SystemConfigQueryParam对象", description = "配置表查询参数")
public class SystemConfigQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
