package co.lq.modules.shop.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.lq.annotation.AnonymousAccess;
import co.lq.common.api.ApiResult;
import co.lq.common.web.controller.BaseController;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.service.ApiHomeAdvertiseService;
import co.lq.modules.shop.web.param.HomeAdvertiseQueryParam;
import co.lq.modules.shop.web.vo.ArticleQueryVo;
import co.lq.modules.shop.web.vo.HomeAdvertiseQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 广告
 *
 * @author songbin
 * @since 2020年3月13日 下午11:02:49
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/advertise")
@Api(value = "广告模块", tags = "商城:广告模块", description = "广告模块")
public class HomeAdvertiseController extends BaseController {

    private final ApiHomeAdvertiseService apiHomeAdvertiseService;

    /**
     * 获取文章文章详情
     */
    @AnonymousAccess
    @GetMapping("/details/{id}")
    @ApiOperation(value = "广告详情", notes = "广告详情", response = ArticleQueryVo.class)
    public ApiResult getArticle(@PathVariable Integer id) throws Exception {
        HomeAdvertiseQueryVo homeAdvertiseQueryVo = apiHomeAdvertiseService.getAdvertiseById(id);
        return ApiResult.ok(homeAdvertiseQueryVo);
    }

    /**
     * 文章列表
     */
    @AnonymousAccess
    @GetMapping("/list")
    @ApiOperation(value = "广告列表", notes = "广告列表", response = ArticleQueryVo.class)
    public ApiResult getArticlePageList(HomeAdvertiseQueryParam homeAdvertiseQueryParam) {
        Paging<HomeAdvertiseQueryVo> paging = apiHomeAdvertiseService.getAdvertisePageList(homeAdvertiseQueryParam);
        return ApiResult.ok(paging.getRecords());
    }

}
