package co.lq.modules.shop.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.Shop;
import co.lq.modules.shop.service.ShopService;
import co.lq.modules.shop.service.dto.ShopQueryCriteria;
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

    private final ShopService storeService;

    public ShopController(ShopService storeService) {
        this.storeService = storeService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('shop:list')")
    public void download(HttpServletResponse response, ShopQueryCriteria criteria) throws IOException {
        storeService.download(storeService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询店铺")
    @ApiOperation("查询店铺")
    @PreAuthorize("@el.check('shop:list')")
    public ResponseEntity<Object> getQshopStores(ShopQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(storeService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增店铺")
    @ApiOperation("新增店铺")
    @PreAuthorize("@el.check('shop:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Shop resources) {
        return new ResponseEntity<>(storeService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改店铺")
    @ApiOperation("修改店铺")
    @PreAuthorize("@el.check('shop:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Shop resources) {
        storeService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除店铺")
    @ApiOperation("删除店铺")
    @PreAuthorize("@el.check('shop:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        storeService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
