package co.lq.modules.user.web.param;

import co.lq.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 设置用户等级表 查询参数对象
 * </p>
 *
 * @author billy
 * @date 2019-12-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserLevelQueryParam对象", description = "设置用户等级表查询参数")
public class UserLevelQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
