package co.lq.modules.user.web.param;

import co.lq.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 附件管理表 查询参数对象
 * </p>
 *
 * @author billy
 * @date 2019-11-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SystemAttachmentQueryParam对象", description = "附件管理表查询参数")
public class SystemAttachmentQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
