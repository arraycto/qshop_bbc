package co.lq.modules.shop.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.exception.ErrorRequestException;
import co.lq.modules.activity.entity.StoreBargain;
import co.lq.modules.activity.entity.StoreCombination;
import co.lq.modules.activity.entity.StoreSeckill;
import co.lq.modules.activity.mapper.StoreBargainMapper;
import co.lq.modules.activity.mapper.StoreCombinationMapper;
import co.lq.modules.activity.mapper.StoreSeckillMapper;
import co.lq.modules.activity.service.ApiStoreBargainService;
import co.lq.modules.activity.service.ApiStoreCombinationService;
import co.lq.modules.activity.service.ApiStoreSeckillService;
import co.lq.modules.order.entity.StoreOrder;
import co.lq.modules.order.service.StoreOrderService;
import co.lq.modules.shop.entity.StoreCart;
import co.lq.modules.shop.entity.StoreProductAttrValue;
import co.lq.modules.shop.mapper.StoreCartMapper;
import co.lq.modules.shop.mapping.CartMap;
import co.lq.modules.shop.service.ApiStoreCartService;
import co.lq.modules.shop.service.ApiStoreCouponUserService;
import co.lq.modules.shop.service.ApiStoreProductAttrService;
import co.lq.modules.shop.service.ApiStoreProductService;
import co.lq.modules.shop.service.ApiStoreServie;
import co.lq.modules.shop.web.vo.StoreCartQueryVo;
import co.lq.modules.shop.web.vo.StoreProductQueryVo;
import co.lq.modules.shop.web.vo.StoreQueryVo;
import co.lq.modules.user.entity.UserBill;
import co.lq.modules.user.service.UserBillService;
import co.lq.modules.user.service.UserService;
import co.lq.modules.user.web.vo.UserQueryVo;
import co.lq.utils.OrderUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-10-25
 */
@Slf4j
@Service
@Builder
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ApiStoreCartServiceImpl extends BaseServiceImpl<StoreCartMapper, StoreCart>
        implements ApiStoreCartService {

    private final StoreCartMapper            storeCartMapper;
    private final StoreSeckillMapper         storeSeckillMapper;
    private final StoreBargainMapper         storeBargainMapper;
    private final StoreCombinationMapper     storeCombinationMapper;
    private final ApiStoreServie             apiStoreServie;
    private final ApiStoreCouponUserService  apiStoreCouponUserService;

    private final ApiStoreProductService     productService;
    private final ApiStoreProductAttrService productAttrService;
    private final ApiStoreCombinationService apiStoreCombinationService;
    private final ApiStoreSeckillService     apiStoreSeckillService;
    private final ApiStoreBargainService     apiStoreBargainService;
    private final StoreOrderService          storeOrderService;
    private final UserService                userService;
    private final UserBillService            billService;

    private final CartMap                    cartMap;

    /**
     * 删除购物车
     *
     * @param uid
     * @param ids
     */
    @Override
    public void removeUserCart(long uid, List<String> ids) {
        QueryWrapper<StoreCart> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid).in("id", ids);

        StoreCart storeCart = new StoreCart();
        storeCart.setIsDel(1);

        storeCartMapper.update(storeCart, wrapper);
    }

    /**
     * 改购物车数量
     *
     * @param cartId
     * @param cartNum
     * @param uid
     */
    @Override
    public void changeUserCartNum(long cartId, int cartNum, long uid) {
        QueryWrapper<StoreCart> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid).eq("id", cartId);

        StoreCart cart = getOne(wrapper);
        if (ObjectUtil.isNull(cart)) {
            throw new ErrorRequestException("购物车不存在");
        }

        if (cartNum <= 0) {
            throw new ErrorRequestException("库存错误");
        }

        //todo 普通商品库存
        int stock = productService.getProductStock(cart.getProductId(), cart.getProductAttrUnique());
        if (stock < cartNum) {
            throw new ErrorRequestException("该产品库存不足" + cartNum);
        }

        if (cartNum == cart.getCartNum()) {
            return;
        }

        StoreCart storeCart = new StoreCart();
        storeCart.setCartNum(cartNum);
        storeCart.setId(Long.valueOf(cartId));

        storeCartMapper.updateById(storeCart);

    }

    /**
     * 购物车列表
     *
     * @param uid 用户id
     * @param cartIds 购物车id，多个逗号隔开
     * @param status 0-购购物车列表
     * @return
     */
    @Override
    public Map<String, Object> getUserProductCartList(long uid, String cartIds, int status) {
        QueryWrapper<StoreCart> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid).eq("type", "product").eq("is_pay", 0).eq("is_del", 0).orderByDesc("add_time");
        if (status == 0) {
            wrapper.eq("is_new", 0);
        }
        if (StrUtil.isNotEmpty(cartIds)) {
            wrapper.in("id", Arrays.asList(cartIds.split(",")));
        }
        List<StoreCart> carts = storeCartMapper.selectList(wrapper);

        List<StoreCartQueryVo> valid = new ArrayList<>();
        List<StoreCartQueryVo> invalid = new ArrayList<>();

        for (StoreCart storeCart : carts) {
            //获取店铺信息
            StoreQueryVo storeQueryVo = apiStoreServie.getStoreById(storeCart.getStoreId());
            StoreProductQueryVo storeProduct;
            if (storeCart.getCombinationId() > 0) {
                storeProduct = ObjectUtil.clone(storeCombinationMapper.combinatiionInfo(storeCart.getCombinationId()));
            } else if (storeCart.getSeckillId() > 0) {
                storeProduct = ObjectUtil.clone(storeSeckillMapper.seckillInfo(storeCart.getSeckillId()));
            } else if (storeCart.getBargainId() > 0) {
                storeProduct = ObjectUtil.clone(storeBargainMapper.bargainInfo(storeCart.getBargainId()));
            } else {
                //必须得重新克隆创建一个新对象
                storeProduct = ObjectUtil.clone(productService.getStoreProductById(storeCart.getProductId()));
            }

            StoreCartQueryVo storeCartQueryVo = cartMap.toDto(storeCart);
            storeCartQueryVo.setStoreName(storeQueryVo.getName());
            storeCartQueryVo.setStoreImage(storeQueryVo.getLogo());
            if (ObjectUtil.isNull(storeProduct)) {
                StoreCart yxStoreCart = new StoreCart();
                yxStoreCart.setIsDel(1);
                storeCartMapper.update(yxStoreCart,
                        new QueryWrapper<StoreCart>().lambda().eq(StoreCart::getId, storeCart.getId()));
            } else if (storeProduct.getIsShow() == 0 || storeProduct.getIsDel() == 1 || storeProduct.getStock() == 0) {
                storeCartQueryVo.setProductInfo(storeProduct);
                invalid.add(storeCartQueryVo);
            } else {
                if (StrUtil.isNotEmpty(storeCart.getProductAttrUnique())) {
                    StoreProductAttrValue productAttrValue = productAttrService
                            .uniqueByAttrInfo(storeCart.getProductAttrUnique());
                    if (ObjectUtil.isNull(productAttrValue) || productAttrValue.getStock() == 0) {
                        storeCartQueryVo.setProductInfo(storeProduct);
                        invalid.add(storeCartQueryVo);
                    } else {
                        storeProduct.setAttrInfo(productAttrValue);
                        storeCartQueryVo.setProductInfo(storeProduct);

                        //设置真实价格
                        //设置VIP价格
                        double vipPrice;
                        if (storeCart.getCombinationId() > 0 || storeCart.getSeckillId() > 0
                                || storeCart.getBargainId() > 0) {
                            vipPrice = productAttrValue.getPrice().doubleValue();
                        } else if (storeCart.getIntegralId() > 0) {
                            vipPrice = storeProduct.getIntegralPrice().doubleValue();
                            storeCartQueryVo.setIntegral(storeProduct.getIntegral() * storeCart.getCartNum());
                        } else {
                            vipPrice = userService.setLevelPrice(productAttrValue.getPrice().doubleValue(), uid);
                        }
                        storeCartQueryVo.setTruePrice(vipPrice);
                        //设置会员价
                        storeCartQueryVo.setVipTruePrice(productAttrValue.getPrice().doubleValue());
                        storeCartQueryVo.setCostPrice(productAttrValue.getCost().doubleValue());
                        storeCartQueryVo.setTrueStock(productAttrValue.getStock());

                        valid.add(storeCartQueryVo);

                    }
                } else {
                    //设置VIP价格
                    //设置VIP价格
                    double vipPrice = 0d;
                    if (storeCart.getCombinationId() > 0 || storeCart.getSeckillId() > 0
                            || storeCart.getBargainId() > 0) {
                        vipPrice = storeProduct.getPrice().doubleValue();
                    } else if (storeCart.getIntegralId() > 0) {
                        vipPrice = storeProduct.getIntegralPrice().doubleValue();
                        storeCartQueryVo.setIntegral(storeProduct.getIntegral() * storeCart.getCartNum());
                    } else {
                        vipPrice = userService.setLevelPrice(storeProduct.getPrice().doubleValue(), uid);
                    }

                    storeCartQueryVo.setTruePrice(vipPrice);
                    //todo 设置会员价
                    storeCartQueryVo.setVipTruePrice(0d);
                    storeCartQueryVo.setCostPrice(storeProduct.getCost().doubleValue());
                    storeCartQueryVo.setTrueStock(storeProduct.getStock());
                    storeCartQueryVo.setProductInfo(storeProduct);

                    valid.add(storeCartQueryVo);
                }
            }

        }

        List<Map<String, Object>> storeCartItemList = new ArrayList<>();
        Map<Long, List<StoreCartQueryVo>> validCartList = valid.stream()
                .collect(Collectors.groupingBy(StoreCartQueryVo::getStoreId));
        final Integer[] goodsCount = { 0 };
        final BigDecimal[] goodsAmount = { new BigDecimal(0.00) };
        final Integer[] goodsIntegral = { 0 };
        final List<Long> productIds = new ArrayList<>();
        validCartList.forEach((k, cartItemAll) -> {
            Map<String, Object> map2 = Maps.newHashMap();
            List<StoreCartQueryVo> newCartItemList = new ArrayList<>();
            final Integer[] storeFoodsCount = { 0 };
            final BigDecimal[] storeGoodsAmount = { new BigDecimal(0.00) };
            final Integer[] storeGoodsIntegral = { 0 };
            cartItemAll.forEach((cart) -> {
                map2.put("storeLogo", cart.getStoreImage());
                map2.put("storeName", cart.getStoreName());
                map2.put("storeId", cart.getStoreId());
                goodsCount[0] += cart.getCartNum();
                storeFoodsCount[0] += cart.getCartNum();
                goodsAmount[0] = goodsAmount[0].add(BigDecimal.valueOf(cart.getTruePrice() * cart.getCartNum()));
                storeGoodsAmount[0] = storeGoodsAmount[0]
                        .add(BigDecimal.valueOf(cart.getTruePrice() * cart.getCartNum()));
                if (cart.getIntegral() != null && cart.getIntegral() > 0) {
                    goodsIntegral[0] += cart.getIntegral();
                    storeGoodsIntegral[0] += cart.getIntegral();
                }
                newCartItemList.add(cart);
                productIds.add(cart.getProductId());
            });
            map2.put("cartList", newCartItemList);
            map2.put("goodsCount", storeFoodsCount[0]);
            map2.put("goodsAmount", storeGoodsAmount[0]);
            map2.put("goodsIntegral", storeGoodsIntegral[0]);
            storeCartItemList.add(map2);
        });
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("valid", storeCartItemList);
        map.put("invalid", invalid);
        map.put("goodsAmount", goodsAmount[0]);
        map.put("goodsCount", goodsCount[0]);
        map.put("goodsIntegral", goodsIntegral[0]);
        return map;
    }

    /**
     * 添加购物车
     *
     * @param uid 用户id
     * @param productId 普通产品编号
     * @param cartNum 购物车数量
     * @param productAttrUnique 属性唯一值
     * @param type product
     * @param isNew 1 加入购物车直接购买 0 加入购物车
     * @param combinationId 拼团id
     * @param seckillId 秒杀id
     * @param bargainId 砍价id
     * @return
     */
    @Override
    public int addCart(long uid, long productId, int cartNum, String productAttrUnique, String type, int isNew,
                       long combinationId, long seckillId, long bargainId, long integralId) {
        StoreProductQueryVo productQueryVo = productService.getStoreProductById(productId);
        Integer usedIntegral = 0;
        UserQueryVo userInfo = new UserQueryVo();
        //todo 拼团
        if (combinationId > 0) {
            boolean isStock = apiStoreCombinationService.judgeCombinationStock(combinationId, cartNum);
            if (!isStock) {
                throw new ErrorRequestException("该产品库存不足");
            }

            StoreCombination storeCombination = apiStoreCombinationService.getCombination(combinationId);
            if (ObjectUtil.isNull(storeCombination)) {
                throw new ErrorRequestException("该产品已下架或删除");
            }
        } else if (seckillId > 0) {
            //秒杀
            StoreSeckill storeSeckill = apiStoreSeckillService.getSeckill(seckillId);
            if (ObjectUtil.isNull(storeSeckill)) {
                throw new ErrorRequestException("该产品已下架或删除");
            }
            if (storeSeckill.getStock() < cartNum) {
                throw new ErrorRequestException("该产品库存不足");
            }
            int seckillOrderCount = storeOrderService
                    .count(new QueryWrapper<StoreOrder>().eq("uid", uid).eq("paid", 1).eq("seckill_id", seckillId));
            if (storeSeckill.getNum() <= seckillOrderCount || storeSeckill.getNum() < cartNum) {
                throw new ErrorRequestException("每人限购:" + storeSeckill.getNum() + "件");
            }

        } else if (bargainId > 0) {
            //砍价
            StoreBargain storeBargain = apiStoreBargainService.getBargain(bargainId);
            if (ObjectUtil.isNull(storeBargain)) {
                throw new ErrorRequestException("该产品已下架或删除");
            }
            if (storeBargain.getStock() < cartNum) {
                throw new ErrorRequestException("该产品库存不足");
            }

        } else {
            if (ObjectUtil.isNull(productQueryVo)) {
                throw new ErrorRequestException("该产品已下架或删除");
            }

            int stock = productService.getProductStock(productId, productAttrUnique);
            if (stock < cartNum) {
                throw new ErrorRequestException("该产品库存不足" + cartNum);
            }
        }

        QueryWrapper<StoreCart> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid)
                .eq("type", type)
                .eq("is_pay", 0)
                .eq("is_del", 0)
                .eq("product_id", productId)
                .eq("is_new", isNew)
                .eq("product_attr_unique", productAttrUnique)
                .eq("combination_id", combinationId)
                .eq("bargain_id", bargainId)
                .eq("seckill_id", seckillId)
                .eq("integral_id", integralId)
                .orderByDesc("id")
                .last("limit 1");

        StoreCart cart = storeCartMapper.selectOne(wrapper);
        StoreCart storeCart = new StoreCart();

        storeCart.setBargainId(bargainId);
        storeCart.setCartNum(cartNum);
        storeCart.setCombinationId(combinationId);
        storeCart.setProductAttrUnique(productAttrUnique);
        storeCart.setProductId(productId);
        storeCart.setSeckillId(seckillId);
        storeCart.setIntegralId(integralId);
        storeCart.setType(type);
        storeCart.setUid(uid);
        storeCart.setIsNew(isNew);
        storeCart.setStoreId(productQueryVo.getStoreId());
        if (ObjectUtil.isNotNull(cart)) {
            if (isNew == 0) {
                storeCart.setCartNum(cartNum + cart.getCartNum());
            }
            storeCart.setId(cart.getId());
            storeCartMapper.updateById(storeCart);
        } else {
            //判断是否已经添加过
            storeCart.setAddTime(OrderUtil.getSecondTimestampTwo());
            storeCartMapper.insert(storeCart);
        }
        if (usedIntegral > 0) {
            userService.decIntegral(uid, usedIntegral);
            //积分流水
            UserBill userBill = new UserBill();
            userBill.setUid(uid);
            userBill.setTitle("积分抵扣");
            userBill.setLinkId(String.valueOf(storeCart.getId()));
            userBill.setCategory("integral");
            userBill.setType("deduction");
            userBill.setNumber(BigDecimal.valueOf(usedIntegral));
            userBill.setBalance(userInfo.getIntegral());
            userBill.setMark("积分兑换商品使用");
            userBill.setStatus(1);
            userBill.setPm(0);
            userBill.setAddTime(OrderUtil.getSecondTimestampTwo());
            billService.save(userBill);
        }

        return storeCart.getId().intValue();
    }

    @Override
    public int getUserCartNum(long uid, String type, int numType) {
        int num = 0;
        QueryWrapper<StoreCart> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid).eq("type", type).eq("is_pay", 0).eq("is_del", 0).eq("is_new", 0);
        if (numType > 0) {
            num = storeCartMapper.selectCount(wrapper);
        } else {
            num = storeCartMapper.cartSum(uid, type);
        }
        return num;
    }

    @Override
    public StoreCartQueryVo getStoreCartById(Serializable id) {
        return storeCartMapper.getStoreCartById(id);
    }

}
