package co.lq.modules.shop.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.util.ObjectUtil;
import co.lq.aop.log.Log;
import co.lq.common.api.ApiResult;
import co.lq.common.web.controller.BaseController;
import co.lq.modules.shop.service.ApiStoreCouponIssueService;
import co.lq.modules.shop.service.ApiStoreCouponUserService;
import co.lq.modules.shop.web.param.StoreCouponQueryParam;
import co.lq.modules.shop.web.vo.StoreCouponUserQueryVo;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 优惠券 todo
 * </p>
 *
 * @author billy
 * @since 2019-10-02
 */
@Slf4j
@RestController
@Api(value = "优惠券", tags = "营销:优惠券", description = "优惠券")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CouponController extends BaseController {

    private final ApiStoreCouponIssueService couponIssueService;
    private final ApiStoreCouponUserService  storeCouponUserService;

    /**
     * 可领取优惠券列表
     */
    @Log(value = "查看优惠券", type = 1)
    @GetMapping("/coupons")
    @ApiOperation(value = "可领取优惠券列表", notes = "可领取优惠券列表")
    public ApiResult<Object> getList(StoreCouponQueryParam queryParam) {
        int uid = SecurityUtils.getUserId().intValue();
        return ApiResult.ok(couponIssueService.getCouponList(queryParam.getPage().intValue(),
                queryParam.getLimit().intValue(), uid));
    }

    /**
     * 领取优惠券
     */
    @Log(value = "领取优惠券", type = 1)
    @PostMapping("/coupon/receive")
    @ApiOperation(value = "领取优惠券", notes = "领取优惠券")
    public ApiResult<Object> receive(@RequestBody String jsonStr) {
        int uid = SecurityUtils.getUserId().intValue();
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        if (ObjectUtil.isNull(jsonObject.get("couponId"))) {
            ApiResult.fail("参数错误");
        }
        couponIssueService.issueUserCoupon(Integer.valueOf(jsonObject.get("couponId").toString()), uid);
        return ApiResult.ok("ok");
    }

    /**
     * 用户已领取优惠券
     */
    @GetMapping("/coupons/user/{type}")
    @ApiOperation(value = "用户已领取优惠券", notes = "用户已领取优惠券")
    public ApiResult<Object> getUserList(@PathVariable Integer type) {
        if (ObjectUtil.isEmpty(type))
            type = 0;
        int uid = SecurityUtils.getUserId().intValue();
        List<StoreCouponUserQueryVo> list = null;
        switch (type) {
            case 0:
                list = storeCouponUserService.getUserCoupon(uid, 0);
                break;
            case 1:
                list = storeCouponUserService.getUserCoupon(uid, 1);
                break;
            case 2:
                list = storeCouponUserService.getUserCoupon(uid, 2);
                break;
            default:
                list = storeCouponUserService.getUserCoupon(uid, 3);
        }
        return ApiResult.ok(list);
    }

    /**
     * 优惠券 订单获取
     */
    @GetMapping("/coupons/order/{price}")
    @ApiOperation(value = "优惠券订单获取", notes = "优惠券订单获取")
    public ApiResult<Object> orderCoupon(@PathVariable Double price) {
        int uid = SecurityUtils.getUserId().intValue();
        return ApiResult.ok(storeCouponUserService.beUsableCouponList(uid, price));
    }

}
