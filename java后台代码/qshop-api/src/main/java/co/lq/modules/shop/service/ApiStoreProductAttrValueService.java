package co.lq.modules.shop.service;

import java.io.Serializable;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.StoreProductAttrValue;
import co.lq.modules.shop.web.param.StoreProductAttrValueQueryParam;
import co.lq.modules.shop.web.vo.StoreProductAttrValueQueryVo;

/**
 * <p>
 * 商品属性值表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-10-23
 */
public interface ApiStoreProductAttrValueService extends BaseService<StoreProductAttrValue> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreProductAttrValueQueryVo getStoreProductAttrValueById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param storeProductAttrValueQueryParam
     * @return
     */
    Paging<StoreProductAttrValueQueryVo> getStoreProductAttrValuePageList(StoreProductAttrValueQueryParam storeProductAttrValueQueryParam)
            throws Exception;

}
