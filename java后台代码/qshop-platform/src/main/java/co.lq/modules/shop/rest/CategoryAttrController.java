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
import co.lq.modules.shop.domain.CategoryAttr;
import co.lq.modules.shop.service.CategoryAttrService;
import co.lq.modules.shop.service.dto.CategoryAttrQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-03-17
 */
@Api(tags = "平台分类属性管理")
@RestController
@RequestMapping("/api/categoryAttr")
public class CategoryAttrController {

    private final CategoryAttrService categoryAttrService;

    public CategoryAttrController(CategoryAttrService categoryAttrService) {
        this.categoryAttrService = categoryAttrService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('categoryAttr:list')")
    public void download(HttpServletResponse response, CategoryAttrQueryCriteria criteria) throws IOException {
        categoryAttrService.download(categoryAttrService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询平台分类属性")
    @ApiOperation("查询平台分类属性")
    @PreAuthorize("@el.check('categoryAttr:list')")
    public ResponseEntity<Object> getCategoryAttrs(CategoryAttrQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(categoryAttrService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增平台分类属性")
    @ApiOperation("新增平台分类属性")
    @PreAuthorize("@el.check('categoryAttr:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody CategoryAttr resources) {
        return new ResponseEntity<>(categoryAttrService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改平台分类属性")
    @ApiOperation("修改平台分类属性")
    @PreAuthorize("@el.check('categoryAttr:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody CategoryAttr resources) {
        categoryAttrService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除平台分类属性")
    @ApiOperation("删除平台分类属性")
    @PreAuthorize("@el.check('categoryAttr:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        categoryAttrService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
