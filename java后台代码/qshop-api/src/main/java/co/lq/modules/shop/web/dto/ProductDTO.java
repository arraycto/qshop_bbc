package co.lq.modules.shop.web.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import co.lq.common.web.param.QueryParam;
import co.lq.modules.shop.entity.StoreProductAttrValue;
import co.lq.modules.shop.web.vo.StoreProductAttrQueryVo;
import co.lq.modules.shop.web.vo.StoreProductQueryVo;
import co.lq.modules.shop.web.vo.StoreProductReplyQueryVo;
import co.lq.modules.shop.web.vo.StoreQueryVo;
import co.lq.modules.shop.web.vo.SystemStoreQueryVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品dto
 * </p>
 *
 * @author billy
 * @date 2019-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductDTO extends QueryParam {
    private static final long                  serialVersionUID = 1L;

    //todo
    private List<StoreProductQueryVo>          goodList         = new ArrayList();

    private String                             priceName        = "";

    private List<StoreProductAttrQueryVo>      productAttr      = new ArrayList();

    private Map<String, StoreProductAttrValue> productValue     = new LinkedHashMap<>();

    private StoreProductReplyQueryVo           reply;

    private String                             replyChance;

    private Integer                            replyCount       = 0;

    //todo
    private List                               similarity       = new ArrayList();

    private StoreProductQueryVo                storeInfo;

    private String                             mapKey;

    //门店
    private SystemStoreQueryVo                 systemStore;

    private Long                               uid              = 0L;

    /**
     * 所属店铺
     */
    private StoreQueryVo                       store;

    /**
     * 店铺收藏
     */
    private Boolean                            storeCollect;

    private BigDecimal                         integralPrice;

    private Integer                            integral;

    private String                             integralName;

}
