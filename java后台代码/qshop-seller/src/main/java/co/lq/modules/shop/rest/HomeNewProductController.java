package co.lq.modules.shop.rest;

import java.io.IOException;
import java.util.List;

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
import co.lq.modules.shop.domain.HomeNewProduct;
import co.lq.modules.shop.service.HomeNewProductService;
import co.lq.modules.shop.service.dto.HomeNewProductQueryCriteria;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-03-27
 */
@Api(tags = "首页推荐商品表管理")
@RestController
@RequestMapping("/api/homeNewProduct")
public class HomeNewProductController {

    private final HomeNewProductService homeNewProductService;
    private final UserDetailsService    userDetailsService;

    public HomeNewProductController(HomeNewProductService homeNewProductService,
                                    UserDetailsService userDetailsService) {
        this.homeNewProductService = homeNewProductService;
        this.userDetailsService = userDetailsService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('homeNewProduct:list')")
    public void download(HttpServletResponse response, HomeNewProductQueryCriteria criteria) throws IOException {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        homeNewProductService.download(homeNewProductService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询首页推荐商品表")
    @ApiOperation("查询首页推荐商品表")
    @PreAuthorize("@el.check('homeNewProduct:list')")
    public ResponseEntity<Object> getHomeNewProducts(HomeNewProductQueryCriteria criteria, Pageable pageable) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(homeNewProductService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增首页推荐商品表")
    @ApiOperation("新增首页推荐商品表")
    @PreAuthorize("@el.check('homeNewProduct:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody List<HomeNewProduct> resources) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        resources.forEach(homeNewProduct -> homeNewProduct.setStoreId(jwtUser.getStoreId()));
        return new ResponseEntity<>(homeNewProductService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改首页推荐商品表")
    @ApiOperation("修改首页推荐商品表")
    @PreAuthorize("@el.check('homeNewProduct:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody HomeNewProduct resources) {
        homeNewProductService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除首页推荐商品表")
    @ApiOperation("删除首页推荐商品表")
    @PreAuthorize("@el.check('homeNewProduct:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        homeNewProductService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
