package co.lq.modules.shop.service;

import java.io.Serializable;
import java.util.List;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.StoreProduct;
import co.lq.modules.shop.web.dto.ProductDTO;
import co.lq.modules.shop.web.param.HomeNewProductQueryParam;
import co.lq.modules.shop.web.param.HomeRecommendProductQueryParam;
import co.lq.modules.shop.web.param.StoreProductQueryParam;
import co.lq.modules.shop.web.vo.HomeNewProductQueryVo;
import co.lq.modules.shop.web.vo.HomeRecommendProductQueryVo;
import co.lq.modules.shop.web.vo.StoreProductQueryVo;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-10-19
 */
public interface ApiStoreProductService extends BaseService<StoreProduct> {

    void incProductStock(int num, long productId, String unique);

    void decProductStock(int num, long productId, String unique);

    int getProductStock(long productId, String unique);

    ProductDTO goodsDetail(long id, int type, long uid);

    List<StoreProductQueryVo> getGoodsList(StoreProductQueryParam productQueryParam);

    /**
     * 商品列表
     *
     * @param page
     * @param limit
     * @param order
     * @return
     */
    List<StoreProductQueryVo> getList(int page, int limit, int order);

    /**
     * 获取推荐商品
     *
     * @param homeRecommendProductQueryParam
     * @param start
     * @param limit
     * @return
     */
    List<HomeRecommendProductQueryVo> getHomeRecommentProductList(HomeRecommendProductQueryParam homeRecommendProductQueryParam,
                                                                  Integer start, Integer limit);

    /**
     * 获取新品
     *
     * @param homeNewProductQueryParam
     * @param start
     * @param limit
     * @return
     */
    List<HomeNewProductQueryVo> getHomeNewProductList(HomeNewProductQueryParam homeNewProductQueryParam, Integer start,
                                                      Integer limit);

    /**
     * 商品列表
     *
     * @param page
     * @param limit
     * @param order
     * @return
     */
    List<StoreProductQueryVo> getList(StoreProductQueryParam storeProductQueryParam, int page, int limit, int order);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreProductQueryVo getStoreProductById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param storeProductQueryParam
     * @return
     */
    Paging getStoreProductPageList(StoreProductQueryParam storeProductQueryParam) throws Exception;

}
