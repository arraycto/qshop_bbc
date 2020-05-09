package co.lq.modules.shop.web.param;

import co.lq.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 组合数据详情表 查询参数对象
 * </p>
 *
 * @author billy
 * @date 2019-10-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SystemGroupDataQueryParam对象", description = "组合数据详情表查询参数")
public class SystemGroupDataQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
