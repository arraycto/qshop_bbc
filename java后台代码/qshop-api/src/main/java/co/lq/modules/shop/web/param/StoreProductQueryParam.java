package co.lq.modules.shop.web.param;

import co.lq.common.web.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品表 查询参数对象
 * </p>
 *
 * @author billy
 * @date 2019-10-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StoreProductQueryParam对象", description = "商品表查询参数")
public class StoreProductQueryParam extends QueryParam {
    private static final long serialVersionUID = 1L;

    private int               type;
    private int               sid;
    private int               news;
    private String            priceOrder;
    private String            salesOrder;
    private String            keyword;
    private Long              storeId;
    private Long              storeCateId;
}
