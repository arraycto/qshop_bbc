package co.lq.modules.shop.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import co.lq.annotation.AnonymousAccess;
import co.lq.common.api.ApiResult;
import co.lq.common.web.controller.BaseController;
import co.lq.modules.shop.service.ApiCatalogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 店铺类目
 *
 * @author songbin
 * @since 2020年3月11日 下午5:42:17
 */
@Slf4j
@RestController
@Api(value = "店铺类目", tags = "商城:店铺类目", description = "店铺类目")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CatalogController extends BaseController {

    private final ApiCatalogService apiCatalogService;

    /**
     * 商品分类列表
     */
    @AnonymousAccess
    @GetMapping("/catalog")
    @ApiOperation(value = "店铺类目列表", notes = "店铺类目列表")
    public ApiResult getCatalogPageList() {
        return ApiResult.ok(apiCatalogService.getList());
    }

    /**
     * 获取最后一级类目id
     */
    @AnonymousAccess
    @GetMapping("/firstCatalog")
    @ApiOperation(value = "店铺类目列表", notes = "店铺类目列表")
    public ApiResult getFirstCatalogPageList() {
        return ApiResult.ok(apiCatalogService.getFirstList());
    }
}
