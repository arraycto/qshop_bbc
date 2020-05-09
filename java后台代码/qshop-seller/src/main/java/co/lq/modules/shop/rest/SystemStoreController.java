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

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import co.lq.aop.log.Log;
import co.lq.constant.ShopConstants;
import co.lq.exception.BadRequestException;
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.modules.shop.domain.SystemStore;
import co.lq.modules.shop.service.SystemStoreService;
import co.lq.modules.shop.service.dto.SystemStoreQueryCriteria;
import co.lq.utils.OrderUtil;
import co.lq.utils.RedisUtil;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-03-03
 */
@Api(tags = "门店管理")
@RestController
@RequestMapping("/api/systemStore")
public class SystemStoreController {

    private final SystemStoreService systemStoreService;
    private final UserDetailsService userDetailsService;

    public SystemStoreController(SystemStoreService systemStoreService, UserDetailsService userDetailsService) {
        this.systemStoreService = systemStoreService;
        this.userDetailsService = userDetailsService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('store:list')")
    public void download(HttpServletResponse response, SystemStoreQueryCriteria criteria) throws IOException {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        systemStoreService.download(systemStoreService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询门店")
    @ApiOperation("查询门店")
    @PreAuthorize("@el.check('store:list')")
    public ResponseEntity<Object> getSystemStores(SystemStoreQueryCriteria criteria, Pageable pageable) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(systemStoreService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping(value = "/getL")
    @Log("获取经纬度")
    @ApiOperation("获取经纬度")
    @PreAuthorize("@el.check('store:getl')")
    public ResponseEntity<Object> create(@Validated @RequestBody String jsonStr) {
        String key = RedisUtil.get("tengxun_map_key");
        if (StrUtil.isBlank(key)) {
            throw new BadRequestException("请先配置腾讯地图key");
        }
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        String addr = jsonObject.getString("addr");
        String url = StrUtil.format("?address={}&key={}", addr, key);
        String json = HttpUtil.get(ShopConstants.QQ_MAP_URL + url);
        return new ResponseEntity<>(json, HttpStatus.CREATED);
    }

    @PutMapping
    @Log("设置门店信息")
    @ApiOperation("设置门店信息")
    @PreAuthorize("@el.check('store:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody SystemStore resources) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        resources.setStoreId(jwtUser.getStoreId());
        if (resources.getId() == null) {
            resources.setAddTime(OrderUtil.getSecondTimestampTwo());
            systemStoreService.create(resources);
        } else {
            systemStoreService.update(resources);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除门店")
    @ApiOperation("删除门店")
    @PreAuthorize("@el.check('store:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        systemStoreService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
