package co.lq.modules.shop.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import co.lq.aop.log.Log;
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.modules.shop.domain.MaterialGroup;
import co.lq.modules.shop.service.MaterialGroupService;
import co.lq.modules.shop.service.dto.MaterialGroupQueryCriteria;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-01-09
 */
@Api(tags = "商城:素材分组管理")
@RestController
@RequestMapping("/api/materialgroup")
public class MaterialGroupController {

    private final MaterialGroupService materialGroupService;
    private final UserDetailsService   userDetailsService;

    public MaterialGroupController(MaterialGroupService materialGroupService, UserDetailsService userDetailsService) {
        this.materialGroupService = materialGroupService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping(value = "/page")
    @Log("查询素材分组")
    @ApiOperation("查询素材分组")
    public ResponseEntity<Object> getMaterialGroups(MaterialGroupQueryCriteria criteria, Pageable pageable) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(materialGroupService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增素材分组")
    @ApiOperation("新增素材分组")
    public ResponseEntity<Object> create(@Validated @RequestBody MaterialGroup resources) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        resources.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(materialGroupService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改素材分组")
    @ApiOperation("修改素材分组")
    public ResponseEntity<Object> update(@Validated @RequestBody MaterialGroup resources) {
        materialGroupService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除素材分组")
    @ApiOperation("删除素材分组")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteAll(@PathVariable String id) {
        materialGroupService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
