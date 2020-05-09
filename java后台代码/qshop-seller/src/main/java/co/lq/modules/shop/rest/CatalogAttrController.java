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
import co.lq.modules.shop.domain.CatalogAttr;
import co.lq.modules.shop.service.CatalogAttrService;
import co.lq.modules.shop.service.dto.CatalogAttrQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-03-10
 */
@Api(tags = "平台类目属性管理")
@RestController
@RequestMapping("/api/catalogAttr")
public class CatalogAttrController {

    private final CatalogAttrService catalogAttrService;

    public CatalogAttrController(CatalogAttrService catalogAttrService) {
        this.catalogAttrService = catalogAttrService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('catalogAttr:list')")
    public void download(HttpServletResponse response, CatalogAttrQueryCriteria criteria) throws IOException {
        catalogAttrService.download(catalogAttrService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询平台类目属性")
    @ApiOperation("查询平台类目属性")
    @PreAuthorize("@el.check('admin', 'catalogAttr:list')")
    public ResponseEntity<Object> getCatalogAttrs(CatalogAttrQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(catalogAttrService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增平台类目属性")
    @ApiOperation("新增平台类目属性")
    @PreAuthorize("@el.check('catalogAttr:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody CatalogAttr resources) {
        return new ResponseEntity<>(catalogAttrService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改平台类目属性")
    @ApiOperation("修改平台类目属性")
    @PreAuthorize("@el.check('catalogAttr:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody CatalogAttr resources) {
        catalogAttrService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除平台类目属性")
    @ApiOperation("删除平台类目属性")
    @PreAuthorize("@el.check('catalogAttr:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        catalogAttrService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
