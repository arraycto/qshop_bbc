package co.lq.modules.activity.web.param;

import co.lq.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 砍价用户帮助表 查询参数对象
 * </p>
 *
 * @author billy
 * @date 2019-12-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "砍价用户帮助表查询参数", description = "砍价用户帮助表查询参数")
public class StoreBargainUserHelpQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
