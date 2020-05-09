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
import co.lq.modules.shop.entity.StoreCatalogRelation;
import co.lq.modules.shop.service.ApiStoreCatalogRelationService;
import co.lq.modules.shop.web.param.StoreCatalogRelationQueryParam;
import co.lq.modules.shop.web.vo.StoreCatalogRelationQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 店铺类目关联表 前端控制器
 * </p>
 *
 * @author billy
 * @since 2020-04-23
 */
@Slf4j
@RestController
@RequestMapping("/storeCatalogRelation")
@Api("店铺类目关联表 API")
public class StoreCatalogRelationController extends BaseController {

    @Autowired
    private ApiStoreCatalogRelationService apiStoreCatalogRelationService;

    /**
     * 添加店铺类目关联表
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加StoreCatalogRelation对象", notes = "添加店铺类目关联表", response = ApiResult.class)
    public ApiResult<Boolean> addStoreCatalogRelation(@Valid @RequestBody StoreCatalogRelation storeCatalogRelation)
            throws Exception {
        boolean flag = apiStoreCatalogRelationService.save(storeCatalogRelation);
        return ApiResult.result(flag);
    }

    /**
     * 修改店铺类目关联表
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改StoreCatalogRelation对象", notes = "修改店铺类目关联表", response = ApiResult.class)
    public ApiResult<Boolean> updateStoreCatalogRelation(@Valid @RequestBody StoreCatalogRelation storeCatalogRelation)
            throws Exception {
        boolean flag = apiStoreCatalogRelationService.updateById(storeCatalogRelation);
        return ApiResult.result(flag);
    }

    /**
     * 删除店铺类目关联表
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除StoreCatalogRelation对象", notes = "删除店铺类目关联表", response = ApiResult.class)
    public ApiResult<Boolean> deleteStoreCatalogRelation(@Valid @RequestBody IdParam idParam) throws Exception {
        boolean flag = apiStoreCatalogRelationService.removeById(idParam.getId());
        return ApiResult.result(flag);
    }

    /**
     * 获取店铺类目关联表
     */
    @PostMapping("/info")
    @ApiOperation(value = "获取StoreCatalogRelation对象详情", notes = "查看店铺类目关联表", response = StoreCatalogRelationQueryVo.class)
    public ApiResult<StoreCatalogRelationQueryVo> getStoreCatalogRelation(@Valid @RequestBody IdParam idParam)
            throws Exception {
        StoreCatalogRelationQueryVo storeCatalogRelationQueryVo = apiStoreCatalogRelationService
                .getStoreCatalogRelationById(idParam.getId());
        return ApiResult.ok(storeCatalogRelationQueryVo);
    }

    /**
     * 店铺类目关联表分页列表
     */
    @PostMapping("/getPageList")
    @ApiOperation(value = "获取StoreCatalogRelation分页列表", notes = "店铺类目关联表分页列表", response = StoreCatalogRelationQueryVo.class)
    public ApiResult<Paging<StoreCatalogRelationQueryVo>> getStoreCatalogRelationPageList(@Valid @RequestBody(required = false) StoreCatalogRelationQueryParam storeCatalogRelationQueryParam)
            throws Exception {
        Paging<StoreCatalogRelationQueryVo> paging = apiStoreCatalogRelationService
                .getStoreCatalogRelationPageList(storeCatalogRelationQueryParam);
        return ApiResult.ok(paging);
    }

}
