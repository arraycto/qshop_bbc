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
import co.lq.modules.shop.domain.GroupActivity;
import co.lq.modules.shop.service.GroupActivityService;
import co.lq.modules.shop.service.dto.GroupActivityQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-04-02
 */
@Api(tags = "团购活动管理")
@RestController
@RequestMapping("/api/smsGroupActivity")
public class GroupActivityController {

    private final GroupActivityService groupActivityService;

    public GroupActivityController(GroupActivityService groupActivityService) {
        this.groupActivityService = groupActivityService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('smsGroupActivity:list')")
    public void download(HttpServletResponse response, GroupActivityQueryCriteria criteria) throws IOException {
        groupActivityService.download(groupActivityService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询团购活动")
    @ApiOperation("查询团购活动")
    @PreAuthorize("@el.check('smsGroupActivity:list')")
    public ResponseEntity<Object> getSmsGroupActivitys(GroupActivityQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(groupActivityService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增团购活动")
    @ApiOperation("新增团购活动")
    @PreAuthorize("@el.check('smsGroupActivity:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody GroupActivity resources) {
        return new ResponseEntity<>(groupActivityService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改团购活动")
    @ApiOperation("修改团购活动")
    @PreAuthorize("@el.check('smsGroupActivity:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody GroupActivity resources) {
        groupActivityService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除团购活动")
    @ApiOperation("删除团购活动")
    @PreAuthorize("@el.check('smsGroupActivity:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        groupActivityService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
