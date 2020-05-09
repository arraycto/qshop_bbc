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
import co.lq.modules.shop.entity.StoreCollect;
import co.lq.modules.shop.service.ApiStoreCollectService;
import co.lq.modules.shop.web.param.StoreCollectQueryParam;
import co.lq.modules.shop.web.vo.StoreCollectQueryVo;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 店铺收藏表 前端控制器
 * </p>
 *
 * @author billy
 * @since 2020-04-05
 */
@Slf4j
@RestController
@RequestMapping("/storeCollect")
@Api("店铺收藏表 API")
public class StoreCollectController extends BaseController {

    @Autowired
    private ApiStoreCollectService apiStoreCollectService;

    /**
     * 添加店铺收藏表
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加StoreCollect对象", notes = "添加店铺收藏表", response = ApiResult.class)
    public ApiResult<Boolean> addStoreCollect(@Valid @RequestBody StoreCollect storeCollect) throws Exception {
        boolean flag = apiStoreCollectService.save(storeCollect);
        return ApiResult.result(flag);
    }

    /**
     * 修改店铺收藏表
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改StoreCollect对象", notes = "修改店铺收藏表", response = ApiResult.class)
    public ApiResult<Boolean> updateStoreCollect(@Valid @RequestBody StoreCollect storeCollect) throws Exception {
        boolean flag = apiStoreCollectService.updateById(storeCollect);
        return ApiResult.result(flag);
    }

    /**
     * 删除店铺收藏表
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除StoreCollect对象", notes = "删除店铺收藏表", response = ApiResult.class)
    public ApiResult<Boolean> deleteStoreCollect(@Valid @RequestBody IdParam idParam) throws Exception {
        boolean flag = apiStoreCollectService.removeById(idParam.getId());
        return ApiResult.result(flag);
    }

    /**
     * 获取店铺收藏表
     */
    @PostMapping("/info")
    @ApiOperation(value = "获取StoreCollect对象详情", notes = "查看店铺收藏表", response = StoreCollectQueryVo.class)
    public ApiResult<StoreCollectQueryVo> getStoreCollect(@Valid @RequestBody IdParam idParam) throws Exception {
        StoreCollectQueryVo storeCollectQueryVo = apiStoreCollectService.getStoreCollectById(idParam.getId());
        return ApiResult.ok(storeCollectQueryVo);
    }

    /**
     * 店铺收藏表分页列表
     */
    @PostMapping("/getPageList")
    @ApiOperation(value = "获取StoreCollect分页列表", notes = "店铺收藏表分页列表", response = StoreCollectQueryVo.class)
    public ApiResult<Paging<StoreCollectQueryVo>> getStoreCollectPageList(@Valid @RequestBody(required = false) StoreCollectQueryParam storeCollectQueryParam)
            throws Exception {
        Long uid = SecurityUtils.getUserId();
        storeCollectQueryParam.setUid(uid);
        Paging<StoreCollectQueryVo> paging = apiStoreCollectService.getStoreCollectPageList(storeCollectQueryParam);
        return ApiResult.ok(paging);
    }

}
