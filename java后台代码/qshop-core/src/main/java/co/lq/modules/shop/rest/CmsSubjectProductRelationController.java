package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.CmsSubjectProductRelation;
import co.lq.modules.shop.service.CmsSubjectProductRelationService;
import co.lq.modules.shop.service.dto.CmsSubjectProductRelationQueryCriteria;
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
@Api(tags = "专题商品关系表管理")
@RestController
@RequestMapping("/api/cmsSubjectProductRelation")
public class CmsSubjectProductRelationController {

    private final CmsSubjectProductRelationService cmsSubjectProductRelationService;

    public CmsSubjectProductRelationController(CmsSubjectProductRelationService cmsSubjectProductRelationService) {
        this.cmsSubjectProductRelationService = cmsSubjectProductRelationService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('cmsSubjectProductRelation:list')")
    public void download(HttpServletResponse response, CmsSubjectProductRelationQueryCriteria criteria) throws IOException {
        cmsSubjectProductRelationService.download(cmsSubjectProductRelationService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询专题商品关系表")
    @ApiOperation("查询专题商品关系表")
    @PreAuthorize("@el.check('cmsSubjectProductRelation:list')")
    public ResponseEntity<Object> getCmsSubjectProductRelations(CmsSubjectProductRelationQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(cmsSubjectProductRelationService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增专题商品关系表")
    @ApiOperation("新增专题商品关系表")
    @PreAuthorize("@el.check('cmsSubjectProductRelation:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody CmsSubjectProductRelation resources){
        return new ResponseEntity<>(cmsSubjectProductRelationService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改专题商品关系表")
    @ApiOperation("修改专题商品关系表")
    @PreAuthorize("@el.check('cmsSubjectProductRelation:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody CmsSubjectProductRelation resources){
        cmsSubjectProductRelationService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除专题商品关系表")
    @ApiOperation("删除专题商品关系表")
    @PreAuthorize("@el.check('cmsSubjectProductRelation:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        cmsSubjectProductRelationService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}