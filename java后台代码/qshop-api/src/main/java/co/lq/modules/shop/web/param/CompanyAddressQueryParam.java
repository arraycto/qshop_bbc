package co.lq.modules.shop.web.param;

import co.lq.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 公司收发货地址表 查询参数对象
 * </p>
 *
 * @author billy
 * @date 2020-05-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CompanyAddressQueryParam对象", description = "公司收发货地址表查询参数")
public class CompanyAddressQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
