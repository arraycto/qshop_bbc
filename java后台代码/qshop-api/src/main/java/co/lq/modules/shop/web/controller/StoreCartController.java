package co.lq.modules.shop.web.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.util.ObjectUtil;
import co.lq.annotation.AnonymousAccess;
import co.lq.aop.log.Log;
import co.lq.common.api.ApiResult;
import co.lq.common.web.controller.BaseController;
import co.lq.modules.shop.service.ApiStoreCartService;
import co.lq.modules.shop.web.param.CartIdsParm;
import co.lq.modules.shop.web.param.StoreCartQueryParam;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 购物车控制器
 * </p>
 *
 * @author billy
 * @since 2019-10-25
 */
@Slf4j
@RestController
@Api(value = "购物车", tags = "商城:购物车", description = "购物车")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StoreCartController extends BaseController {

    private final ApiStoreCartService apiStoreCartService;

    /**
     * 购物车 获取数量
     */
    @AnonymousAccess
    @GetMapping("/cart/count")
    @ApiOperation(value = "获取数量", notes = "获取数量")
    public ApiResult count(StoreCartQueryParam queryParam) {
        Map<String, Object> map = new LinkedHashMap<>();
        int uid;
        try {
            uid = SecurityUtils.getUserId().intValue();
        } catch (Exception e) {
            map.put("count", 0);
            return ApiResult.ok(map);
        }
        map.put("count", apiStoreCartService.getUserCartNum(uid, "product", queryParam.getNumType().intValue()));
        return ApiResult.ok(map);
    }

    /**
     * 购物车 添加
     */
    @Log(value = "添加购物车", type = 1)
    @PostMapping("/cart/add")
    @ApiOperation(value = "添加购物车", notes = "添加购物车")
    public ApiResult add(@RequestBody String jsonStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        Map<String, Object> map = new LinkedHashMap<>();
        int uid = SecurityUtils.getUserId().intValue();
        if (ObjectUtil.isNull(jsonObject.get("cartNum")) || ObjectUtil.isNull(jsonObject.get("productId"))) {
            return ApiResult.fail("参数有误");
        }
        Integer cartNum = jsonObject.getInteger("cartNum");
        if (ObjectUtil.isNull(cartNum)) {
            return ApiResult.fail("购物车数量参数有误");
        }
        if (cartNum <= 0) {
            return ApiResult.fail("购物车数量必须大于0");
        }
        int isNew = 1;
        if (ObjectUtil.isNotNull(jsonObject.get("new"))) {
            isNew = jsonObject.getInteger("new");
        }
        Integer productId = jsonObject.getInteger("productId");
        if (ObjectUtil.isNull(productId)) {
            return ApiResult.fail("产品参数有误");
        }
        String uniqueId = jsonObject.get("uniqueId").toString();

        //拼团
        long combinationId = 0;
        if (ObjectUtil.isNotNull(jsonObject.get("combinationId"))) {
            combinationId = jsonObject.getLong("combinationId");
        }

        //秒杀
        long seckillId = 0;
        if (ObjectUtil.isNotNull(jsonObject.get("secKillId"))) {
            seckillId = jsonObject.getLong("secKillId");
        }

        //秒杀
        long bargainId = 0;
        if (ObjectUtil.isNotNull(jsonObject.get("bargainId"))) {
            bargainId = jsonObject.getLong("bargainId");
        }

        long integralId = 0;
        if (ObjectUtil.isNotNull(jsonObject.get("integralId"))) {
            integralId = jsonObject.getInteger("integralId");
        }

        try {
            map.put("cartId", apiStoreCartService.addCart(uid, productId, cartNum, uniqueId, "product", isNew,
                    combinationId, seckillId, bargainId, integralId));
        } catch (Exception e) {
            return ApiResult.fail(e.getMessage());
        }
        return ApiResult.ok(map);
    }

    /**
     * 购物车列表
     */
    @Log(value = "查看购物车", type = 1)
    @PostMapping("/cart/list")
    @ApiOperation(value = "购物车列表", notes = "购物车列表")
    public ApiResult getList() {
        long uid = SecurityUtils.getUserId();
        return ApiResult.ok(apiStoreCartService.getUserProductCartList(uid, "", 0));
    }

    /**
     * 修改产品数量
     */
    @PostMapping("/cart/num")
    @ApiOperation(value = "修改产品数量", notes = "修改产品数量")
    public ApiResult<Object> cartNum(@RequestBody String jsonStr) {
        int uid = SecurityUtils.getUserId().intValue();
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        if (ObjectUtil.isNull(jsonObject.get("id")) || ObjectUtil.isNull(jsonObject.get("number"))) {
            ApiResult.fail("参数错误");
        }

        try {
            apiStoreCartService.changeUserCartNum(jsonObject.getInteger("id"), jsonObject.getInteger("number"), uid);
        } catch (Exception e) {
            return ApiResult.fail(e.getMessage());
        }
        return ApiResult.ok("ok");
    }

    /**
     * 购物车删除产品
     */
    @PostMapping("/cart/del")
    @ApiOperation(value = "购物车删除产品", notes = "购物车删除产品")
    public ApiResult<Object> cartDel(@Validated @RequestBody CartIdsParm parm) {
        int uid = SecurityUtils.getUserId().intValue();
        apiStoreCartService.removeUserCart(uid, parm.getIds());

        return ApiResult.ok("ok");
    }

}
