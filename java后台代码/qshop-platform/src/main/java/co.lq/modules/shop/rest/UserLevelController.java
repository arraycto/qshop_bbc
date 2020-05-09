package co.lq.modules.shop.rest;

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
import co.lq.modules.shop.domain.UserLevel;
import co.lq.modules.shop.service.UserLevelService;
import co.lq.modules.shop.service.dto.UserLevelQueryCriteria;
import co.lq.utils.OrderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2019-12-04
 */
@Api(tags = "商城:用户等级管理")
@RestController
@RequestMapping("api")
public class UserLevelController {

    private final UserLevelService userLevelService;

    public UserLevelController(UserLevelService userLevelService) {
        this.userLevelService = userLevelService;
    }

    @Log("查询")
    @ApiOperation(value = "查询")
    @GetMapping(value = "/systemUserLevel")
    @PreAuthorize("@el.check('admin','SYSTEMUSERLEVEL_ALL','SYSTEMUSERLEVEL_SELECT')")
    public ResponseEntity getSystemUserLevels(UserLevelQueryCriteria criteria, Pageable pageable) {
        criteria.setStoreId(0L);
        return new ResponseEntity(userLevelService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("新增")
    @ApiOperation(value = "新增")
    @PostMapping(value = "/systemUserLevel")
    @PreAuthorize("@el.check('admin','SYSTEMUSERLEVEL_ALL','SYSTEMUSERLEVEL_CREATE')")
    public ResponseEntity create(@Validated @RequestBody UserLevel resources) {
        resources.setStoreId(0L);
        resources.setAddTime(OrderUtil.getSecondTimestampTwo());
        return new ResponseEntity(userLevelService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改")
    @ApiOperation(value = "修改")
    @PutMapping(value = "/systemUserLevel")
    @PreAuthorize("@el.check('admin','SYSTEMUSERLEVEL_ALL','SYSTEMUSERLEVEL_EDIT')")
    public ResponseEntity update(@Validated @RequestBody UserLevel resources) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        userLevelService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "/systemUserLevel/{id}")
    @PreAuthorize("@el.check('admin','SYSTEMUSERLEVEL_ALL','SYSTEMUSERLEVEL_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        userLevelService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
