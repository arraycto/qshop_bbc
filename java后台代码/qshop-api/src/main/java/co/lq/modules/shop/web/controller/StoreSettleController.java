package co.lq.modules.shop.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.lq.annotation.AnonymousAccess;
import co.lq.aop.log.Log;
import co.lq.common.api.ApiResult;
import co.lq.common.web.controller.BaseController;
import co.lq.modules.shop.entity.StoreSettle;
import co.lq.modules.shop.service.ApiStoreServie;
import co.lq.modules.shop.service.ApiStoreSettleService;
import co.lq.modules.shop.web.param.StoreQueryParam;
import co.lq.modules.shop.web.param.StoreSettleQueryParam;
import co.lq.modules.shop.web.vo.StoreQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 店铺入驻信息
 *
 * @author songbin
 * @since 2020年3月31日 下午2:24:06
 */
@Slf4j
@RestController
@Api(value = "店铺入驻信息", tags = "商城:店铺入驻信息", description = "店铺入驻信息")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StoreSettleController extends BaseController {

    private final ApiStoreSettleService apiStoreSettleServie;
    private final ApiStoreServie        apiStoreServie;

    /**
     * 店铺列表
     */
    @AnonymousAccess
    @GetMapping("/shopSettleList")
    @ApiOperation(value = "店铺入驻信息列表", notes = "店铺入驻信息列表")
    public ApiResult getShopSettlePageList() {
        return ApiResult.ok(apiStoreSettleServie.getList());
    }

    /**
     * 查询店铺信息
     */
    @GetMapping("/getShopSettleByUStoreId")
    @ApiOperation(value = "查询店铺入驻信息", notes = "查询店铺入驻信息")
    public ApiResult getShopSettleByUStoreId(@Param(value = "uid") Long uid) {
        //        Long uid = SecurityUtils.getUserId();
        StoreQueryParam storeQueryParam = new StoreQueryParam();
        storeQueryParam.setUid(uid);
        StoreQueryVo storeQueryVo = apiStoreServie.getShopInfoByUid(storeQueryParam);

        StoreSettleQueryParam storeSettleQueryParam = new StoreSettleQueryParam();
        storeSettleQueryParam.setStoreId(storeQueryVo.getId());
        return ApiResult.ok(apiStoreSettleServie.getShopSetleByStoreId(storeSettleQueryParam));
    }

    /**
     * 入驻存储店铺信息
     */
    @Log(value = "入驻存储店铺信息", type = 1)
    @PostMapping("/saveShopSettle")
    @ApiOperation(value = "入驻存储店铺信息", notes = "入驻存储店铺信息")
    public ApiResult saveShop(@RequestBody StoreSettle storeSettle) {
        //        Long uid = SecurityUtils.getUserId();
        StoreQueryParam storeQueryParam = new StoreQueryParam();
        storeQueryParam.setUid(storeSettle.getUid());
        StoreQueryVo storeQueryVo = apiStoreServie.getShopInfoByUid(storeQueryParam);

        storeSettle.setStoreId(storeQueryVo.getId());
        return ApiResult.ok(apiStoreSettleServie.saveShopSettle(storeSettle));
    }
}
