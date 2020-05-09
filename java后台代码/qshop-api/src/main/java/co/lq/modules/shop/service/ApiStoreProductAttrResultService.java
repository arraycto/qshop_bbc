package co.lq.modules.shop.service;

import java.io.Serializable;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.StoreProductAttrResult;
import co.lq.modules.shop.web.param.StoreProductAttrResultQueryParam;
import co.lq.modules.shop.web.vo.StoreProductAttrResultQueryVo;

/**
 * <p>
 * 商品属性详情表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-10-23
 */
public interface ApiStoreProductAttrResultService extends BaseService<StoreProductAttrResult> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreProductAttrResultQueryVo getStoreProductAttrResultById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param storeProductAttrResultQueryParam
     * @return
     */
    Paging<StoreProductAttrResultQueryVo> getStoreProductAttrResultPageList(StoreProductAttrResultQueryParam storeProductAttrResultQueryParam)
            throws Exception;

}
