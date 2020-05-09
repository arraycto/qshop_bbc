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
import co.lq.modules.shop.domain.StoreSettle;
import co.lq.modules.shop.service.StoreSettleService;
import co.lq.modules.shop.service.dto.StoreSettleQueryCriteria;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-03-28
 */
@Api(tags = "店铺入驻信息表管理")
@RestController
@RequestMapping("/api/storeSettle")
public class StoreSettleController {

    private final StoreSettleService storeSettleService;
    private final UserDetailsService userDetailsService;

    public StoreSettleController(StoreSettleService storeSettleService, UserDetailsService userDetailsService) {
        this.storeSettleService = storeSettleService;
        this.userDetailsService = userDetailsService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('storeSettle:list')")
    public void download(HttpServletResponse response, StoreSettleQueryCriteria criteria) throws IOException {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        storeSettleService.download(storeSettleService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询店铺入驻信息表")
    @ApiOperation("查询店铺入驻信息表")
    @PreAuthorize("@el.check('storeSettle:list')")
    public ResponseEntity<Object> getStoreSettles(StoreSettleQueryCriteria criteria, Pageable pageable) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(storeSettleService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增店铺入驻信息表")
    @ApiOperation("新增店铺入驻信息表")
    @PreAuthorize("@el.check('storeSettle:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody StoreSettle resources) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        resources.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(storeSettleService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改店铺入驻信息表")
    @ApiOperation("修改店铺入驻信息表")
    @PreAuthorize("@el.check('storeSettle:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody StoreSettle resources) {
        storeSettleService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除店铺入驻信息表")
    @ApiOperation("删除店铺入驻信息表")
    @PreAuthorize("@el.check('storeSettle:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        storeSettleService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
