package co.lq.modules.shop.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.entity.HomeNewProduct;
import co.lq.modules.shop.web.param.HomeNewProductQueryParam;
import co.lq.modules.shop.web.vo.HomeNewProductQueryVo;

/**
 * <p>
 * 首页推荐商品表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2020-04-18
 */
@Repository
public interface HomeNewProductMapper extends BaseMapper<HomeNewProduct> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    HomeNewProductQueryVo getHomeNewProductById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param homeNewProductQueryParam
     * @return
     */
    IPage<HomeNewProductQueryVo> getHomeNewProductPageList(@Param("page") Page page,
                                                           @Param("param") HomeNewProductQueryParam homeNewProductQueryParam);

    /**
     * 获取新品
     *
     * @param homeNewProductQueryParam
     * @param start
     * @param limit
     * @return
     */
    List<HomeNewProductQueryVo> getHomeNewProductList(@Param("param") HomeNewProductQueryParam homeNewProductQueryParam,
                                                      @Param("start") Integer start, @Param("limit") Integer limit);

}
