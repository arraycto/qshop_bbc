package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.StoreCatalogRelation;
import co.lq.modules.shop.service.StoreCatalogRelationService;
import co.lq.modules.shop.service.dto.StoreCatalogRelationQueryCriteria;
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
* @date 2020-04-23
*/
@Api(tags = "店铺类目关联表管理")
@RestController
@RequestMapping("/api/storeCatalogRelation")
public class StoreCatalogRelationController {

    private final StoreCatalogRelationService storeCatalogRelationService;

    public StoreCatalogRelationController(StoreCatalogRelationService storeCatalogRelationService) {
        this.storeCatalogRelationService = storeCatalogRelationService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('storeCatalogRelation:list')")
    public void download(HttpServletResponse response, StoreCatalogRelationQueryCriteria criteria) throws IOException {
        storeCatalogRelationService.download(storeCatalogRelationService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询店铺类目关联表")
    @ApiOperation("查询店铺类目关联表")
    @PreAuthorize("@el.check('storeCatalogRelation:list')")
    public ResponseEntity<Object> getStoreCatalogRelations(StoreCatalogRelationQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(storeCatalogRelationService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增店铺类目关联表")
    @ApiOperation("新增店铺类目关联表")
    @PreAuthorize("@el.check('storeCatalogRelation:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody StoreCatalogRelation resources){
        return new ResponseEntity<>(storeCatalogRelationService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改店铺类目关联表")
    @ApiOperation("修改店铺类目关联表")
    @PreAuthorize("@el.check('storeCatalogRelation:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody StoreCatalogRelation resources){
        storeCatalogRelationService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除店铺类目关联表")
    @ApiOperation("删除店铺类目关联表")
    @PreAuthorize("@el.check('storeCatalogRelation:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        storeCatalogRelationService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}