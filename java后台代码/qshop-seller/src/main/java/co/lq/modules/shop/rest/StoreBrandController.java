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
import co.lq.modules.shop.domain.Shop;
import co.lq.modules.shop.domain.StoreBrand;
import co.lq.modules.shop.service.ShopService;
import co.lq.modules.shop.service.StoreBrandService;
import co.lq.modules.shop.service.dto.StoreBrandQueryCriteria;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-03-27
 */
@Api(tags = "品牌包管理")
@RestController
@RequestMapping("/api/storeBrand")
public class StoreBrandController {

    private final StoreBrandService  storeBrandService;
    private final UserDetailsService userDetailsService;
    private final ShopService        shopService;

    public StoreBrandController(StoreBrandService storeBrandService, UserDetailsService userDetailsService,
                                ShopService shopService) {
        this.storeBrandService = storeBrandService;
        this.userDetailsService = userDetailsService;
        this.shopService = shopService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('storeBrand:list')")
    public void download(HttpServletResponse response, StoreBrandQueryCriteria criteria) throws IOException {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        storeBrandService.download(storeBrandService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询品牌包")
    @ApiOperation("查询品牌包")
    @PreAuthorize("@el.check('storeBrand:list')")
    public ResponseEntity<Object> getStoreBrands(StoreBrandQueryCriteria criteria, Pageable pageable) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(storeBrandService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增品牌包")
    @ApiOperation("新增品牌包")
    @PreAuthorize("@el.check('storeBrand:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody StoreBrand resources) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        Shop shop = shopService.findById(jwtUser.getStoreId());
        resources.setShop(shop);
        return new ResponseEntity<>(storeBrandService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改品牌包")
    @ApiOperation("修改品牌包")
    @PreAuthorize("@el.check('storeBrand:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody StoreBrand resources) {
        storeBrandService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除品牌包")
    @ApiOperation("删除品牌包")
    @PreAuthorize("@el.check('storeBrand:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        storeBrandService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
