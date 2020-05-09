package co.lq.modules.shop.web.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.resource.ClassPathResource;
import co.lq.annotation.AnonymousAccess;
import co.lq.common.api.ApiResult;
import co.lq.constant.ShopConstants;
import co.lq.modules.shop.service.ApiStoreProductService;
import co.lq.modules.shop.service.ApiStoreServie;
import co.lq.modules.shop.service.ApiSystemGroupDataService;
import co.lq.modules.shop.web.param.HomeNewProductQueryParam;
import co.lq.modules.shop.web.param.HomeRecommendProductQueryParam;
import co.lq.modules.shop.web.param.StoreQueryParam;
import co.lq.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

/**
 * @ClassName IndexController
 * @Author billy
 * @Date 2019/10/19
 **/

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "首页模块", tags = "商城:首页模块", description = "首页模块")
public class IndexController {

    private final ApiSystemGroupDataService systemGroupDataService;
    private final ApiStoreProductService    storeProductService;
    private final ApiStoreServie            storeServie;

    @AnonymousAccess
    //@Cacheable(cacheNames = ShopConstants.QSHOP_REDIS_INDEX_KEY)
    @GetMapping("/index")
    @ApiOperation(value = "首页数据", notes = "首页数据")
    public ApiResult<Map<String, Object>> index() {

        Map<String, Object> map = new LinkedHashMap<>();
        //banner
        map.put("banner", systemGroupDataService.getDatas("routine_home_banner"));
        //首页按钮
        map.put("menus", systemGroupDataService.getDatas("routine_home_menus"));
        //首页活动区域图片
        map.put("activity", systemGroupDataService.getDatas("routine_home_activity"));
        //热搜
        map.put("hot", systemGroupDataService.getDatas("routine_hot_search"));

        //推荐商品
        HomeRecommendProductQueryParam homeRecommendProductQueryParam = new HomeRecommendProductQueryParam();
        map.put("bastList", storeProductService.getHomeRecommentProductList(homeRecommendProductQueryParam, 0, 20));
        //首发新品
        HomeNewProductQueryParam homeNewProductQueryParam = new HomeNewProductQueryParam();
        map.put("firstList", storeProductService.getHomeNewProductList(homeNewProductQueryParam, 0, 20));
        //        //精品推荐
        //        map.put("bastList", storeProductService.getList(1, 6, 1));
        //        //首发新品
        //        map.put("firstList", storeProductService.getList(1, 6, 2));
        //        //促销单品
        //        map.put("benefit", storeProductService.getList(1, 3, 3));
        //        //热门榜单
        //        map.put("likeInfo", storeProductService.getList(1, 3, 4));
        //获取店铺
        StoreQueryParam storeQueryParam = new StoreQueryParam();
        map.put("shopList", storeServie.getShopList(storeQueryParam, 0, 3));

        //滚动
        map.put("roll", systemGroupDataService.getDatas("routine_home_roll_news"));

        return ApiResult.ok(map);
    }

    @AnonymousAccess
    @Cacheable(cacheNames = ShopConstants.QSHOP_REDIS_INDEX_KEY)
    @GetMapping("/bannerList")
    @ApiOperation(value = "首页数据banner", notes = "首页数据v")
    public ApiResult<Map<String, Object>> bannerList() {

        Map<String, Object> map = new LinkedHashMap<>();
        //banner
        map.put("banner", systemGroupDataService.getDatas("routine_home_banner"));

        return ApiResult.ok(map);
    }

    @AnonymousAccess
    @GetMapping("/search/keyword")
    @ApiOperation(value = "热门搜索关键字获取", notes = "热门搜索关键字获取")
    public ApiResult<List<String>> search() {
        List<Map<String, Object>> list = systemGroupDataService.getDatas("routine_hot_search");
        List<String> stringList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            stringList.add(map.get("title").toString());
        }
        return ApiResult.ok(stringList);
    }

    @AnonymousAccess
    @PostMapping("/image_base64")
    @ApiOperation(value = "获取图片base64", notes = "获取图片base64")
    @Deprecated
    public ApiResult<List<String>> imageBase64() {
        return ApiResult.ok(null);
    }

    @AnonymousAccess
    @GetMapping("/citys")
    @ApiOperation(value = "获取城市json", notes = "获取城市json")
    public ApiResult<String> cityJson() {
        String path = "city.json";
        String name = "city.json";
        try {
            File file = FileUtil.inputStreamToFile(new ClassPathResource(path).getStream(), name);
            FileReader fileReader = new FileReader(file, "UTF-8");
            String string = fileReader.readString();
            System.out.println(string);
            JSONObject jsonObject = JSON.parseObject(string);
            return ApiResult.ok(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();

            return ApiResult.fail("无数据");
        }

    }

}
