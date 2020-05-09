package co.lq.modules.wechat.web.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import cn.hutool.core.util.StrUtil;
import co.lq.common.api.ApiResult;
import co.lq.exception.ErrorRequestException;
import co.lq.modules.user.entity.User;
import co.lq.modules.user.service.UserService;
import co.lq.modules.user.service.WechatUserService;
import co.lq.modules.user.web.vo.UserQueryVo;
import co.lq.modules.wechat.web.param.BindPhoneParam;
import co.lq.modules.wechat.web.param.WxPhoneParam;
import co.lq.utils.RedisUtil;
import co.lq.utils.RedisUtils;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * @author billy
 * @date 2020/02/07
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "微信其他", tags = "微信:微信其他", description = "微信其他")
public class WxMaUserController {

    private final WxMaService       wxMaService;
    private final WechatUserService wechatUserService;
    private final UserService       userService;
    private final RedisUtils        redisUtils;

    @PostMapping("/binding")
    @ApiOperation(value = "公众号绑定手机号", notes = "公众号绑定手机号")
    public ApiResult<String> verify(@Validated @RequestBody BindPhoneParam param) {

        Object codeObj = redisUtils.get("code_" + param.getPhone());
        if (codeObj == null) {
            return ApiResult.fail("请先获取验证码");
        }
        String code = codeObj.toString();

        if (!StrUtil.equals(code, param.getCaptcha())) {
            return ApiResult.fail("验证码错误");
        }

        Long uid = SecurityUtils.getUserId();
        UserQueryVo userQueryVo = userService.getUserById(uid);
        if (StrUtil.isNotBlank(userQueryVo.getPhone())) {
            return ApiResult.fail("您的账号已经绑定过手机号码");
        }

        User user = new User();
        user.setPhone(param.getPhone());
        user.setUid(uid);
        userService.updateById(user);

        return ApiResult.ok("绑定成功");

    }

    @PostMapping("/wxapp/binding")
    @ApiOperation(value = "小程序绑定手机号", notes = "小程序绑定手机号")
    public ApiResult<String> phone(@Validated @RequestBody WxPhoneParam param) {

        Long uid = SecurityUtils.getUserId();
        UserQueryVo userQueryVo = userService.getUserById(uid);
        if (StrUtil.isNotBlank(userQueryVo.getPhone())) {
            return ApiResult.fail("您的账号已经绑定过手机号码");
        }

        //读取redis配置
        String appId = RedisUtil.get("wxapp_appId");
        String secret = RedisUtil.get("wxapp_secret");
        if (StrUtil.isBlank(appId) || StrUtil.isBlank(secret)) {
            throw new ErrorRequestException("请先配置小程序");
        }
        WxMaDefaultConfigImpl wxMaConfig = new WxMaDefaultConfigImpl();
        wxMaConfig.setAppid(appId);
        wxMaConfig.setSecret(secret);
        wxMaService.setWxMaConfig(wxMaConfig);
        String phone = "";
        try {
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(param.getCode());

            // 解密
            WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService().getPhoneNoInfo(session.getSessionKey(),
                    param.getEncryptedData(), param.getIv());

            phone = phoneNoInfo.getPhoneNumber();
            User user = new User();
            user.setPhone(phone);
            user.setUid(uid);
            userService.updateById(user);
        } catch (WxErrorException e) {
            return ApiResult.fail(e.getMessage());
            //e.printStackTrace();
        }
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("phone", phone);

        return ApiResult.ok(map, "绑定成功");
    }

}
