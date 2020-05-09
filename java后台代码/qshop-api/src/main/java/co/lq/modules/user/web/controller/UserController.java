package co.lq.modules.user.web.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import co.lq.annotation.AnonymousAccess;
import co.lq.aop.log.Log;
import co.lq.common.api.ApiResult;
import co.lq.common.web.controller.BaseController;
import co.lq.modules.order.service.StoreOrderService;
import co.lq.modules.shop.service.ApiStoreProductRelationService;
import co.lq.modules.shop.service.ApiSystemGroupDataService;
import co.lq.modules.user.entity.User;
import co.lq.modules.user.service.UserBillService;
import co.lq.modules.user.service.UserLevelService;
import co.lq.modules.user.service.UserService;
import co.lq.modules.user.service.UserSignService;
import co.lq.modules.user.web.param.UserEditParam;
import co.lq.modules.user.web.param.UserSignQueryParam;
import co.lq.modules.user.web.vo.UserLevelQueryVo;
import co.lq.modules.user.web.vo.UserQueryVo;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户控制器
 * </p>
 *
 * @author billy
 * @since 2019-10-16
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "用户中心", tags = "用户:用户中心", description = "用户中心")
public class UserController extends BaseController {

    private final UserService                    userService;
    private final ApiSystemGroupDataService      systemGroupDataService;
    private final StoreOrderService              orderService;
    private final ApiStoreProductRelationService relationService;
    private final UserSignService                userSignService;
    private final UserBillService                userBillService;
    private final UserLevelService               userLevelService;

    private static Lock                          lock = new ReentrantLock(false);

    /**
     * 用户资料
     */
    @GetMapping("/userinfo")
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息", response = UserQueryVo.class)
    public ApiResult<Object> userInfo() {
        long uid = SecurityUtils.getUserId();

        //update count
        userService.setUserSpreadCount(uid);
        return ApiResult.ok(userService.getUserById(uid));
    }

    /**
     * 获取个人中心菜单
     */
    @Log(value = "进入用户中心", type = 1)
    @GetMapping("/menu/user")
    @ApiOperation(value = "获取个人中心菜单", notes = "获取个人中心菜单")
    public ApiResult<Map<String, Object>> userMenu() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("routine_my_menus", systemGroupDataService.getDatas("routine_my_menus"));
        return ApiResult.ok(map);
    }

    /**
     * 个人中心
     */
    @GetMapping("/user")
    @ApiOperation(value = "个人中心", notes = "个人中心")
    public ApiResult<Object> user() {
        int uid = SecurityUtils.getUserId().intValue();
        UserQueryVo userQueryVo = userService.getNewUserById(uid);

        if (userQueryVo.getLevel() > 0) {
            userQueryVo.setVip(true);
            UserLevelQueryVo userLevelQueryVo = userLevelService.getUserLevelById(userQueryVo.getLevel());
            userQueryVo.setVipIcon(userLevelQueryVo.getIcon());
            userQueryVo.setVipId(userQueryVo.getLevel());
            userQueryVo.setVipName(userLevelQueryVo.getName());
        }
        return ApiResult.ok(userQueryVo);
    }

    /**
     * 订单统计数据
     */
    @GetMapping("/order/data")
    @ApiOperation(value = "订单统计数据", notes = "订单统计数据")
    public ApiResult<Object> orderData() {
        int uid = SecurityUtils.getUserId().intValue();
        return ApiResult.ok(orderService.orderData(uid));
    }

    /**
     * 获取收藏产品
     */
    @GetMapping("/collect/user")
    @ApiOperation(value = "获取收藏产品", notes = "获取收藏产品")
    public ApiResult<Object> collectUser(@RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "limit", defaultValue = "10") int limit) {
        int uid = SecurityUtils.getUserId().intValue();
        return ApiResult.ok(relationService.userCollectProduct(page, limit, uid));
    }

    /**
     * 用户资金统计
     */
    @GetMapping("/user/balance")
    @ApiOperation(value = "用户资金统计", notes = "用户资金统计")
    public ApiResult<Object> collectUser() {
        long uid = SecurityUtils.getUserId();
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("now_money", userService.getUserById(uid).getNowMoney());
        map.put("orderStatusSum", orderService.orderData(uid).getSumPrice());
        map.put("recharge", 0);
        return ApiResult.ok(map);
    }

    /**
     * 获取活动状态
     */
    @AnonymousAccess
    @GetMapping("/user/activity")
    @ApiOperation(value = "获取活动状态", notes = "获取活动状态")
    @Deprecated
    public ApiResult<Object> activity() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("is_bargin", false);
        map.put("is_pink", false);
        map.put("is_seckill", false);
        return ApiResult.ok(map);
    }

    /**
     * 签到用户信息
     */
    @PostMapping("/sign/user")
    @ApiOperation(value = "签到用户信息", notes = "签到用户信息")
    public ApiResult<Object> sign(@RequestBody String jsonStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        long uid = SecurityUtils.getUserId();
        UserQueryVo userQueryVo = userService.getUserById(uid);
        int sumSignDay = userSignService.getSignSumDay(uid);
        boolean isDaySign = userSignService.getToDayIsSign(uid);
        boolean isYesterDaySign = userSignService.getYesterDayIsSign(uid);
        userQueryVo.setSumSignDay(sumSignDay);
        userQueryVo.setIsDaySign(isDaySign);
        userQueryVo.setIsYesterDaySign(isYesterDaySign);
        if (!isDaySign && !isYesterDaySign)
            userQueryVo.setSignNum(0);
        return ApiResult.ok(userQueryVo);
    }

    /**
     * 签到配置
     */
    @GetMapping("/sign/config")
    @ApiOperation(value = "签到配置", notes = "签到配置")
    public ApiResult<Object> signConfig() {
        return ApiResult.ok(systemGroupDataService.getDatas("sign_day_num"));
    }

    /**
     * 签到列表
     */
    @GetMapping("/sign/list")
    @ApiOperation(value = "签到列表", notes = "签到列表")
    public ApiResult<Object> signList(UserSignQueryParam queryParam) {
        int uid = SecurityUtils.getUserId().intValue();
        return ApiResult.ok(
                userSignService.getSignList(uid, queryParam.getPage().intValue(), queryParam.getLimit().intValue()));
    }

    /**
     * 签到列表（年月）
     */
    @GetMapping("/sign/month")
    @ApiOperation(value = "签到列表（年月）", notes = "签到列表（年月）")
    public ApiResult<Object> signMonthList(UserSignQueryParam queryParam) {
        int uid = SecurityUtils.getUserId().intValue();
        return ApiResult.ok(userBillService.getUserBillList(queryParam.getPage().intValue(),
                queryParam.getLimit().intValue(), uid, 5));
    }

    /**
     * 开始签到
     */
    @PostMapping("/sign/integral")
    @ApiOperation(value = "开始签到", notes = "开始签到")
    public ApiResult<Object> signIntegral() {
        int uid = SecurityUtils.getUserId().intValue();
        boolean isDaySign = userSignService.getToDayIsSign(uid);
        if (isDaySign)
            return ApiResult.fail("已签到");
        int integral = 0;
        try {
            lock.lock();
            integral = userSignService.sign(uid);
        } finally {
            lock.unlock();
        }

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("integral", integral);
        return ApiResult.ok(map, "签到获得" + integral + "积分");
    }

    @PostMapping("/user/edit")
    @ApiOperation(value = "用户修改信息", notes = "用修改信息")
    public ApiResult<Object> edit(@Validated @RequestBody UserEditParam param) {
        Long uid = SecurityUtils.getUserId();

        User user = new User();
        user.setAvatar(param.getAvatar());
        user.setNickname(param.getNickname());
        user.setUid(uid);

        userService.updateById(user);

        return ApiResult.ok("修改成功");
    }

}
