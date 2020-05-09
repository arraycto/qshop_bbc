package co.lq.modules.shop.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.lq.aop.log.Log;
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.modules.shop.domain.Shop;
import co.lq.modules.shop.service.ShopService;
import co.lq.modules.shop.service.dto.ShopQueryCriteria;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-03-10
 */
@Api(tags = "店铺管理")
@RestController
@RequestMapping("/api/shop")
public class ShopController {

    private final ShopService        storeService;
    private final UserDetailsService userDetailsService;

    public ShopController(ShopService storeService, UserDetailsService userDetailsService) {
        this.storeService = storeService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    @Log("查询店铺")
    @ApiOperation("查询店铺")
    @PreAuthorize("@el.check('shop:list')")
    public ResponseEntity<Object> getQshopStores(ShopQueryCriteria criteria, Pageable pageable) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setId(jwtUser.getStoreId());
        return new ResponseEntity<>(storeService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增店铺")
    @ApiOperation("新增店铺")
    @PreAuthorize("@el.check('shop:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Shop resources) {
        return new ResponseEntity<>(storeService.create(resources), HttpStatus.CREATED);
    }

    @GetMapping(value = "/list")
    @Log("查询店铺")
    @ApiOperation("查询店铺")
    @PreAuthorize("@el.check('shop:list')")
    public ResponseEntity<Object> getShopList(ShopQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(storeService.queryAll(criteria, pageable), HttpStatus.OK);
    }
}
