package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.CmsSubjectCategory;
import co.lq.modules.shop.service.CmsSubjectCategoryService;
import co.lq.modules.shop.service.dto.CmsSubjectCategoryQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author billy
* @date 2020-03-27
*/
@Api(tags = "专题分类表管理")
@RestController
@RequestMapping("/api/cmsSubjectCategory")
public class CmsSubjectCategoryController {

    private final CmsSubjectCategoryService cmsSubjectCategoryService;

    public CmsSubjectCategoryController(CmsSubjectCategoryService cmsSubjectCategoryService) {
        this.cmsSubjectCategoryService = cmsSubjectCategoryService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('cmsSubjectCategory:list')")
    public void download(HttpServletResponse response, CmsSubjectCategoryQueryCriteria criteria) throws IOException {
        cmsSubjectCategoryService.download(cmsSubjectCategoryService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询专题分类表")
    @ApiOperation("查询专题分类表")
    @PreAuthorize("@el.check('cmsSubjectCategory:list')")
    public ResponseEntity<Object> getCmsSubjectCategorys(CmsSubjectCategoryQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(cmsSubjectCategoryService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增专题分类表")
    @ApiOperation("新增专题分类表")
    @PreAuthorize("@el.check('cmsSubjectCategory:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody CmsSubjectCategory resources){
        return new ResponseEntity<>(cmsSubjectCategoryService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改专题分类表")
    @ApiOperation("修改专题分类表")
    @PreAuthorize("@el.check('cmsSubjectCategory:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody CmsSubjectCategory resources){
        cmsSubjectCategoryService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除专题分类表")
    @ApiOperation("删除专题分类表")
    @PreAuthorize("@el.check('cmsSubjectCategory:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        cmsSubjectCategoryService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}