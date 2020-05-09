package co.lq.modules.system.rest;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
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
import co.lq.modules.system.domain.SellerMenu;
import co.lq.modules.system.service.SellerMenuService;
import co.lq.modules.system.service.SellerRoleService;
import co.lq.modules.system.service.SellerService;
import co.lq.modules.system.service.dto.SellerDTO;
import co.lq.modules.system.service.dto.SellerMenuDTO;
import co.lq.modules.system.service.dto.SellerMenuQueryCriteria;
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
public class SellerMenuController {

    private final SellerMenuService  sellerMenuService;

    private final SellerService      sellerService;

    private final SellerRoleService  sellerRoleService;

    private final UserDetailsService userDetailsService;

    private static final String      ENTITY_NAME = "menu";

    public SellerMenuController(SellerMenuService sellerMenuService, SellerService sellerService,
                                SellerRoleService sellerRoleService, UserDetailsService userDetailsService) {
        this.sellerMenuService = sellerMenuService;
        this.sellerService = sellerService;
        this.sellerRoleService = sellerRoleService;
        this.userDetailsService = userDetailsService;
    }

    @Log("导出菜单数据")
    @ApiOperation("导出菜单数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('menu:list')")
    public void download(HttpServletResponse response, SellerMenuQueryCriteria criteria) throws IOException {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        sellerMenuService.download(sellerMenuService.queryAll(criteria), response);
    }

    @ApiOperation("获取前端所需菜单")
    @GetMapping(value = "/build")
    public ResponseEntity<Object> buildMenus() {
        SellerDTO user = sellerService.findByName(SecurityUtils.getUsername());
        List<SellerMenuDTO> sellerMenuDtoList = sellerMenuService
                .findByRoles(sellerRoleService.findByUsersId(user.getId()));
        List<SellerMenuDTO> sellerMenuDtos = (List<SellerMenuDTO>) sellerMenuService.buildTree(sellerMenuDtoList)
                .get("content");
        return new ResponseEntity<>(sellerMenuService.buildMenus(sellerMenuDtos), HttpStatus.OK);
    }

    @ApiOperation("返回全部的菜单")
    @GetMapping(value = "/tree")
    @PreAuthorize("@el.check('menu:list','roles:list')")
    public ResponseEntity<Object> getMenuTree() {
        return new ResponseEntity<>(sellerMenuService.getMenuTree(sellerMenuService.findByPid(0L)), HttpStatus.OK);
    }

    @Log("查询菜单")
    @ApiOperation("查询菜单")
    @GetMapping
    @PreAuthorize("@el.check('menu:list')")
    public ResponseEntity<Object> getMenus(SellerMenuQueryCriteria criteria) {
        List<SellerMenuDTO> sellerMenuDtoList = sellerMenuService.queryAll(criteria);
        return new ResponseEntity<>(sellerMenuService.buildTree(sellerMenuDtoList), HttpStatus.OK);
    }

    @Log("新增菜单")
    @ApiOperation("新增菜单")
    @PostMapping
    @PreAuthorize("@el.check('menu:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody SellerMenu resources) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return new ResponseEntity<>(sellerMenuService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改菜单")
    @ApiOperation("修改菜单")
    @PutMapping
    @PreAuthorize("@el.check('menu:edit')")
    public ResponseEntity<Object> update(@Validated(SellerMenu.Update.class) @RequestBody SellerMenu resources) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        sellerMenuService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除菜单")
    @ApiOperation("删除菜单")
    @DeleteMapping
    @PreAuthorize("@el.check('menu:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        Set<SellerMenu> sellerMenuSet = new HashSet<>();
        for (Long id : ids) {
            List<SellerMenu> sellerMenuList = sellerMenuService.findByPid(id);
            sellerMenuSet.add(sellerMenuService.findOne(id));
            sellerMenuSet = sellerMenuService.getDeleteMenus(sellerMenuList, sellerMenuSet);
        }
        sellerMenuService.delete(sellerMenuSet);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
