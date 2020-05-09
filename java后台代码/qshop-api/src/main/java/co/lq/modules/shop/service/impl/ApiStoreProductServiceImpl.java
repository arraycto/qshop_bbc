package co.lq.modules.shop.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.exception.ErrorRequestException;
import co.lq.modules.shop.entity.StoreProduct;
import co.lq.modules.shop.entity.StoreProductAttrValue;
import co.lq.modules.shop.mapper.HomeNewProductMapper;
import co.lq.modules.shop.mapper.HomeRecommendProductMapper;
import co.lq.modules.shop.mapper.StoreCollectMapper;
import co.lq.modules.shop.mapper.StoreProductAttrValueMapper;
import co.lq.modules.shop.mapper.StoreProductMapper;
import co.lq.modules.shop.mapping.StoreProductMap;
import co.lq.modules.shop.service.ApiStoreProductAttrService;
import co.lq.modules.shop.service.ApiStoreProductRelationService;
import co.lq.modules.shop.service.ApiStoreProductReplyService;
import co.lq.modules.shop.service.ApiStoreProductService;
import co.lq.modules.shop.service.ApiStoreServie;
import co.lq.modules.shop.service.ApiSystemStoreService;
import co.lq.modules.shop.web.dto.ProductDTO;
import co.lq.modules.shop.web.param.HomeNewProductQueryParam;
import co.lq.modules.shop.web.param.HomeRecommendProductQueryParam;
import co.lq.modules.shop.web.param.StoreProductQueryParam;
import co.lq.modules.shop.web.vo.HomeNewProductQueryVo;
import co.lq.modules.shop.web.vo.HomeRecommendProductQueryVo;
import co.lq.modules.shop.web.vo.StoreCollectQueryVo;
import co.lq.modules.shop.web.vo.StoreProductAttrQueryVo;
import co.lq.modules.shop.web.vo.StoreProductQueryVo;
import co.lq.modules.user.service.UserService;
import co.lq.utils.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-10-19
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ApiStoreProductServiceImpl extends BaseServiceImpl<StoreProductMapper, StoreProduct>
        implements ApiStoreProductService {

    private final StoreProductMapper             storeProductMapper;
    private final StoreProductAttrValueMapper    storeProductAttrValueMapper;
    private final StoreCollectMapper             storeCollectMapper;
    private final HomeRecommendProductMapper     homeRecommendProductMapper;
    private final HomeNewProductMapper           homeNewProductMapper;

    private final ApiStoreProductAttrService     storeProductAttrService;
    private final ApiStoreProductRelationService relationService;
    private final ApiStoreProductReplyService    replyService;
    private final UserService                    userService;
    private final ApiSystemStoreService          systemStoreService;

    private final StoreProductMap                storeProductMap;

    private final ApiStoreServie                 apiStoreServie;

    /**
     * 增加库存 减少销量
     *
     * @param num
     * @param productId
     * @param unique
     */
    @Override
    public void incProductStock(int num, long productId, String unique) {
        if (StrUtil.isNotEmpty(unique)) {
            storeProductAttrService.incProductAttrStock(num, productId, unique);
            storeProductMapper.decSales(num, productId);
        } else {
            storeProductMapper.incStockDecSales(num, productId);
        }
    }

    /**
     * 库存与销量
     *
     * @param num
     * @param productId
     * @param unique
     */
    @Override
    public void decProductStock(int num, long productId, String unique) {
        if (StrUtil.isNotEmpty(unique)) {
            storeProductAttrService.decProductAttrStock(num, productId, unique);
            storeProductMapper.incSales(num, productId);
        } else {
            storeProductMapper.decStockIncSales(num, productId);
        }
    }

    /**
     * 返回商品库存
     *
     * @param productId
     * @param unique
     * @return
     */
    @Override
    public int getProductStock(long productId, String unique) {
        if (StrUtil.isEmpty(unique)) {
            return getStoreProductById(productId).getStock();
        } else {
            return storeProductAttrService.uniqueByStock(unique);
        }

    }

    @Override
    public ProductDTO goodsDetail(long id, int type, long uid) {
        QueryWrapper<StoreProduct> wrapper = new QueryWrapper<>();
        wrapper.eq("is_del", 0).eq("is_show", 1).eq("id", id);
        StoreProduct storeProduct = storeProductMapper.selectOne(wrapper);
        if (ObjectUtil.isNull(storeProduct)) {
            throw new ErrorRequestException("商品不存在或已下架");
        }
        Map<String, Object> returnMap = storeProductAttrService.getProductAttrDetail(id, 0, 0);
        ProductDTO productDTO = new ProductDTO();
        StoreProductQueryVo storeProductQueryVo = storeProductMap.toDto(storeProduct);

        //处理库存
        Integer newStock = storeProductAttrValueMapper.sumStock(id);
        if (newStock != null)
            storeProductQueryVo.setStock(newStock);

        //设置VIP价格
        Double vipPrice;
        if (uid == 0L) {
            vipPrice = storeProductQueryVo.getPrice().doubleValue();
            storeProductQueryVo.setUserCollect(false);
            productDTO.setStoreCollect(false);
        } else {
            vipPrice = userService.setLevelPrice(storeProductQueryVo.getPrice().doubleValue(), uid);
            storeProductQueryVo.setUserCollect(relationService.isProductRelation(id, "product", uid, "collect"));
            StoreCollectQueryVo storeCollectQueryVo = storeCollectMapper.getStoreCollectByUidAndStoreIdAndType(uid,
                    storeProductQueryVo.getStoreId(), "collect");
            if (storeCollectQueryVo != null) {
                productDTO.setStoreCollect(true);
            } else {
                productDTO.setStoreCollect(false);
            }
        }
        storeProductQueryVo.setVipPrice(BigDecimal.valueOf(vipPrice));
        productDTO.setStoreInfo(storeProductQueryVo);
        productDTO.setProductAttr((List<StoreProductAttrQueryVo>) returnMap.get("productAttr"));
        productDTO.setProductValue((Map<String, StoreProductAttrValue>) returnMap.get("productValue"));

        productDTO.setReply(replyService.getReply(id));
        int replyCount = replyService.productReplyCount(id);
        productDTO.setReplyCount(replyCount);
        productDTO.setReplyChance(replyService.doReply(id, replyCount));//百分比

        //店铺
        productDTO.setStore(apiStoreServie.getStoreByUidAndId(storeProductQueryVo.getStoreId(), uid));
        //门店
        productDTO.setSystemStore(systemStoreService.getStoreInfo(storeProductQueryVo.getStoreId()));
        productDTO.setMapKey(RedisUtil.get("store_self_mention"));

        return productDTO;
    }

    /**
     * 商品列表
     *
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<StoreProductQueryVo> getGoodsList(StoreProductQueryParam productQueryParam) {

        QueryWrapper<StoreProduct> wrapper = new QueryWrapper<>();
        wrapper.eq("is_del", 0).eq("is_show", 1).orderByDesc("sort");

        //分类搜索
        if (productQueryParam.getSid() > 0) {
            wrapper.eq("catalog_id", productQueryParam.getSid());
        }
        //关键字搜索
        if (StrUtil.isNotEmpty(productQueryParam.getKeyword())) {
            wrapper.like("product_name", productQueryParam.getKeyword());
        }

        //新品搜索
        if (productQueryParam.getNews() == 1) {
            wrapper.eq("is_new", 1);
        }
        //销量排序
        if (productQueryParam.getSalesOrder().equals("desc")) {
            wrapper.orderByDesc("sales");
        } else {
            wrapper.orderByAsc("sales");
        }
        //价格排序
        if (productQueryParam.getPriceOrder().equals("desc")) {
            wrapper.orderByDesc("price");
        } else {
            wrapper.orderByAsc("price");
        }

        Page<StoreProduct> pageModel = new Page<>(productQueryParam.getPage(), productQueryParam.getLimit());

        IPage<StoreProduct> pageList = storeProductMapper.selectPage(pageModel, wrapper);

        List<StoreProductQueryVo> list = storeProductMap.toDto(pageList.getRecords());

        //        for (GoodsDTO goodsDTO : list) {
        //            goodsDTO.setIsCollect(isCollect(goodsDTO.getGoodsId(),userId));
        //        }

        return list;
    }

    /**
     * 商品列表
     *
     * @param page
     * @param limit
     * @param order
     * @return
     */
    @Override
    public List<StoreProductQueryVo> getList(int page, int limit, int order) {

        QueryWrapper<StoreProduct> wrapper = new QueryWrapper<>();
        wrapper.eq("is_del", 0).eq("is_show", 1).orderByDesc("sort");

        //todo order = 1 精品推荐  order=2  新品 3-优惠产品 4-热卖
        switch (order) {
            case 1:
                wrapper.eq("is_best", 1);
                break;
            case 2:
                wrapper.eq("is_new", 1);
                break;
            case 3:
                wrapper.eq("is_benefit", 1);
                break;
            case 4:
                wrapper.eq("is_hot", 1);
                break;
            default:
                wrapper.eq("is_best", 1);
                break;
        }
        Page<StoreProduct> pageModel = new Page<>(page, limit);

        IPage<StoreProduct> pageList = storeProductMapper.selectPage(pageModel, wrapper);

        List<StoreProductQueryVo> list = storeProductMap.toDto(pageList.getRecords());

        return list;
    }

    @Override
    public List<HomeRecommendProductQueryVo> getHomeRecommentProductList(HomeRecommendProductQueryParam homeRecommendProductQueryParam,
                                                                         Integer start, Integer limit) {
        return homeRecommendProductMapper.getHomeRecommentProductList(homeRecommendProductQueryParam, start, limit);
    }

    @Override
    public List<HomeNewProductQueryVo> getHomeNewProductList(HomeNewProductQueryParam homeNewProductQueryParam,
                                                             Integer start, Integer limit) {
        return homeNewProductMapper.getHomeNewProductList(homeNewProductQueryParam, start, limit);
    }

    @Override
    public List<StoreProductQueryVo> getList(StoreProductQueryParam storeProductQueryParam, int page, int limit,
                                             int order) {
        QueryWrapper<StoreProduct> wrapper = new QueryWrapper<>();
        wrapper.eq("is_del", 0).eq("is_show", 1).orderByDesc("sort");
        if (storeProductQueryParam.getStoreId() != null) {
            wrapper.eq("store_id", storeProductQueryParam.getStoreId());
        }
        if (storeProductQueryParam.getStoreCateId() != null) {
            wrapper.eq("store_cate_id", storeProductQueryParam.getStoreCateId());
        }

        //todo order = 1 精品推荐  order=2  新品 3-优惠产品 4-热卖
        switch (order) {
            case 1:
                wrapper.eq("is_best", 1);
                break;
            case 2:
                wrapper.eq("is_new", 1);
                break;
            case 3:
                wrapper.eq("is_benefit", 1);
                break;
            case 4:
                wrapper.eq("is_hot", 1);
                break;
            default:
                break;
        }
        Page<StoreProduct> pageModel = new Page<>(page, limit);

        IPage<StoreProduct> pageList = storeProductMapper.selectPage(pageModel, wrapper);

        List<StoreProductQueryVo> list = storeProductMap.toDto(pageList.getRecords());

        return list;
    }

    @Override
    public StoreProductQueryVo getStoreProductById(Serializable id) {
        return storeProductMapper.getStoreProductById(id);
    }

    @Override
    public Paging<StoreProductQueryVo> getStoreProductPageList(StoreProductQueryParam storeProductQueryParam)
            throws Exception {
        Page page = setPageParam(storeProductQueryParam, OrderItem.desc("create_time"));
        IPage<StoreProductQueryVo> iPage = storeProductMapper.getStoreProductPageList(page, storeProductQueryParam);
        return new Paging(iPage);
    }

}
