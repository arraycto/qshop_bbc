package co.lq.modules.shop.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.util.ObjectUtil;
import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.SystemConfig;
import co.lq.modules.shop.service.SystemConfigService;
import co.lq.modules.shop.service.dto.SystemConfigQueryCriteria;
import co.lq.mp.config.WxMpConfiguration;
import co.lq.mp.config.WxPayConfiguration;
import co.lq.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2019-10-10
 */
@Api(tags = "商城:配置管理")
@RestController
@RequestMapping("api")
public class SystemConfigController {

    private final SystemConfigService systemConfigService;

    public SystemConfigController(SystemConfigService systemConfigService) {
        this.systemConfigService = systemConfigService;
    }

    @Log("查询")
    @ApiOperation(value = "查询")
    @GetMapping(value = "/systemConfig")
    @PreAuthorize("@el.check('admin','SYSTEMCONFIG_ALL','SYSTEMCONFIG_SELECT')")
    public ResponseEntity getSystemConfigs(SystemConfigQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(systemConfigService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("新增或修改")
    @ApiOperation(value = "新增或修改")
    @PostMapping(value = "/systemConfig")
    @PreAuthorize("@el.check('admin','SYSTEMCONFIG_ALL','SYSTEMCONFIG_CREATE')")
    public ResponseEntity create(@RequestBody String jsonStr) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        jsonObject.forEach((key, value) -> {
            SystemConfig systemConfig = systemConfigService.findByKey(key);
            SystemConfig systemConfigModel = new SystemConfig();
            systemConfigModel.setMenuName(key);
            systemConfigModel.setValue(value.toString());
            //重新配置微信相关
            if (key.equals("wechat_appid")) {
                WxMpConfiguration.removeWxMpService();
                WxPayConfiguration.removeWxPayService();
            }
            if (key.equals("wxpay_mchId") || key.equals("wxpay_appId")) {
                WxPayConfiguration.removeWxPayService();
            }
            RedisUtil.set(key, value.toString(), 0);
            if (ObjectUtil.isNull(systemConfig)) {
                systemConfigService.create(systemConfigModel);
            } else {
                systemConfigModel.setId(systemConfig.getId());
                systemConfigService.update(systemConfigModel);
            }
        });

        return new ResponseEntity(HttpStatus.CREATED);
    }

}
