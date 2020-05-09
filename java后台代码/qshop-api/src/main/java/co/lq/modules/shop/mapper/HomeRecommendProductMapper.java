package co.lq.modules.shop.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.entity.HomeRecommendProduct;
import co.lq.modules.shop.web.param.HomeRecommendProductQueryParam;
import co.lq.modules.shop.web.vo.HomeRecommendProductQueryVo;

/**
 * <p>
 * 人气推荐商品表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2020-04-18
 */
@Repository
public interface HomeRecommendProductMapper extends BaseMapper<HomeRecommendProduct> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    HomeRecommendProductQueryVo getHomeRecommendProductById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param homeRecommendProductQueryParam
     * @return
     */
    IPage<HomeRecommendProductQueryVo> getHomeRecommendProductPageList(@Param("page") Page page,
                                                                       @Param("param") HomeRecommendProductQueryParam homeRecommendProductQueryParam);

    /**
     * 获取推荐商品
     *
     * @param start
     * @param limit
     * @return
     */
    List<HomeRecommendProductQueryVo> getHomeRecommentProductList(@Param("param") HomeRecommendProductQueryParam homeRecommendProductQueryParam,
                                                                  @Param("start") Integer start,
                                                                  @Param("limit") Integer limit);

}
