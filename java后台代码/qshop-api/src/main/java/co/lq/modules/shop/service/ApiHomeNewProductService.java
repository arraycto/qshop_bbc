package co.lq.modules.shop.service;

import java.io.Serializable;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.HomeNewProduct;
import co.lq.modules.shop.web.param.HomeNewProductQueryParam;
import co.lq.modules.shop.web.vo.HomeNewProductQueryVo;

/**
 * <p>
 * 首页推荐商品表 服务类
 * </p>
 *
 * @author billy
 * @since 2020-04-18
 */
public interface ApiHomeNewProductService extends BaseService<HomeNewProduct> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    HomeNewProductQueryVo getHomeNewProductById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param homeNewProductQueryParam
     * @return
     */
    Paging<HomeNewProductQueryVo> getHomeNewProductPageList(HomeNewProductQueryParam homeNewProductQueryParam)
            throws Exception;

}
