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
import co.lq.modules.shop.service.ApiCmsSubjectService;
import co.lq.modules.shop.web.param.CmsSubjectQueryParam;
import co.lq.modules.shop.web.vo.ArticleQueryVo;
import co.lq.modules.shop.web.vo.CmsSubjectQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 文章 前端控制器
 * </p>
 *
 * @author billy
 * @since 2019-10-02
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/article")
@Api(value = "文章模块", tags = "商城:文章模块", description = "文章模块")
public class ArticleController extends BaseController {

    private final ApiCmsSubjectService apiCmsSubjectService;

    /**
     * 获取文章文章详情
     */
    @AnonymousAccess
    @GetMapping("/details/{id}")
    @ApiOperation(value = "文章详情", notes = "文章详情", response = ArticleQueryVo.class)
    public ApiResult getArticle(@PathVariable Long id) throws Exception {
        CmsSubjectQueryVo cmsSubjectQueryVo = apiCmsSubjectService.getCmsSubjectById(id);
        apiCmsSubjectService.incReadCount(id);
        return ApiResult.ok(cmsSubjectQueryVo);
    }

    /**
     * 文章列表
     */
    @AnonymousAccess
    @GetMapping("/list")
    @ApiOperation(value = "文章列表", notes = "文章列表", response = ArticleQueryVo.class)
    public ApiResult getArticlePageList(CmsSubjectQueryParam queryParam) throws Exception {
        Paging<CmsSubjectQueryVo> paging = apiCmsSubjectService.getCmsSubjectPageList(queryParam);
        return ApiResult.ok(paging.getRecords());
    }

}
