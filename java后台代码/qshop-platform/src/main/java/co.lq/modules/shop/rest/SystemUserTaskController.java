package co.lq.modules.shop.rest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import co.lq.modules.shop.domain.UserTask;
import co.lq.modules.shop.service.UserTaskService;
import co.lq.modules.shop.service.dto.UserTaskQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2019-12-04
 */
@Api(tags = "商城:用户任务管理")
@RestController
@RequestMapping("api")
public class SystemUserTaskController {

    private final UserTaskService userTaskService;

    public SystemUserTaskController(UserTaskService userTaskService) {
        this.userTaskService = userTaskService;
    }

    @Log("查询")
    @ApiOperation(value = "查询")
    @GetMapping(value = "/systemUserTask")
    @PreAuthorize("@el.check('admin','SYSTEMUSERTASK_ALL','SYSTEMUSERTASK_SELECT')")
    public ResponseEntity getSystemUserTasks(UserTaskQueryCriteria criteria, Pageable pageable) {
        Sort sort = new Sort(Sort.Direction.ASC, "levelId");
        Pageable pageableT = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        criteria.setStoreId(0L);
        return new ResponseEntity(userTaskService.queryAll(criteria, pageableT), HttpStatus.OK);
    }

    @Log("新增")
    @ApiOperation(value = "新增")
    @PostMapping(value = "/systemUserTask")
    @PreAuthorize("@el.check('admin','SYSTEMUSERTASK_ALL','SYSTEMUSERTASK_CREATE')")
    public ResponseEntity create(@Validated @RequestBody UserTask resources) {
        return new ResponseEntity(userTaskService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改")
    @ApiOperation(value = "修改")
    @PutMapping(value = "/systemUserTask")
    @PreAuthorize("@el.check('admin','SYSTEMUSERTASK_ALL','SYSTEMUSERTASK_EDIT')")
    public ResponseEntity update(@Validated @RequestBody UserTask resources) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        userTaskService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "/systemUserTask/{id}")
    @PreAuthorize("@el.check('admin','SYSTEMUSERTASK_ALL','SYSTEMUSERTASK_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        userTaskService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
