package co.lq.modules.shop.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import co.lq.annotation.AnonymousAccess;
import co.lq.aop.log.Log;
import co.lq.common.api.ApiResult;
import co.lq.common.web.controller.BaseController;
import co.lq.modules.notify.NotifyService;
import co.lq.modules.notify.NotifyType;
import co.lq.modules.notify.SmsResult;
import co.lq.modules.security.rest.param.RegParam;
import co.lq.modules.security.rest.param.VerityParam;
import co.lq.modules.shop.entity.Store;
import co.lq.modules.shop.entity.SystemUser;
import co.lq.modules.shop.service.ApiStoreServie;
import co.lq.modules.shop.web.param.StoreQueryParam;
import co.lq.modules.shop.web.vo.StoreVo;
import co.lq.utils.RedisUtils;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 店铺
 *
 * @author songbin
 * @since 2020年3月12日 下午9:12:51
 */
@Slf4j
@RestController
@Api(value = "店铺", tags = "商城:店铺", description = "店铺")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StoreController extends BaseController {

    private final ApiStoreServie  apiStoreServie;

    private final RedisUtils      redisUtils;

    @Value("${qshop.notify.sms.enable}")
    private Boolean               enableSms;

    private final NotifyService   notifyService;

    private final PasswordEncoder passwordEncoder;

    /**
     * 店铺列表
     */
    @AnonymousAccess
    @GetMapping("/shopList")
    @ApiOperation(value = "店铺列表", notes = "店铺列表")
    public ApiResult getShopPageList(StoreQueryParam queryParam) {
        return ApiResult.ok(apiStoreServie.getStorePageList(queryParam));
    }

    /**
     * 查询店铺信息
     */
    @GetMapping("/getShopInfoByUid")
    @ApiOperation(value = "查询店铺信息", notes = "查询店铺信息")
    public ApiResult getShopInfoByUid() {
        Long uid = SecurityUtils.getUserId();
        StoreQueryParam storeQueryParam = new StoreQueryParam();
        storeQueryParam.setUid(uid);
        return ApiResult.ok(apiStoreServie.getShopInfoByUid(storeQueryParam));
    }

    /**
     * 查询店铺信息
     */
    @AnonymousAccess
    @GetMapping("/getShopInfoById")
    @ApiOperation(value = "查询店铺信息", notes = "查询店铺信息")
    public ApiResult getShopInfoById(@RequestParam(name = "id") Long id) {
        Long uid;
        try {
            uid = SecurityUtils.getUserId();
        } catch (Exception e) {
            uid = 0L;
        }
        return ApiResult.ok(apiStoreServie.getStoreHome(id, uid));
    }

    /**
     * 查询店铺信息
     */
    @PostMapping("/collectShop")
    @ApiOperation(value = "查询店铺信息", notes = "查询店铺信息")
    public ApiResult collectShop(@RequestBody JSONObject jsonObject) {
        Long uid = SecurityUtils.getUserId();
        return ApiResult
                .ok(apiStoreServie.collectStore(jsonObject.getLong("id"), uid, jsonObject.getInteger("isChecked")));
    }

    /**
     * 入驻存储店铺信息
     */
    @AnonymousAccess
    @Log(value = "入驻存储店铺信息", type = 1)
    @PostMapping("/saveShop")
    @ApiOperation(value = "入驻存储店铺信息", notes = "入驻存储店铺信息")
    public ApiResult saveShop(@RequestBody StoreVo storeVo) {
        //        Long uid = SecurityUtils.getUserId();
        //        storeVo.setUid(uid);
        Store store = apiStoreServie.saveShop(storeVo);
        //店铺入驻分类
        apiStoreServie.saveCatalogRelation(store.getId(), storeVo.getCatalogId());
        apiStoreServie.createAdminRole(storeVo);
        apiStoreServie.relationAdminRole(storeVo);

        return ApiResult.ok(storeVo);
    }

    @AnonymousAccess
    @PostMapping("/shopRegister/verify")
    @ApiOperation(value = "验证码发送", notes = "验证码发送")
    public ApiResult verify(@Validated @RequestBody VerityParam param) {
        SystemUser user = apiStoreServie.getSystemByUsername(param.getAccount());
        if (param.getType() == null) {
            param.setType("bind");
        }
        if ("register".equals(param.getType()) && ObjectUtil.isNotNull(user)) {
            return ApiResult.ok("帐号已注册");
        }
        String codeKey = "code_" + param.getPhone();
        if (ObjectUtil.isNotNull(redisUtils.get(codeKey))) {
            if (!enableSms) {
                return ApiResult.ok("10分钟内有效:" + redisUtils.get(codeKey).toString());
            }
            return ApiResult.ok("验证码10分钟内有效,请查看手机短信");

        }
        String code = RandomUtil.randomNumbers(6);

        redisUtils.set(codeKey, code, 600L);
        if (!enableSms) {
            return ApiResult.ok("测试阶段验证码:" + code);
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
    @PostMapping("/shopRegister")
    @ApiOperation(value = "店铺入驻注册新用户", notes = "H5注册新用户")
    public ApiResult register(@Validated @RequestBody RegParam param) {
        Object codeObj = redisUtils.get("code_" + param.getPhone());
        if (codeObj == null) {
            return ApiResult.fail("请先获取验证码");
        }
        String code = codeObj.toString();
        if (!StrUtil.equals(code, param.getCaptcha())) {
            return ApiResult.fail("验证码错误");
        }

        SystemUser user = apiStoreServie.getSystemByUsername(param.getAccount());
        if (ObjectUtil.isNotNull(user)) {
            return ApiResult.fail("用户已存在");
        }

        user = new SystemUser();
        user.setUsername(param.getAccount());
        user.setPassword(passwordEncoder.encode(param.getPassword()));
        user.setPhone(param.getAccount());
        user.setStoreId(0L);
        user = apiStoreServie.saveSystemUser(user);

        return ApiResult.ok(user);
    }

    /**
     * 新增入驻店铺类目以及资质
     */
    @Log(value = "新增入驻店铺类目以及资质", type = 1)
    @PostMapping("/saveShopCatalog")
    @ApiOperation(value = "新增入驻店铺类目以及资质", notes = "入驻存储店铺信息")
    public ApiResult saveShopCatalog(@RequestBody StoreVo storeVo) {
        //店铺入驻分类
        apiStoreServie.saveCatalogRelation(storeVo.getId(), storeVo.getCatalogId());
        Boolean result = true;

        return ApiResult.ok(result);
    }
}
