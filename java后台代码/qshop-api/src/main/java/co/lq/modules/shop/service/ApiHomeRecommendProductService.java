package co.lq.modules.shop.service;

import java.io.Serializable;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.HomeRecommendProduct;
import co.lq.modules.shop.web.param.HomeRecommendProductQueryParam;
import co.lq.modules.shop.web.vo.HomeRecommendProductQueryVo;

/**
 * <p>
 * 人气推荐商品表 服务类
 * </p>
 *
 * @author billy
 * @since 2020-04-18
 */
public interface ApiHomeRecommendProductService extends BaseService<HomeRecommendProduct> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    HomeRecommendProductQueryVo getHomeRecommendProductById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param homeRecommendProductQueryParam
     * @return
     */
    Paging<HomeRecommendProductQueryVo> getHomeRecommendProductPageList(HomeRecommendProductQueryParam homeRecommendProductQueryParam)
            throws Exception;

}
