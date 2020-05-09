package co.lq.modules.system.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;
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
import co.lq.modules.system.domain.Dict;
import co.lq.modules.system.service.PlatformDictService;
import co.lq.modules.system.service.dto.DictQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2019-04-10
 */
@Api(tags = "系统：字典管理")
@RestController
@RequestMapping("/api/dict")
public class DictController {

    private final PlatformDictService platformDictService;

    private static final String       ENTITY_NAME = "dict";

    public DictController(PlatformDictService platformDictService) {
        this.platformDictService = platformDictService;
    }

    @Log("导出字典数据")
    @ApiOperation("导出字典数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','dict:list')")
    public void download(HttpServletResponse response, DictQueryCriteria criteria) throws IOException {
        platformDictService.download(platformDictService.queryAll(criteria), response);
    }

    @Log("查询字典")
    @ApiOperation("查询字典")
    @GetMapping(value = "/all")
    @PreAuthorize("@el.check('admin','dict:list')")
    public ResponseEntity<Object> all() {
        return new ResponseEntity<>(platformDictService.queryAll(new DictQueryCriteria()), HttpStatus.OK);
    }

    @Log("查询字典")
    @ApiOperation("查询字典")
    @GetMapping
    @PreAuthorize("@el.check('admin','dict:list')")
    public ResponseEntity<Object> getDicts(DictQueryCriteria resources, Pageable pageable) {
        return new ResponseEntity<>(platformDictService.queryAll(resources, pageable), HttpStatus.OK);
    }

    @Log("新增字典")
    @ApiOperation("新增字典")
    @PostMapping
    @PreAuthorize("@el.check('admin','dict:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Dict resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return new ResponseEntity<>(platformDictService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改字典")
    @ApiOperation("修改字典")
    @PutMapping
    @PreAuthorize("@el.check('admin','dict:edit')")
    public ResponseEntity<Object> update(@Validated(Dict.Update.class) @RequestBody Dict resources) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        platformDictService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除字典")
    @ApiOperation("删除字典")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("@el.check('admin','dict:del')")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        platformDictService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
