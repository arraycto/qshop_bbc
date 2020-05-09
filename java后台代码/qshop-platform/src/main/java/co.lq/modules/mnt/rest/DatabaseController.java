package co.lq.modules.mnt.rest;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;

import co.lq.aop.log.Log;
import co.lq.exception.BadRequestException;
import co.lq.modules.mnt.domain.Database;
import co.lq.modules.mnt.service.DatabaseService;
import co.lq.modules.mnt.service.dto.DatabaseDto;
import co.lq.modules.mnt.service.dto.DatabaseQueryCriteria;
import co.lq.modules.mnt.util.SqlUtils;
import co.lq.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
@Api(tags = "数据库管理")
@RestController
@RequestMapping("/api/database")
public class DatabaseController {

    private String                fileSavePath = System.getProperty("java.io.tmpdir");

    private final DatabaseService databaseService;

    public DatabaseController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Log("导出数据库数据")
    @ApiOperation("导出数据库数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','database:list')")
    public void download(HttpServletResponse response, DatabaseQueryCriteria criteria) throws IOException {
        databaseService.download(databaseService.queryAll(criteria), response);
    }

    @Log("查询数据库")
    @ApiOperation(value = "查询数据库")
    @GetMapping
    @PreAuthorize("@el.check('admin','database:list')")
    public ResponseEntity<Object> getDatabases(DatabaseQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(databaseService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("新增数据库")
    @ApiOperation(value = "新增数据库")
    @PostMapping
    @PreAuthorize("@el.check('admin','database:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Database resources) {
        return new ResponseEntity<>(databaseService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改数据库")
    @ApiOperation(value = "修改数据库")
    @PutMapping
    @PreAuthorize("@el.check('admin','database:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Database resources) {
        databaseService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除数据库")
    @ApiOperation(value = "删除数据库")
    @DeleteMapping
    @PreAuthorize("@el.check('admin','database:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<String> ids) {
        databaseService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("测试数据库链接")
    @ApiOperation(value = "测试数据库链接")
    @PostMapping("/testConnect")
    @PreAuthorize("@el.check('admin','database:testConnect')")
    public ResponseEntity<Object> testConnect(@Validated @RequestBody Database resources) {
        return new ResponseEntity<>(databaseService.testConnection(resources), HttpStatus.CREATED);
    }

    @Log("执行SQL脚本")
    @ApiOperation(value = "执行SQL脚本")
    @PostMapping(value = "/upload")
    @PreAuthorize("@el.check('admin','database:add')")
    public ResponseEntity<Object> upload(@RequestBody MultipartFile file, HttpServletRequest request) throws Exception {
        String id = request.getParameter("id");
        DatabaseDto database = databaseService.findById(id);
        String fileName;
        if (database != null) {
            fileName = file.getOriginalFilename();
            File executeFile = new File(fileSavePath + fileName);
            FileUtil.del(executeFile);
            file.transferTo(executeFile);
            String result = SqlUtils.executeFile(database.getJdbcUrl(), database.getUserName(), database.getPwd(),
                    executeFile);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            throw new BadRequestException("Database not exist");
        }
    }
}
