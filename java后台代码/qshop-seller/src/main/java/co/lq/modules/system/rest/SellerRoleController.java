package co.lq.modules.system.rest;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.lang.Dict;
import co.lq.aop.log.Log;
import co.lq.exception.BadRequestException;
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.modules.system.domain.SellerRole;
import co.lq.modules.system.service.SellerRoleService;
import co.lq.modules.system.service.SellerService;
import co.lq.modules.system.service.dto.SellerDTO;
import co.lq.modules.system.service.dto.SellerRoleDTO;
import co.lq.modules.system.service.dto.SellerRoleQueryCriteria;
import co.lq.modules.system.service.dto.SellerRoleSmallDTO;
import co.lq.utils.SecurityUtils;
import co.lq.utils.ThrowableUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2018-12-03
 */
@Api(tags = "系统：角色管理")
@RestController
@RequestMapping("/api/roles")
public class SellerRoleController {

    private final SellerRoleService  sellerRoleService;
    private final SellerService      sellerService;
    private final UserDetailsService userDetailsService;

    private static final String      ENTITY_NAME = "role";

    public SellerRoleController(SellerRoleService sellerRoleService, SellerService sellerService,
                                UserDetailsService userDetailsService) {
        this.sellerRoleService = sellerRoleService;
        this.sellerService = sellerService;
        this.userDetailsService = userDetailsService;
    }

    @ApiOperation("获取单个role")
    @GetMapping(value = "/{id}")
    @PreAuthorize("@el.check('roles:list')")
    public ResponseEntity<Object> getRoles(@PathVariable Long id) {
        return new ResponseEntity<>(sellerRoleService.findById(id), HttpStatus.OK);
    }

    @Log("导出角色数据")
    @ApiOperation("导出角色数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('role:list')")
    public void download(HttpServletResponse response, SellerRoleQueryCriteria criteria) throws IOException {
        sellerRoleService.download(sellerRoleService.queryAll(criteria), response);
    }

    @ApiOperation("返回全部的角色")
    @GetMapping(value = "/all")
    @PreAuthorize("@el.check('roles:list','user:add','user:edit')")
    public ResponseEntity<Object> getAll(@PageableDefault(value = 2000, sort = {
            "level" }, direction = Sort.Direction.ASC) Pageable pageable, SellerRoleQueryCriteria criteria) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(sellerRoleService.queryAll(pageable), HttpStatus.OK);
    }

    @Log("查询角色")
    @ApiOperation("查询角色")
    @GetMapping
    @PreAuthorize("@el.check('roles:list')")
    public ResponseEntity<Object> getRoles(SellerRoleQueryCriteria criteria, Pageable pageable) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(sellerRoleService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @ApiOperation("获取用户级别")
    @GetMapping(value = "/level")
    public ResponseEntity<Object> getLevel() {
        return new ResponseEntity<>(Dict.create().set("level", getLevels(null)), HttpStatus.OK);
    }

    @Log("新增角色")
    @ApiOperation("新增角色")
    @PostMapping
    @PreAuthorize("@el.check('roles:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody SellerRole resources) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        getLevels(resources.getLevel());
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        resources.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(sellerRoleService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改角色")
    @ApiOperation("修改角色")
    @PutMapping
    @PreAuthorize("@el.check('roles:edit')")
    public ResponseEntity<Object> update(@Validated(SellerRole.Update.class) @RequestBody SellerRole resources) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        getLevels(resources.getLevel());
        sellerRoleService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("修改角色菜单")
    @ApiOperation("修改角色菜单")
    @PutMapping(value = "/menu")
    @PreAuthorize("@el.check('roles:edit')")
    public ResponseEntity<Object> updateMenu(@RequestBody SellerRole resources) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        SellerRoleDTO role = sellerRoleService.findById(resources.getId());
        getLevels(role.getLevel());
        sellerRoleService.updateMenu(resources, role);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除角色")
    @ApiOperation("删除角色")
    @DeleteMapping
    @PreAuthorize("@el.check('roles:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        for (Long id : ids) {
            SellerRoleDTO role = sellerRoleService.findById(id);
            getLevels(role.getLevel());
        }
        try {
            sellerRoleService.delete(ids);
        } catch (Throwable e) {
            ThrowableUtil.throwForeignKeyException(e, "所选角色存在用户关联，请取消关联后再试");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 获取用户的角色级别
     *
     * @return /
     */
    private int getLevels(Integer level) {
        SellerDTO user = sellerService.findByName(SecurityUtils.getUsername());
        List<Integer> levels = sellerRoleService.findByUsersId(user.getId())
                .stream()
                .map(SellerRoleSmallDTO::getLevel)
                .collect(Collectors.toList());
        int min = Collections.min(levels);
        if (level != null) {
            if (level < min) {
                throw new BadRequestException("权限不足，你的角色级别：" + min + "，低于操作的角色级别：" + level);
            }
        }
        return min;
    }
}
