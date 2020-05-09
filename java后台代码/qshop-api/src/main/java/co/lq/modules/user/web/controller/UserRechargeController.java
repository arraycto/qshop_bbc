package co.lq.modules.user.web.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.order.WxPayMwebOrderResult;
import com.github.binarywang.wxpay.exception.WxPayException;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import co.lq.common.api.ApiResult;
import co.lq.common.web.controller.BaseController;
import co.lq.enums.BillDetailEnum;
import co.lq.exception.ErrorRequestException;
import co.lq.modules.shop.service.ApiSystemConfigService;
import co.lq.modules.user.entity.WechatUser;
import co.lq.modules.user.service.UserRechargeService;
import co.lq.modules.user.service.WechatUserService;
import co.lq.modules.user.web.param.RechargeParam;
import co.lq.mp.service.PayService;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户充值 前端控制器
 * </p>
 *
 * @author billy
 * @since 2020-03-01
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "用户充值", tags = "用户:用户充值", description = "用户充值")
public class UserRechargeController extends BaseController {

    private final UserRechargeService    userRechargeService;
    private final ApiSystemConfigService systemConfigService;
    private final PayService             payService;
    private final WechatUserService      wechatUserService;

    /**
     * 公众号充值/H5充值
     */
    @PostMapping("/recharge/wechat")
    @ApiOperation(value = "公众号充值/H5充值", notes = "公众号充值/H5充值", response = ApiResult.class)
    public ApiResult<Map<String, Object>> add(@Valid @RequestBody RechargeParam param) {
        int uid = SecurityUtils.getUserId().intValue();
        String money = systemConfigService.getData("store_user_min_recharge");
        Double newMoney = 0d;
        if (StrUtil.isNotEmpty(money))
            newMoney = Double.valueOf(money);
        if (newMoney > param.getPrice())
            throw new ErrorRequestException("充值金额不能低于" + newMoney);

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type", param.getFrom());

        //生成分布式唯一值
        String orderSn = IdUtil.getSnowflake(0, 0).nextIdStr();

        param.setOrderSn(orderSn);
        userRechargeService.addRecharge(param, uid);

        BigDecimal bigDecimal = new BigDecimal(100);
        int price = bigDecimal.multiply(BigDecimal.valueOf(param.getPrice())).intValue();
        try {
            if (param.getFrom().equals("weixinh5")) {
                WxPayMwebOrderResult result = payService.wxH5Pay(orderSn, "H5充值", price,
                        BillDetailEnum.TYPE_1.getValue());
                map.put("data", result.getMwebUrl());
            } else {
                WechatUser wechatUser = wechatUserService.getById(uid);
                if (ObjectUtil.isNull(wechatUser))
                    throw new ErrorRequestException("用户错误");
                WxPayMpOrderResult result = payService.wxPay(orderSn, wechatUser.getOpenid(), "公众号充值", price,
                        BillDetailEnum.TYPE_1.getValue());
                Map<String, String> jsConfig = new HashMap<>();
                jsConfig.put("appId", result.getAppId());
                jsConfig.put("timestamp", result.getTimeStamp());
                jsConfig.put("nonceStr", result.getNonceStr());
                jsConfig.put("package", result.getPackageValue());
                jsConfig.put("signType", result.getSignType());
                jsConfig.put("paySign", result.getPaySign());
                map.put("data", jsConfig);
            }
        } catch (WxPayException e) {
            return ApiResult.fail(e.getMessage());
        }

        return ApiResult.ok(map);
    }

}
