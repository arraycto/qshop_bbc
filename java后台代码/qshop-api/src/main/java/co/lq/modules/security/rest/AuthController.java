package co.lq.modules.security.rest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonResponse;
import com.vdurmont.emoji.EmojiParser;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import co.lq.annotation.AnonymousAccess;
import co.lq.aop.log.Log;
import co.lq.common.api.ApiResult;
import co.lq.constant.ShopConstants;
import co.lq.enums.AppFromEnum;
import co.lq.exception.ErrorRequestException;
import co.lq.modules.notify.NotifyService;
import co.lq.modules.notify.NotifyType;
import co.lq.modules.notify.SmsResult;
import co.lq.modules.security.config.SecurityProperties;
import co.lq.modules.security.rest.param.LoginParam;
import co.lq.modules.security.rest.param.RegParam;
import co.lq.modules.security.rest.param.VerityParam;
import co.lq.modules.security.security.TokenProvider;
import co.lq.modules.security.security.vo.AuthUser;
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.modules.security.service.OnlineUserService;
import co.lq.modules.user.entity.User;
import co.lq.modules.user.entity.WechatUser;
import co.lq.modules.user.service.UserService;
import co.lq.modules.user.service.WechatUserService;
import co.lq.mp.config.WxMpConfiguration;
import co.lq.utils.OrderUtil;
import co.lq.utils.RedisUtil;
import co.lq.utils.RedisUtils;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * @author billy
 * @date 2020/01/12
 */
@Slf4j
@RestController
@Api(tags = "授权:用户授权中心")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    @Value("${single.login:false}")
    private Boolean                            singleLogin;
    @Value("${qshop.notify.sms.enable}")
    private Boolean                            enableSms;

    private final SecurityProperties           properties;
    private final RedisUtils                   redisUtils;
    private final UserDetailsService           userDetailsService;
    private final OnlineUserService            onlineUserService;
    private final TokenProvider                tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService                  userService;
    private final PasswordEncoder              passwordEncoder;
    private final WechatUserService            wechatUserService;
    private final WxMaService                  wxMaService;
    private final NotifyService                notifyService;

    @Log("H5用户登录")
    @ApiOperation("H5登录授权")
    @AnonymousAccess
    @PostMapping(value = "/login")
    public ApiResult<Map<String, String>> login(@Validated @RequestBody AuthUser authUser, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authUser.getUsername(), authUser.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌
        String token = tokenProvider.createToken(authentication);
        final JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        // 保存在线信息
        onlineUserService.save(jwtUser, token, request);

        Date expiresTime = tokenProvider.getExpirationDateFromToken(token);
        String expiresTimeStr = DateUtil.formatDateTime(expiresTime);
        // 返回 token 与 用户信息
        Map<String, Object> authInfo = new HashMap<String, Object>(2) {
            {
                put("token", token);
                put("expires_time", expiresTimeStr);
            }
        };
        if (singleLogin) {
            //踢掉之前已经登录的token
            onlineUserService.checkLoginOnUser(authUser.getUsername(), token);
        }

        //设置推广关系
        if (StrUtil.isNotEmpty(authUser.getSpread()) && !authUser.getSpread().equals("NaN")) {
            userService.setSpread(Integer.valueOf(authUser.getSpread()), jwtUser.getId().intValue());
        }

        // 返回 token
        return ApiResult.ok(authInfo);
    }

    /**
     * 微信公众号授权
     */
    @AnonymousAccess
    @GetMapping("/wechat/auth")
    @ApiOperation(value = "微信公众号授权", notes = "微信公众号授权")
    public ApiResult<Object> authLogin(@RequestParam(value = "code") String code,
                                       @RequestParam(value = "spread") String spread, HttpServletRequest request) {

        try {
            WxMpService wxService = WxMpConfiguration.getWxMpService();
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxService.oauth2getAccessToken(code);
            WxMpUser wxMpUser = wxService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
            String openid = wxMpUser.getOpenId();

            User user = userService.findByName(openid);

            JwtUser jwtUser = null;
            if (ObjectUtil.isNotNull(user)) {
                jwtUser = (JwtUser) userDetailsService.loadUserByUsername(openid);
            } else {

                //如果后台删除了用户
                WechatUser wechatUser = wechatUserService.getUserInfo(openid);
                if (wechatUser != null) {
                    wechatUserService.removeById(wechatUser.getUid());
                }

                //过滤掉表情
                String nickname = EmojiParser.removeAllEmojis(wxMpUser.getNickname());
                log.info("昵称：{}", nickname);
                //用户保存
                user = new User();
                user.setAccount(nickname);
                user.setUsername(wxMpUser.getOpenId());
                user.setPassword(passwordEncoder.encode(ShopConstants.QSHOP_DEFAULT_PWD));
                user.setPwd(passwordEncoder.encode(ShopConstants.QSHOP_DEFAULT_PWD));
                user.setPhone("");
                user.setUserType(AppFromEnum.WECHAT.getValue());
                user.setAddTime(OrderUtil.getSecondTimestampTwo());
                user.setLastTime(OrderUtil.getSecondTimestampTwo());
                user.setNickname(nickname);
                user.setAvatar(wxMpUser.getHeadImgUrl());
                user.setNowMoney(BigDecimal.ZERO);
                user.setBrokeragePrice(BigDecimal.ZERO);
                user.setIntegral(BigDecimal.ZERO);

                userService.save(user);

                //保存微信用户
                wechatUser = new WechatUser();
                wechatUser.setAddTime(OrderUtil.getSecondTimestampTwo());
                wechatUser.setNickname(nickname);
                wechatUser.setOpenid(wxMpUser.getOpenId());
                int sub = 0;
                if (ObjectUtil.isNotNull(wxMpUser.getSubscribe()) && wxMpUser.getSubscribe())
                    sub = 1;
                wechatUser.setSubscribe(sub);
                wechatUser.setSex(wxMpUser.getSex());
                wechatUser.setLanguage(wxMpUser.getLanguage());
                wechatUser.setCity(wxMpUser.getCity());
                wechatUser.setProvince(wxMpUser.getProvince());
                wechatUser.setCountry(wxMpUser.getCountry());
                wechatUser.setHeadimgurl(wxMpUser.getHeadImgUrl());
                if (ObjectUtil.isNotNull(wxMpUser.getSubscribeTime())) {
                    wechatUser.setSubscribeTime(wxMpUser.getSubscribeTime().intValue());
                }
                if (StrUtil.isNotEmpty(wxMpUser.getUnionId())) {
                    wechatUser.setUnionid(wxMpUser.getUnionId());
                }
                if (StrUtil.isNotEmpty(wxMpUser.getRemark())) {
                    wechatUser.setUnionid(wxMpUser.getRemark());
                }
                if (ObjectUtil.isNotEmpty(wxMpUser.getGroupId())) {
                    wechatUser.setGroupid(wxMpUser.getGroupId());
                }
                wechatUser.setUid(user.getUid());

                wechatUserService.save(wechatUser);

                jwtUser = (JwtUser) userDetailsService.loadUserByUsername(wxMpUser.getOpenId());
            }

            //设置推广关系
            if (StrUtil.isNotEmpty(spread) && !spread.equals("NaN")) {
                userService.setSpread(Integer.valueOf(spread), jwtUser.getId().intValue());
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    jwtUser.getUsername(), ShopConstants.QSHOP_DEFAULT_PWD);

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // 生成令牌
            String token = tokenProvider.createToken(authentication);
            final JwtUser jwtUserT = (JwtUser) authentication.getPrincipal();
            // 保存在线信息
            onlineUserService.save(jwtUserT, token, request);

            Date expiresTime = tokenProvider.getExpirationDateFromToken(token);
            String expiresTimeStr = DateUtil.formatDateTime(expiresTime);

            Map<String, String> map = new LinkedHashMap<>();
            map.put("token", token);
            map.put("expires_time", expiresTimeStr);

            // 返回 token
            return ApiResult.ok(map);
        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ApiResult.fail("授权失败");
        }

    }

    /**
     * 小程序登陆接口
     */
    @AnonymousAccess
    @PostMapping("/wxapp/auth")
    @ApiOperation(value = "小程序登陆", notes = "小程序登陆")
    public ApiResult<Object> login(@Validated @RequestBody LoginParam loginParam, HttpServletRequest request) {
        String code = loginParam.getCode();
        String encryptedData = loginParam.getEncryptedData();
        String iv = loginParam.getIv();
        String spread = loginParam.getSpread();
        try {
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
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
            String openid = session.getOpenid();

            User user = userService.findByName(openid);
            JwtUser jwtUser = null;
            if (ObjectUtil.isNotNull(user)) {
                jwtUser = (JwtUser) userDetailsService.loadUserByUsername(openid);
            } else {

                //如果后台删除了用户
                WechatUser wechatUser = wechatUserService.getUserAppInfo(openid);
                if (wechatUser != null) {
                    wechatUserService.removeById(wechatUser.getUid());
                }

                WxMaUserInfo wxMpUser = wxMaService.getUserService().getUserInfo(session.getSessionKey(), encryptedData,
                        iv);
                //过滤掉表情
                String nickname = EmojiParser.removeAllEmojis(wxMpUser.getNickName());
                //用户保存
                user = new User();
                user.setAccount(nickname);
                user.setUsername(wxMpUser.getOpenId());
                user.setPassword(passwordEncoder.encode(ShopConstants.QSHOP_DEFAULT_PWD));
                user.setPwd(passwordEncoder.encode(ShopConstants.QSHOP_DEFAULT_PWD));
                user.setPhone("");
                user.setUserType(AppFromEnum.ROUNTINE.getValue());
                user.setAddTime(OrderUtil.getSecondTimestampTwo());
                user.setLastTime(OrderUtil.getSecondTimestampTwo());
                user.setNickname(nickname);
                user.setAvatar(wxMpUser.getAvatarUrl());
                user.setNowMoney(BigDecimal.ZERO);
                user.setBrokeragePrice(BigDecimal.ZERO);
                user.setIntegral(BigDecimal.ZERO);

                userService.save(user);

                //保存微信用户
                wechatUser = new WechatUser();
                // System.out.println("wxMpUser:"+wxMpUser);
                wechatUser.setAddTime(OrderUtil.getSecondTimestampTwo());
                wechatUser.setNickname(nickname);
                wechatUser.setRoutineOpenid(wxMpUser.getOpenId());
                int sub = 0;
                wechatUser.setSubscribe(sub);
                wechatUser.setSex(Integer.valueOf(wxMpUser.getGender()));
                wechatUser.setLanguage(wxMpUser.getLanguage());
                wechatUser.setCity(wxMpUser.getCity());
                wechatUser.setProvince(wxMpUser.getProvince());
                wechatUser.setCountry(wxMpUser.getCountry());
                wechatUser.setHeadimgurl(wxMpUser.getAvatarUrl());
                if (StrUtil.isNotEmpty(wxMpUser.getUnionId())) {
                    wechatUser.setUnionid(wxMpUser.getUnionId());
                }
                wechatUser.setUid(user.getUid());

                wechatUserService.save(wechatUser);

                jwtUser = (JwtUser) userDetailsService.loadUserByUsername(wxMpUser.getOpenId());
            }

            //设置推广关系
            if (StrUtil.isNotEmpty(spread)) {
                userService.setSpread(Integer.valueOf(spread), jwtUser.getId().intValue());
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    jwtUser.getUsername(), ShopConstants.QSHOP_DEFAULT_PWD);

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // 生成令牌
            String token = tokenProvider.createToken(authentication);
            final JwtUser jwtUserT = (JwtUser) authentication.getPrincipal();
            // 保存在线信息
            onlineUserService.save(jwtUserT, token, request);

            Date expiresTime = tokenProvider.getExpirationDateFromToken(token);
            String expiresTimeStr = DateUtil.formatDateTime(expiresTime);

            Map<String, String> map = new LinkedHashMap<>();
            map.put("token", token);
            map.put("expires_time", expiresTimeStr);

            // 返回 token
            return ApiResult.ok(map);
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return ApiResult.fail(e.toString());
        }
    }

    @AnonymousAccess
    @PostMapping("/register/verify")
    @ApiOperation(value = "验证码发送", notes = "验证码发送")
    public ApiResult<String> verify(@Validated @RequestBody VerityParam param) {
        Boolean isTest = true;
        User user = userService.findByName(param.getPhone());
        if (param.getType() == null)
            param.setType("bind");
        if (param.getType().equals("register") && ObjectUtil.isNotNull(user)) {
            return ApiResult.fail("手机号已注册");
        }
        if (param.getType().equals("login") && ObjectUtil.isNull(user)) {
            return ApiResult.fail("账号不存在");
        }
        String codeKey = "code_" + param.getPhone();
        if (ObjectUtil.isNotNull(redisUtils.get(codeKey))) {
            if (!enableSms) {
                return ApiResult.fail("10分钟内有效:" + redisUtils.get(codeKey).toString());
            }
            return ApiResult.fail("验证码10分钟内有效,请查看手机短信");

        }
        String code = RandomUtil.randomNumbers(6);

        redisUtils.set(codeKey, code, 600L);
        if (!enableSms) {
            return ApiResult.fail("测试阶段验证码:" + code);
        }
        //发送阿里云短信
        SmsResult smsResult = notifyService.notifySmsTemplateSync(param.getPhone(), NotifyType.CAPTCHA,
                new String[] { code });
        CommonResponse commonResponse = (CommonResponse) smsResult.getResult();
        if (smsResult.isSuccessful()) {
            log.info("详情：{}", commonResponse.getData());
            return ApiResult.ok("发送成功，请注意查收");
        } else {
            JSONObject jsonObject = JSON.parseObject(commonResponse.getData());
            log.info("错误详情：{}", commonResponse.getData());
            //删除redis存储
            redisUtils.del(codeKey);
            return ApiResult.ok("发送失败：" + jsonObject.getString("Message"));
        }

    }

    @AnonymousAccess
    @PostMapping("/register")
    @ApiOperation(value = "H5注册新用户", notes = "H5注册新用户")
    public ApiResult<String> register(@Validated @RequestBody RegParam param) {

        Object codeObj = redisUtils.get("code_" + param.getAccount());
        if (codeObj == null) {
            return ApiResult.fail("请先获取验证码");
        }
        String code = codeObj.toString();

        if (!StrUtil.equals(code, param.getCaptcha())) {
            return ApiResult.fail("验证码错误");
        }

        User user = userService.findByName(param.getAccount());
        if (ObjectUtil.isNotNull(user)) {
            return ApiResult.fail("用户已存在");
        }

        user = new User();
        user.setAccount(param.getAccount());
        user.setUsername(param.getAccount());
        user.setPassword(passwordEncoder.encode(param.getPassword()));
        user.setPwd(passwordEncoder.encode(param.getPassword()));
        user.setPhone(param.getAccount());
        user.setUserType(AppFromEnum.H5.getValue());
        user.setAddTime(OrderUtil.getSecondTimestampTwo());
        user.setLastTime(OrderUtil.getSecondTimestampTwo());
        user.setNickname(param.getAccount());
        user.setAvatar(ShopConstants.QSHOP_DEFAULT_AVATAR);
        user.setNowMoney(BigDecimal.ZERO);
        user.setBrokeragePrice(BigDecimal.ZERO);
        user.setIntegral(BigDecimal.ZERO);

        userService.save(user);

        return ApiResult.ok("注册成功");
    }

    @AnonymousAccess
    @PostMapping("/register/reset")
    @ApiOperation(value = "密码重置", notes = "密码重置")
    public ApiResult resetPassword(@Validated @RequestBody RegParam param) {

        Object codeObj = redisUtils.get("code_" + param.getAccount());
        if (codeObj == null) {
            return ApiResult.fail("请先获取验证码");
        }
        String code = codeObj.toString();

        if (!StrUtil.equals(code, param.getCaptcha())) {
            return ApiResult.fail("验证码错误");
        }

        User user = userService.findByName(param.getAccount());
        if (!ObjectUtil.isNotNull(user)) {
            return ApiResult.fail("用户不存在");
        }

        String pwd = passwordEncoder.encode(param.getPassword());
        user.setPassword(pwd);
        user.setPwd(pwd);
        userService.updateById(user);

        return ApiResult.ok("重置成功");
    }

    @ApiOperation("获取用户信息")
    @GetMapping(value = "/info")
    public ApiResult<Object> getUserInfo() {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        return ApiResult.ok(jwtUser);
    }

    @ApiOperation(value = "退出登录", notes = "退出登录")
    @AnonymousAccess
    @PostMapping(value = "/auth/logout")
    public ApiResult<Object> logout(HttpServletRequest request) {
        onlineUserService.logout(tokenProvider.getToken(request));
        return ApiResult.ok("退出成功");
    }
}
