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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.Brand;
import co.lq.modules.shop.service.BrandService;
import co.lq.modules.shop.service.dto.BrandQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-03-27
 */
@Api(tags = "品牌包管理")
@RestController
@RequestMapping("/api/brand")
public class BrandController {

    private final BrandService storeBrandService;

    public BrandController(BrandService storeBrandService) {
        this.storeBrandService = storeBrandService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('storeBrand:list')")
    public void download(HttpServletResponse response, BrandQueryCriteria criteria) throws IOException {
        storeBrandService.download(storeBrandService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询品牌包")
    @ApiOperation("查询品牌包")
    @PreAuthorize("@el.check('storeBrand:list')")
    public ResponseEntity<Object> getStoreBrands(BrandQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(storeBrandService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "detail/{id}")
    @Log("查询品牌包")
    @ApiOperation("查询品牌包")
    @PreAuthorize("@el.check('storeBrand:list')")
    public ResponseEntity<Object> getStoreBrandInfo(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(storeBrandService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增品牌包")
    @ApiOperation("新增品牌包")
    @PreAuthorize("@el.check('storeBrand:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Brand resources) {
        return new ResponseEntity<>(storeBrandService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改品牌包")
    @ApiOperation("修改品牌包")
    @PreAuthorize("@el.check('storeBrand:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Brand resources) {
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
