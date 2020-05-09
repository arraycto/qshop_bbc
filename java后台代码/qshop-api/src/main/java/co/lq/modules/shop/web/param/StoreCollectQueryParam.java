package co.lq.modules.shop.web.param;

import co.lq.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 店铺收藏表 查询参数对象
 * </p>
 *
 * @author billy
 * @date 2020-04-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StoreCollectQueryParam对象", description = "店铺收藏表查询参数")
public class StoreCollectQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;

    private Integer           deleted;

    private Long              uid;
}
