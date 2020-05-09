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
import co.lq.modules.shop.entity.HomeNewProduct;
import co.lq.modules.shop.mapper.HomeNewProductMapper;
import co.lq.modules.shop.service.ApiHomeNewProductService;
import co.lq.modules.shop.web.param.HomeNewProductQueryParam;
import co.lq.modules.shop.web.vo.HomeNewProductQueryVo;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 首页推荐商品表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2020-04-18
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiHomeNewProductServiceImpl extends BaseServiceImpl<HomeNewProductMapper, HomeNewProduct>
        implements ApiHomeNewProductService {

    @Autowired
    private HomeNewProductMapper homeNewProductMapper;

    @Override
    public HomeNewProductQueryVo getHomeNewProductById(Serializable id) throws Exception {
        return homeNewProductMapper.getHomeNewProductById(id);
    }

    @Override
    public Paging<HomeNewProductQueryVo> getHomeNewProductPageList(HomeNewProductQueryParam homeNewProductQueryParam)
            throws Exception {
        Page page = setPageParam(homeNewProductQueryParam, OrderItem.desc("create_time"));
        IPage<HomeNewProductQueryVo> iPage = homeNewProductMapper.getHomeNewProductPageList(page,
                homeNewProductQueryParam);
        return new Paging(iPage);
    }

}
