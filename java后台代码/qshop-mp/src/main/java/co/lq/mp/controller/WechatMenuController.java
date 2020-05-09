package co.lq.mp.controller;

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

import co.lq.exception.BadRequestException;
import co.lq.mp.config.WxMpConfiguration;
import co.lq.mp.domain.Cache;
import co.lq.mp.service.CacheService;
import co.lq.utils.OrderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;

/**
 * @author billy
 * @date 2019-10-06
 */
@Api(tags = "商城:微信菜單")
@RestController
@RequestMapping("api")
public class WechatMenuController {

    private final CacheService cacheService;

    public WechatMenuController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @ApiOperation(value = "查询菜单")
    @GetMapping(value = "/cache")
    @PreAuthorize("@el.check('admin','CACHE_ALL','CACHE_SELECT')")
    public ResponseEntity getCaches() {
        return new ResponseEntity(cacheService.findById("wechat_menus"), HttpStatus.OK);
    }

    @ApiOperation(value = "创建菜单")
    @PostMapping(value = "/cache")
    @PreAuthorize("@el.check('admin','CACHE_ALL','CACHE_CREATE')")
    public ResponseEntity create(@RequestBody String jsonStr) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        String jsonButton = jsonObject.get("buttons").toString();
        Cache cache = new Cache();
        Boolean isExist = cacheService.isExist("wechat_menus");
        WxMenu menu = JSONObject.parseObject(jsonStr, WxMenu.class);

        WxMpService wxService = WxMpConfiguration.getWxMpService();
        if (isExist) {
            cache.setKey("wechat_menus");
            cache.setResult(jsonButton);
            cacheService.update(cache);
        } else {
            cache.setKey("wechat_menus");
            cache.setResult(jsonButton);
            cache.setAddTime(OrderUtil.getSecondTimestampTwo());
            cacheService.create(cache);
        }

        //创建菜单
        try {
            wxService.getMenuService().menuDelete();
            wxService.getMenuService().menuCreate(menu);
        } catch (WxErrorException e) {
            throw new BadRequestException(e.getMessage());
            // e.printStackTrace();
        }

        return new ResponseEntity(HttpStatus.OK);
    }

}
