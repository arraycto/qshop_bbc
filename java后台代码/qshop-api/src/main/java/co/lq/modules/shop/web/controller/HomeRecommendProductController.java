package co.lq.modules.shop.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.lq.common.api.ApiResult;
import co.lq.common.web.controller.BaseController;
import co.lq.common.web.param.IdParam;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.HomeRecommendProduct;
import co.lq.modules.shop.service.ApiHomeRecommendProductService;
import co.lq.modules.shop.web.param.HomeRecommendProductQueryParam;
import co.lq.modules.shop.web.vo.HomeRecommendProductQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 人气推荐商品表 前端控制器
 * </p>
 *
 * @author billy
 * @since 2020-04-18
 */
@Slf4j
@RestController
@RequestMapping("/homeRecommendProduct")
@Api("人气推荐商品表 API")
public class HomeRecommendProductController extends BaseController {

    @Autowired
    private ApiHomeRecommendProductService apiHomeRecommendProductService;

    /**
     * 添加人气推荐商品表
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加HomeRecommendProduct对象", notes = "添加人气推荐商品表", response = ApiResult.class)
    public ApiResult<Boolean> addHomeRecommendProduct(@Valid @RequestBody HomeRecommendProduct homeRecommendProduct)
            throws Exception {
        boolean flag = apiHomeRecommendProductService.save(homeRecommendProduct);
        return ApiResult.result(flag);
    }

    /**
     * 修改人气推荐商品表
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改HomeRecommendProduct对象", notes = "修改人气推荐商品表", response = ApiResult.class)
    public ApiResult<Boolean> updateHomeRecommendProduct(@Valid @RequestBody HomeRecommendProduct homeRecommendProduct)
            throws Exception {
        boolean flag = apiHomeRecommendProductService.updateById(homeRecommendProduct);
        return ApiResult.result(flag);
    }

    /**
     * 删除人气推荐商品表
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除HomeRecommendProduct对象", notes = "删除人气推荐商品表", response = ApiResult.class)
    public ApiResult<Boolean> deleteHomeRecommendProduct(@Valid @RequestBody IdParam idParam) throws Exception {
        boolean flag = apiHomeRecommendProductService.removeById(idParam.getId());
        return ApiResult.result(flag);
    }

    /**
     * 获取人气推荐商品表
     */
    @PostMapping("/info")
    @ApiOperation(value = "获取HomeRecommendProduct对象详情", notes = "查看人气推荐商品表", response = HomeRecommendProductQueryVo.class)
    public ApiResult<HomeRecommendProductQueryVo> getHomeRecommendProduct(@Valid @RequestBody IdParam idParam)
            throws Exception {
        HomeRecommendProductQueryVo homeRecommendProductQueryVo = apiHomeRecommendProductService
                .getHomeRecommendProductById(idParam.getId());
        return ApiResult.ok(homeRecommendProductQueryVo);
    }

    /**
     * 人气推荐商品表分页列表
     */
    @PostMapping("/getPageList")
    @ApiOperation(value = "获取HomeRecommendProduct分页列表", notes = "人气推荐商品表分页列表", response = HomeRecommendProductQueryVo.class)
    public ApiResult<Paging<HomeRecommendProductQueryVo>> getHomeRecommendProductPageList(@Valid @RequestBody(required = false) HomeRecommendProductQueryParam homeRecommendProductQueryParam)
            throws Exception {
        Paging<HomeRecommendProductQueryVo> paging = apiHomeRecommendProductService
                .getHomeRecommendProductPageList(homeRecommendProductQueryParam);
        return ApiResult.ok(paging);
    }

}
