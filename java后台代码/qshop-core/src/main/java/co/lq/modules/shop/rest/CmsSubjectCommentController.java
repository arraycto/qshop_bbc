package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.CmsSubjectComment;
import co.lq.modules.shop.service.CmsSubjectCommentService;
import co.lq.modules.shop.service.dto.CmsSubjectCommentQueryCriteria;
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
@Api(tags = "专题评论表管理")
@RestController
@RequestMapping("/api/cmsSubjectComment")
public class CmsSubjectCommentController {

    private final CmsSubjectCommentService cmsSubjectCommentService;

    public CmsSubjectCommentController(CmsSubjectCommentService cmsSubjectCommentService) {
        this.cmsSubjectCommentService = cmsSubjectCommentService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('cmsSubjectComment:list')")
    public void download(HttpServletResponse response, CmsSubjectCommentQueryCriteria criteria) throws IOException {
        cmsSubjectCommentService.download(cmsSubjectCommentService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询专题评论表")
    @ApiOperation("查询专题评论表")
    @PreAuthorize("@el.check('cmsSubjectComment:list')")
    public ResponseEntity<Object> getCmsSubjectComments(CmsSubjectCommentQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(cmsSubjectCommentService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增专题评论表")
    @ApiOperation("新增专题评论表")
    @PreAuthorize("@el.check('cmsSubjectComment:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody CmsSubjectComment resources){
        return new ResponseEntity<>(cmsSubjectCommentService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改专题评论表")
    @ApiOperation("修改专题评论表")
    @PreAuthorize("@el.check('cmsSubjectComment:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody CmsSubjectComment resources){
        cmsSubjectCommentService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除专题评论表")
    @ApiOperation("删除专题评论表")
    @PreAuthorize("@el.check('cmsSubjectComment:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        cmsSubjectCommentService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}