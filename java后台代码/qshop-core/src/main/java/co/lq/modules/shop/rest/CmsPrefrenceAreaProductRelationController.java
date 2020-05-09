package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.CmsPrefrenceAreaProductRelation;
import co.lq.modules.shop.service.CmsPrefrenceAreaProductRelationService;
import co.lq.modules.shop.service.dto.CmsPrefrenceAreaProductRelationQueryCriteria;
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
* @date 2020-04-11
*/
@Api(tags = "优选专区和产品关系表管理")
@RestController
@RequestMapping("/api/cmsPrefrenceAreaProductRelation")
public class CmsPrefrenceAreaProductRelationController {

    private final CmsPrefrenceAreaProductRelationService cmsPrefrenceAreaProductRelationService;

    public CmsPrefrenceAreaProductRelationController(CmsPrefrenceAreaProductRelationService cmsPrefrenceAreaProductRelationService) {
        this.cmsPrefrenceAreaProductRelationService = cmsPrefrenceAreaProductRelationService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('cmsPrefrenceAreaProductRelation:list')")
    public void download(HttpServletResponse response, CmsPrefrenceAreaProductRelationQueryCriteria criteria) throws IOException {
        cmsPrefrenceAreaProductRelationService.download(cmsPrefrenceAreaProductRelationService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询优选专区和产品关系表")
    @ApiOperation("查询优选专区和产品关系表")
    @PreAuthorize("@el.check('cmsPrefrenceAreaProductRelation:list')")
    public ResponseEntity<Object> getCmsPrefrenceAreaProductRelations(CmsPrefrenceAreaProductRelationQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(cmsPrefrenceAreaProductRelationService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增优选专区和产品关系表")
    @ApiOperation("新增优选专区和产品关系表")
    @PreAuthorize("@el.check('cmsPrefrenceAreaProductRelation:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody CmsPrefrenceAreaProductRelation resources){
        return new ResponseEntity<>(cmsPrefrenceAreaProductRelationService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改优选专区和产品关系表")
    @ApiOperation("修改优选专区和产品关系表")
    @PreAuthorize("@el.check('cmsPrefrenceAreaProductRelation:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody CmsPrefrenceAreaProductRelation resources){
        cmsPrefrenceAreaProductRelationService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除优选专区和产品关系表")
    @ApiOperation("删除优选专区和产品关系表")
    @PreAuthorize("@el.check('cmsPrefrenceAreaProductRelation:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        cmsPrefrenceAreaProductRelationService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}