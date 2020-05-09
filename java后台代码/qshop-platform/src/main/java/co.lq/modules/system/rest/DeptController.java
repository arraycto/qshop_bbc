package co.lq.modules.system.rest;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

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

import cn.hutool.core.collection.CollectionUtil;
import co.lq.aop.log.Log;
import co.lq.config.DataScope;
import co.lq.exception.BadRequestException;
import co.lq.modules.system.domain.PlatformDept;
import co.lq.modules.system.service.PlatformDeptService;
import co.lq.modules.system.service.dto.PlatformDeptDTO;
import co.lq.modules.system.service.dto.PlatformDeptQueryCriteria;
import co.lq.utils.ThrowableUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2019-03-25
 */
@RestController
@Api(tags = "系统：部门管理")
@RequestMapping("/api/dept")
public class DeptController {

    private final PlatformDeptService platformDeptService;

    private final DataScope           dataScope;

    private static final String       ENTITY_NAME = "platformDept";

    public DeptController(PlatformDeptService platformDeptService, DataScope dataScope) {
        this.platformDeptService = platformDeptService;
        this.dataScope = dataScope;
    }

    @Log("导出部门数据")
    @ApiOperation("导出部门数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','dept:list')")
    public void download(HttpServletResponse response, PlatformDeptQueryCriteria criteria) throws IOException {
        platformDeptService.download(platformDeptService.queryAll(criteria), response);
    }

    @Log("查询部门")
    @ApiOperation("查询部门")
    @GetMapping
    @PreAuthorize("@el.check('user:list','admin','dept:list')")
    public ResponseEntity<Object> getDepts(PlatformDeptQueryCriteria criteria) {
        // 数据权限
        criteria.setIds(dataScope.getDeptIds());
        List<PlatformDeptDTO> platformDeptDtos = platformDeptService.queryAll(criteria);
        return new ResponseEntity<>(platformDeptService.buildTree(platformDeptDtos), HttpStatus.OK);
    }

    @Log("新增部门")
    @ApiOperation("新增部门")
    @PostMapping
    @PreAuthorize("@el.check('admin','dept:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody PlatformDept resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return new ResponseEntity<>(platformDeptService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改部门")
    @ApiOperation("修改部门")
    @PutMapping
    @PreAuthorize("@el.check('admin','dept:edit')")
    public ResponseEntity<Object> update(@Validated(PlatformDept.Update.class) @RequestBody PlatformDept resources) {
        platformDeptService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除部门")
    @ApiOperation("删除部门")
    @DeleteMapping
    @PreAuthorize("@el.check('admin','dept:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        Set<PlatformDeptDTO> platformDeptDtos = new HashSet<>();
        for (Long id : ids) {
            List<PlatformDept> platformDeptList = platformDeptService.findByPid(id);
            platformDeptDtos.add(platformDeptService.findById(id));
            if (CollectionUtil.isNotEmpty(platformDeptList)) {
                platformDeptDtos = platformDeptService.getDeleteDepts(platformDeptList, platformDeptDtos);
            }
        }
        try {
            platformDeptService.delete(platformDeptDtos);
        } catch (Throwable e) {
            ThrowableUtil.throwForeignKeyException(e, "所选部门中存在岗位或者角色关联，请取消关联后再试");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
