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
import co.lq.modules.shop.domain.Material;
import co.lq.modules.shop.service.MaterialService;
import co.lq.modules.shop.service.dto.MaterialQueryCriteria;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-01-09
 */
@Api(tags = "店铺:素材管理管理")
@RestController
@RequestMapping("/api/material")
public class MaterialController {

    private final MaterialService    materialService;
    private final UserDetailsService userDetailsService;

    public MaterialController(MaterialService materialService, UserDetailsService userDetailsService) {
        this.materialService = materialService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping(value = "/page")
    @Log("查询素材管理")
    @ApiOperation("查询素材管理")
    public ResponseEntity<Object> getMaterials(MaterialQueryCriteria criteria, Pageable pageable) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(materialService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增素材管理")
    @ApiOperation("新增素材管理")
    public ResponseEntity<Object> create(@Validated @RequestBody Material resources) {
        resources.setCreateId(SecurityUtils.getUsername());
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        resources.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(materialService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改素材管理")
    @ApiOperation("修改素材管理")
    public ResponseEntity<Object> update(@Validated @RequestBody Material resources) {
        materialService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除素材管理")
    @ApiOperation("删除素材管理")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteAll(@PathVariable String id) {
        materialService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
