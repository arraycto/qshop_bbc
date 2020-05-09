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
import co.lq.modules.shop.entity.HomeNewProduct;
import co.lq.modules.shop.service.ApiHomeNewProductService;
import co.lq.modules.shop.web.param.HomeNewProductQueryParam;
import co.lq.modules.shop.web.vo.HomeNewProductQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 首页推荐商品表 前端控制器
 * </p>
 *
 * @author billy
 * @since 2020-04-18
 */
@Slf4j
@RestController
@RequestMapping("/homeNewProduct")
@Api("首页推荐商品表 API")
public class HomeNewProductController extends BaseController {

    @Autowired
    private ApiHomeNewProductService apiHomeNewProductService;

    /**
     * 添加首页推荐商品表
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加HomeNewProduct对象", notes = "添加首页推荐商品表", response = ApiResult.class)
    public ApiResult<Boolean> addHomeNewProduct(@Valid @RequestBody HomeNewProduct homeNewProduct) throws Exception {
        boolean flag = apiHomeNewProductService.save(homeNewProduct);
        return ApiResult.result(flag);
    }

    /**
     * 修改首页推荐商品表
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改HomeNewProduct对象", notes = "修改首页推荐商品表", response = ApiResult.class)
    public ApiResult<Boolean> updateHomeNewProduct(@Valid @RequestBody HomeNewProduct homeNewProduct) throws Exception {
        boolean flag = apiHomeNewProductService.updateById(homeNewProduct);
        return ApiResult.result(flag);
    }

    /**
     * 删除首页推荐商品表
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除HomeNewProduct对象", notes = "删除首页推荐商品表", response = ApiResult.class)
    public ApiResult<Boolean> deleteHomeNewProduct(@Valid @RequestBody IdParam idParam) throws Exception {
        boolean flag = apiHomeNewProductService.removeById(idParam.getId());
        return ApiResult.result(flag);
    }

    /**
     * 获取首页推荐商品表
     */
    @PostMapping("/info")
    @ApiOperation(value = "获取HomeNewProduct对象详情", notes = "查看首页推荐商品表", response = HomeNewProductQueryVo.class)
    public ApiResult<HomeNewProductQueryVo> getHomeNewProduct(@Valid @RequestBody IdParam idParam) throws Exception {
        HomeNewProductQueryVo homeNewProductQueryVo = apiHomeNewProductService.getHomeNewProductById(idParam.getId());
        return ApiResult.ok(homeNewProductQueryVo);
    }

    /**
     * 首页推荐商品表分页列表
     */
    @PostMapping("/getPageList")
    @ApiOperation(value = "获取HomeNewProduct分页列表", notes = "首页推荐商品表分页列表", response = HomeNewProductQueryVo.class)
    public ApiResult<Paging<HomeNewProductQueryVo>> getHomeNewProductPageList(@Valid @RequestBody(required = false) HomeNewProductQueryParam homeNewProductQueryParam)
            throws Exception {
        Paging<HomeNewProductQueryVo> paging = apiHomeNewProductService
                .getHomeNewProductPageList(homeNewProductQueryParam);
        return ApiResult.ok(paging);
    }

}
