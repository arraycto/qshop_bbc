package co.lq.modules.shop.web.param;

import javax.validation.constraints.NotBlank;

import co.lq.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品点赞和收藏表 查询参数对象
 * </p>
 *
 * @author billy
 * @date 2019-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StoreProductRelationQueryParam对象", description = "商品点赞和收藏表查询参数")
public class StoreProductRelationQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String            category;

    private Long              id;

    private Long[]            ids;
}
