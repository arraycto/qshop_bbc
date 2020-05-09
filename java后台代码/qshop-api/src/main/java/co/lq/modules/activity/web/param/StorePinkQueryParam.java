package co.lq.modules.activity.web.param;

import co.lq.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 拼团表 查询参数对象
 * </p>
 *
 * @author billy
 * @date 2019-11-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "拼团表查询参数", description = "拼团表查询参数")
public class StorePinkQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
