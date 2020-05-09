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
 * <p>
 * 商品分类前端控制器
 * </p>
 *
 * @author billy
 * @since 2019-10-22
 */
@Slf4j
@RestController
@Api(value = "商品分类", tags = "商城:商品分类", description = "商品分类")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryController extends BaseController {

    private final ApiCatalogService apiCategoryService;

    /**
     * 商品分类列表
     */
    @AnonymousAccess
    @GetMapping("/category")
    @ApiOperation(value = "商品分类列表", notes = "商品分类列表")
    public ApiResult getStoreCategoryPageList() {
        return ApiResult.ok(apiCategoryService.getList());
    }

}
