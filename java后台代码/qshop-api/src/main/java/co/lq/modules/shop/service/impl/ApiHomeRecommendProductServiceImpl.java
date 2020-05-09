package co.lq.modules.shop.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.HomeRecommendProduct;
import co.lq.modules.shop.mapper.HomeRecommendProductMapper;
import co.lq.modules.shop.service.ApiHomeRecommendProductService;
import co.lq.modules.shop.web.param.HomeRecommendProductQueryParam;
import co.lq.modules.shop.web.vo.HomeRecommendProductQueryVo;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 人气推荐商品表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2020-04-18
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiHomeRecommendProductServiceImpl extends
        BaseServiceImpl<HomeRecommendProductMapper, HomeRecommendProduct> implements ApiHomeRecommendProductService {

    @Autowired
    private HomeRecommendProductMapper homeRecommendProductMapper;

    @Override
    public HomeRecommendProductQueryVo getHomeRecommendProductById(Serializable id) throws Exception {
        return homeRecommendProductMapper.getHomeRecommendProductById(id);
    }

    @Override
    public Paging<HomeRecommendProductQueryVo> getHomeRecommendProductPageList(HomeRecommendProductQueryParam homeRecommendProductQueryParam)
            throws Exception {
        Page page = setPageParam(homeRecommendProductQueryParam, OrderItem.desc("create_time"));
        IPage<HomeRecommendProductQueryVo> iPage = homeRecommendProductMapper.getHomeRecommendProductPageList(page,
                homeRecommendProductQueryParam);
        return new Paging(iPage);
    }

}
