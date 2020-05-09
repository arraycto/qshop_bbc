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

import co.lq.aop.log.Log;
import co.lq.exception.BadRequestException;
import co.lq.modules.system.domain.PlatformMenu;
import co.lq.modules.system.service.PlatformMenuService;
import co.lq.modules.system.service.PlatformRoleService;
import co.lq.modules.system.service.PlatformUserService;
import co.lq.modules.system.service.dto.MenuDTO;
import co.lq.modules.system.service.dto.MenuQueryCriteria;
import co.lq.modules.system.service.dto.PlatformUserDTO;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2018-12-03
 */
@Api(tags = "系统：菜单管理")
@RestController
@RequestMapping("/api/menus")
@SuppressWarnings("unchecked")
public class MenuController {

    private final PlatformMenuService platformMenuService;

    private final PlatformUserService platformUserService;

    private final PlatformRoleService platformRoleService;

    private static final String       ENTITY_NAME = "menu";

    public MenuController(PlatformMenuService platformMenuService, PlatformUserService platformUserService,
                          PlatformRoleService platformRoleService) {
        this.platformMenuService = platformMenuService;
        this.platformUserService = platformUserService;
        this.platformRoleService = platformRoleService;
    }

    @Log("导出菜单数据")
    @ApiOperation("导出菜单数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('menu:list')")
    public void download(HttpServletResponse response, MenuQueryCriteria criteria) throws IOException {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        platformMenuService.download(platformMenuService.queryAll(criteria), response);
    }

    @ApiOperation("获取前端所需菜单")
    @GetMapping(value = "/build")
    public ResponseEntity<Object> buildMenus() {
        PlatformUserDTO user = platformUserService.findByName(SecurityUtils.getUsername());
        List<MenuDTO> menuDtoList = platformMenuService.findByRoles(platformRoleService.findByUsersId(user.getId()));
        List<MenuDTO> menuDtos = (List<MenuDTO>) platformMenuService.buildTree(menuDtoList).get("content");
        return new ResponseEntity<>(platformMenuService.buildMenus(menuDtos), HttpStatus.OK);
    }

    @ApiOperation("返回全部的菜单")
    @GetMapping(value = "/tree")
    @PreAuthorize("@el.check('menu:list','roles:list')")
    public ResponseEntity<Object> getMenuTree() {
        return new ResponseEntity<>(platformMenuService.getMenuTree(platformMenuService.findByPid(0L)), HttpStatus.OK);
    }

    @Log("查询菜单")
    @ApiOperation("查询菜单")
    @GetMapping
    @PreAuthorize("@el.check('menu:list')")
    public ResponseEntity<Object> getMenus(MenuQueryCriteria criteria) {
        List<MenuDTO> menuDtoList = platformMenuService.queryAll(criteria);
        return new ResponseEntity<>(platformMenuService.buildTree(menuDtoList), HttpStatus.OK);
    }

    @Log("新增菜单")
    @ApiOperation("新增菜单")
    @PostMapping
    @PreAuthorize("@el.check('menu:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody PlatformMenu resources) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return new ResponseEntity<>(platformMenuService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改菜单")
    @ApiOperation("修改菜单")
    @PutMapping
    @PreAuthorize("@el.check('menu:edit')")
    public ResponseEntity<Object> update(@Validated(PlatformMenu.Update.class) @RequestBody PlatformMenu resources) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        platformMenuService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除菜单")
    @ApiOperation("删除菜单")
    @DeleteMapping
    @PreAuthorize("@el.check('menu:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        Set<PlatformMenu> platformMenuSet = new HashSet<>();
        for (Long id : ids) {
            List<PlatformMenu> platformMenuList = platformMenuService.findByPid(id);
            platformMenuSet.add(platformMenuService.findOne(id));
            platformMenuSet = platformMenuService.getDeleteMenus(platformMenuList, platformMenuSet);
        }
        platformMenuService.delete(platformMenuSet);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
