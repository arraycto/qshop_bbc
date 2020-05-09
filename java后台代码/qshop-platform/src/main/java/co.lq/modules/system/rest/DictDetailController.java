package co.lq.modules.system.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.lq.aop.log.Log;
import co.lq.exception.BadRequestException;
import co.lq.modules.system.domain.DictDetail;
import co.lq.modules.system.service.PlatformDictDetailService;
import co.lq.modules.system.service.dto.DictDetailQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2019-04-10
 */
@RestController
@Api(tags = "系统：字典详情管理")
@RequestMapping("/api/dictDetail")
public class DictDetailController {

    private final PlatformDictDetailService platformDictDetailService;

    private static final String             ENTITY_NAME = "dictDetail";

    public DictDetailController(PlatformDictDetailService platformDictDetailService) {
        this.platformDictDetailService = platformDictDetailService;
    }

    @Log("查询字典详情")
    @ApiOperation("查询字典详情")
    @GetMapping
    public ResponseEntity<Object> getDictDetails(DictDetailQueryCriteria criteria, @PageableDefault(sort = {
            "sort" }, direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(platformDictDetailService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("查询多个字典详情")
    @ApiOperation("查询多个字典详情")
    @GetMapping(value = "/map")
    public ResponseEntity<Object> getDictDetailMaps(DictDetailQueryCriteria criteria, @PageableDefault(sort = {
            "sort" }, direction = Sort.Direction.ASC) Pageable pageable) {
        String[] names = criteria.getDictName().split(",");
        Map<String, Object> map = new HashMap<>(names.length);
        for (String name : names) {
            criteria.setDictName(name);
            map.put(name, platformDictDetailService.queryAll(criteria, pageable).get("content"));
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Log("新增字典详情")
    @ApiOperation("新增字典详情")
    @PostMapping
    @PreAuthorize("@el.check('admin','dict:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody DictDetail resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return new ResponseEntity<>(platformDictDetailService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改字典详情")
    @ApiOperation("修改字典详情")
    @PutMapping
    @PreAuthorize("@el.check('admin','dict:edit')")
    public ResponseEntity<Object> update(@Validated(DictDetail.Update.class) @RequestBody DictDetail resources) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        platformDictDetailService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除字典详情")
    @ApiOperation("删除字典详情")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("@el.check('admin','dict:del')")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        platformDictDetailService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
