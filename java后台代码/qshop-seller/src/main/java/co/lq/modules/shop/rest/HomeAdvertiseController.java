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

import co.lq.aop.log.Log;
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.modules.shop.domain.HomeAdvertise;
import co.lq.modules.shop.service.HomeAdvertiseService;
import co.lq.modules.shop.service.dto.HomeAdvertiseQueryCriteria;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-03-13
 */
@Api(tags = "广告表管理")
@RestController
@RequestMapping("/api/homeAdvertise")
public class HomeAdvertiseController {

    private final HomeAdvertiseService homeAdvertiseService;
    private final UserDetailsService   userDetailsService;

    public HomeAdvertiseController(HomeAdvertiseService homeAdvertiseService, UserDetailsService userDetailsService) {
        this.homeAdvertiseService = homeAdvertiseService;
        this.userDetailsService = userDetailsService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('homeAdvertise:list')")
    public void download(HttpServletResponse response, HomeAdvertiseQueryCriteria criteria) throws IOException {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        homeAdvertiseService.download(homeAdvertiseService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询广告表")
    @ApiOperation("查询广告表")
    @PreAuthorize("@el.check('homeAdvertise:list')")
    public ResponseEntity<Object> getHomeAdvertises(HomeAdvertiseQueryCriteria criteria, Pageable pageable) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(homeAdvertiseService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增广告表")
    @ApiOperation("新增广告表")
    @PreAuthorize("@el.check('homeAdvertise:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody HomeAdvertise resources) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        resources.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(homeAdvertiseService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改广告表")
    @ApiOperation("修改广告表")
    @PreAuthorize("@el.check('homeAdvertise:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody HomeAdvertise resources) {
        homeAdvertiseService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除广告表")
    @ApiOperation("删除广告表")
    @PreAuthorize("@el.check('homeAdvertise:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        homeAdvertiseService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
