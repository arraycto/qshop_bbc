package co.lq.rest;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.lq.aop.log.Log;
import co.lq.domain.LocalStorage;
import co.lq.service.LocalStorageService;
import co.lq.service.dto.LocalStorageQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2019-09-05
 */
@Api(tags = "工具：本地存储管理")
@RestController
@RequestMapping("/api/localStorage")
public class LocalStorageController {

    private final LocalStorageService localStorageService;

    public LocalStorageController(LocalStorageService localStorageService) {
        this.localStorageService = localStorageService;
    }

    @ApiOperation("查询文件")
    @GetMapping
    @PreAuthorize("@el.check('storage:list')")
    public ResponseEntity<Object> getLocalStorages(LocalStorageQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(localStorageService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('storage:list')")
    public void download(HttpServletResponse response, LocalStorageQueryCriteria criteria) throws IOException {
        localStorageService.download(localStorageService.queryAll(criteria), response);
    }

    @ApiOperation("上传文件")
    @PostMapping
    @PreAuthorize("@el.check('storage:add')")
    public ResponseEntity<Object> create(@RequestParam String name, @RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(localStorageService.create(name, file), HttpStatus.CREATED);
    }

    @ApiOperation("修改文件")
    @PutMapping
    @PreAuthorize("@el.check('storage:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody LocalStorage resources) {
        localStorageService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("多选删除")
    @DeleteMapping
    @ApiOperation("多选删除")
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        localStorageService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
