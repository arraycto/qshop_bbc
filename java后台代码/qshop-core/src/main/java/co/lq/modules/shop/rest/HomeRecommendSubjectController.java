package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.HomeRecommendSubject;
import co.lq.modules.shop.service.HomeRecommendSubjectService;
import co.lq.modules.shop.service.dto.HomeRecommendSubjectQueryCriteria;
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
@Api(tags = "首页推荐专题表管理")
@RestController
@RequestMapping("/api/homeRecommendSubject")
public class HomeRecommendSubjectController {

    private final HomeRecommendSubjectService homeRecommendSubjectService;

    public HomeRecommendSubjectController(HomeRecommendSubjectService homeRecommendSubjectService) {
        this.homeRecommendSubjectService = homeRecommendSubjectService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('homeRecommendSubject:list')")
    public void download(HttpServletResponse response, HomeRecommendSubjectQueryCriteria criteria) throws IOException {
        homeRecommendSubjectService.download(homeRecommendSubjectService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询首页推荐专题表")
    @ApiOperation("查询首页推荐专题表")
    @PreAuthorize("@el.check('homeRecommendSubject:list')")
    public ResponseEntity<Object> getHomeRecommendSubjects(HomeRecommendSubjectQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(homeRecommendSubjectService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增首页推荐专题表")
    @ApiOperation("新增首页推荐专题表")
    @PreAuthorize("@el.check('homeRecommendSubject:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody HomeRecommendSubject resources){
        return new ResponseEntity<>(homeRecommendSubjectService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改首页推荐专题表")
    @ApiOperation("修改首页推荐专题表")
    @PreAuthorize("@el.check('homeRecommendSubject:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody HomeRecommendSubject resources){
        homeRecommendSubjectService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除首页推荐专题表")
    @ApiOperation("删除首页推荐专题表")
    @PreAuthorize("@el.check('homeRecommendSubject:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        homeRecommendSubjectService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}