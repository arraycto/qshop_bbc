package co.lq.modules.shop.service;

import java.util.Map;

import co.lq.common.service.BaseService;
import co.lq.modules.shop.entity.StoreProductAttr;
import co.lq.modules.shop.entity.StoreProductAttrValue;

/**
 * <p>
 * 商品属性表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-10-23
 */
public interface ApiStoreProductAttrService extends BaseService<StoreProductAttr> {

    void incProductAttrStock(int num, long productId, String unique);

    void decProductAttrStock(int num, long productId, String unique);

    Map<String, Object> getProductAttrDetail(long productId, int uid, int type);

    int uniqueByStock(String unique);

    Boolean issetProductUnique(long productId, String unique);

    StoreProductAttrValue uniqueByAttrInfo(String unique);
}
