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
import co.lq.modules.shop.entity.FeightTemplate;
import co.lq.modules.shop.service.ApiFeightTemplateService;
import co.lq.modules.shop.web.param.FeightTemplateQueryParam;
import co.lq.modules.shop.web.vo.FeightTemplateQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 运费模版 前端控制器
 * </p>
 *
 * @author billy
 * @since 2020-05-03
 */
@Slf4j
@RestController
@RequestMapping("/feightTemplate")
@Api("运费模版 API")
public class FeightTemplateController extends BaseController {

    @Autowired
    private ApiFeightTemplateService apiFeightTemplateService;

    /**
     * 添加运费模版
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加FeightTemplate对象", notes = "添加运费模版", response = ApiResult.class)
    public ApiResult<Boolean> addFeightTemplate(@Valid @RequestBody FeightTemplate feightTemplate) throws Exception {
        boolean flag = apiFeightTemplateService.save(feightTemplate);
        return ApiResult.result(flag);
    }

    /**
     * 修改运费模版
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改FeightTemplate对象", notes = "修改运费模版", response = ApiResult.class)
    public ApiResult<Boolean> updateFeightTemplate(@Valid @RequestBody FeightTemplate feightTemplate) throws Exception {
        boolean flag = apiFeightTemplateService.updateById(feightTemplate);
        return ApiResult.result(flag);
    }

    /**
     * 删除运费模版
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除FeightTemplate对象", notes = "删除运费模版", response = ApiResult.class)
    public ApiResult<Boolean> deleteFeightTemplate(@Valid @RequestBody IdParam idParam) throws Exception {
        boolean flag = apiFeightTemplateService.removeById(idParam.getId());
        return ApiResult.result(flag);
    }

    /**
     * 获取运费模版
     */
    @PostMapping("/info")
    @ApiOperation(value = "获取FeightTemplate对象详情", notes = "查看运费模版", response = FeightTemplateQueryVo.class)
    public ApiResult<FeightTemplateQueryVo> getFeightTemplate(@Valid @RequestBody IdParam idParam) throws Exception {
        FeightTemplateQueryVo feightTemplateQueryVo = apiFeightTemplateService.getFeightTemplateById(idParam.getId());
        return ApiResult.ok(feightTemplateQueryVo);
    }

    /**
     * 运费模版分页列表
     */
    @PostMapping("/getPageList")
    @ApiOperation(value = "获取FeightTemplate分页列表", notes = "运费模版分页列表", response = FeightTemplateQueryVo.class)
    public ApiResult<Paging<FeightTemplateQueryVo>> getFeightTemplatePageList(@Valid @RequestBody(required = false) FeightTemplateQueryParam feightTemplateQueryParam)
            throws Exception {
        Paging<FeightTemplateQueryVo> paging = apiFeightTemplateService
                .getFeightTemplatePageList(feightTemplateQueryParam);
        return ApiResult.ok(paging);
    }

}
