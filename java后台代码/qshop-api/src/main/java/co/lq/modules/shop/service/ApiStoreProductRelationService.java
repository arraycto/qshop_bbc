package co.lq.modules.shop.service;

import java.io.Serializable;
import java.util.List;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.StoreProductRelation;
import co.lq.modules.shop.web.param.StoreProductRelationQueryParam;
import co.lq.modules.shop.web.vo.StoreProductRelationQueryVo;

/**
 * <p>
 * 商品点赞和收藏表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-10-23
 */
public interface ApiStoreProductRelationService extends BaseService<StoreProductRelation> {

    Boolean isProductRelation(long productId, String category, long uid, String relationType);

    void addRroductRelation(StoreProductRelationQueryParam param, long uid, String relationType);

    void delRroductRelation(StoreProductRelationQueryParam param, long uid, String relationType);

    List<StoreProductRelationQueryVo> userCollectProduct(int page, int limit, long uid);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreProductRelationQueryVo getStoreProductRelationById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param storeProductRelationQueryParam
     * @return
     */
    Paging<StoreProductRelationQueryVo> getStoreProductRelationPageList(StoreProductRelationQueryParam storeProductRelationQueryParam)
            throws Exception;

}
