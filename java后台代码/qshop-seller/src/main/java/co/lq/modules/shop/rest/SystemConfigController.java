package co.lq.modules.shop.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.util.ObjectUtil;
import co.lq.aop.log.Log;
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.modules.shop.domain.StoreSystemConfig;
import co.lq.modules.shop.service.StoreSystemConfigService;
import co.lq.modules.shop.service.dto.StoreSystemConfigQueryCriteria;
import co.lq.utils.RedisUtil;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-03-11
 */
@Api(tags = "店铺配置管理")
@RestController
@RequestMapping("/api/storeSystemConfig")
public class SystemConfigController {

    private final StoreSystemConfigService storeSystemConfigService;
    private final UserDetailsService       userDetailsService;

    public SystemConfigController(StoreSystemConfigService storeSystemConfigService,
                                  UserDetailsService userDetailsService) {
        this.storeSystemConfigService = storeSystemConfigService;
        this.userDetailsService = userDetailsService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('storeSystemConfig:list')")
    public void download(HttpServletResponse response, StoreSystemConfigQueryCriteria criteria) throws IOException {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        storeSystemConfigService.download(storeSystemConfigService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询店铺配置")
    @ApiOperation("查询店铺配置")
    @PreAuthorize("@el.check('storeSystemConfig:list')")
    public ResponseEntity<Object> getStoreSystemConfigs(StoreSystemConfigQueryCriteria criteria, Pageable pageable) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(storeSystemConfigService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增店铺配置")
    @ApiOperation("新增店铺配置")
    @PreAuthorize("@el.check('storeSystemConfig:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody String jsonStr) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        jsonObject.forEach((key, value) -> {
            StoreSystemConfig systemConfig = storeSystemConfigService.findByKey(key);
            StoreSystemConfig systemConfigModel = new StoreSystemConfig();
            systemConfigModel.setMenuName(key);
            systemConfigModel.setValue(value.toString());
            systemConfigModel.setStoreId(jwtUser.getStoreId());
            RedisUtil.set(key, value.toString(), 0);
            if (ObjectUtil.isNull(systemConfig)) {
                storeSystemConfigService.create(systemConfigModel);
            } else {
                systemConfigModel.setId(systemConfig.getId());
                storeSystemConfigService.update(systemConfigModel);
            }
        });
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改店铺配置")
    @ApiOperation("修改店铺配置")
    @PreAuthorize("@el.check('storeSystemConfig:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody StoreSystemConfig resources) {
        storeSystemConfigService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除店铺配置")
    @ApiOperation("删除店铺配置")
    @PreAuthorize("@el.check('storeSystemConfig:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        storeSystemConfigService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
