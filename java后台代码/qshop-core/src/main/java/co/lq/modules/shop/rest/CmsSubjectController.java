package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.CmsSubject;
import co.lq.modules.shop.service.CmsSubjectService;
import co.lq.modules.shop.service.dto.CmsSubjectQueryCriteria;
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
@Api(tags = "专题表管理")
@RestController
@RequestMapping("/api/cmsSubject")
public class CmsSubjectController {

    private final CmsSubjectService cmsSubjectService;

    public CmsSubjectController(CmsSubjectService cmsSubjectService) {
        this.cmsSubjectService = cmsSubjectService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('cmsSubject:list')")
    public void download(HttpServletResponse response, CmsSubjectQueryCriteria criteria) throws IOException {
        cmsSubjectService.download(cmsSubjectService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询专题表")
    @ApiOperation("查询专题表")
    @PreAuthorize("@el.check('cmsSubject:list')")
    public ResponseEntity<Object> getCmsSubjects(CmsSubjectQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(cmsSubjectService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增专题表")
    @ApiOperation("新增专题表")
    @PreAuthorize("@el.check('cmsSubject:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody CmsSubject resources){
        return new ResponseEntity<>(cmsSubjectService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改专题表")
    @ApiOperation("修改专题表")
    @PreAuthorize("@el.check('cmsSubject:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody CmsSubject resources){
        cmsSubjectService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除专题表")
    @ApiOperation("删除专题表")
    @PreAuthorize("@el.check('cmsSubject:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        cmsSubjectService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}