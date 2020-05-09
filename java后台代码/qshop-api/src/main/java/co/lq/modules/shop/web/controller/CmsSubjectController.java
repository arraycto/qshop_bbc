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
import co.lq.modules.shop.entity.CmsSubject;
import co.lq.modules.shop.service.ApiCmsSubjectService;
import co.lq.modules.shop.web.param.CmsSubjectQueryParam;
import co.lq.modules.shop.web.vo.CmsSubjectQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 专题表 前端控制器
 * </p>
 *
 * @author billy
 * @since 2020-04-19
 */
@Slf4j
@RestController
@RequestMapping("/cmsSubject")
@Api("专题表 API")
public class CmsSubjectController extends BaseController {

    @Autowired
    private ApiCmsSubjectService apiCmsSubjectService;

    /**
     * 添加专题表
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加CmsSubject对象", notes = "添加专题表", response = ApiResult.class)
    public ApiResult addCmsSubject(@Valid @RequestBody CmsSubject cmsSubject) throws Exception {
        boolean flag = apiCmsSubjectService.save(cmsSubject);
        return ApiResult.result(flag);
    }

    /**
     * 修改专题表
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改CmsSubject对象", notes = "修改专题表", response = ApiResult.class)
    public ApiResult updateCmsSubject(@Valid @RequestBody CmsSubject cmsSubject) throws Exception {
        boolean flag = apiCmsSubjectService.updateById(cmsSubject);
        return ApiResult.result(flag);
    }

    /**
     * 删除专题表
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除CmsSubject对象", notes = "删除专题表", response = ApiResult.class)
    public ApiResult deleteCmsSubject(@Valid @RequestBody IdParam idParam) throws Exception {
        boolean flag = apiCmsSubjectService.removeById(idParam.getId());
        return ApiResult.result(flag);
    }

    /**
     * 获取专题表
     */
    @PostMapping("/info")
    @ApiOperation(value = "获取CmsSubject对象详情", notes = "查看专题表", response = CmsSubjectQueryVo.class)
    public ApiResult getCmsSubject(@Valid @RequestBody IdParam idParam) throws Exception {
        CmsSubjectQueryVo cmsSubjectQueryVo = apiCmsSubjectService.getCmsSubjectById(idParam.getId());
        return ApiResult.ok(cmsSubjectQueryVo);
    }

    /**
     * 专题表分页列表
     */
    @PostMapping("/getPageList")
    @ApiOperation(value = "获取CmsSubject分页列表", notes = "专题表分页列表", response = CmsSubjectQueryVo.class)
    public ApiResult getCmsSubjectPageList(@Valid @RequestBody(required = false) CmsSubjectQueryParam cmsSubjectQueryParam)
            throws Exception {
        Paging<CmsSubjectQueryVo> paging = apiCmsSubjectService.getCmsSubjectPageList(cmsSubjectQueryParam);
        return ApiResult.ok(paging);
    }

}
