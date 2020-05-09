package co.lq.modules.shop.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;
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
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.modules.shop.domain.StoreCategoryAttr;
import co.lq.modules.shop.service.StoreCategoryAttrService;
import co.lq.modules.shop.service.dto.StoreCategoryAttrQueryCriteria;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-03-10
 */
@Api(tags = "店铺分类属性管理")
@RestController
@RequestMapping("/api/storeCategoryAttr")
public class CategoryAttrController {

    private final StoreCategoryAttrService storeCategoryAttrService;
    private final UserDetailsService       userDetailsService;

    public CategoryAttrController(StoreCategoryAttrService storeCategoryAttrService,
                                  UserDetailsService userDetailsService) {
        this.storeCategoryAttrService = storeCategoryAttrService;
        this.userDetailsService = userDetailsService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('storeCategoryAttr:list')")
    public void download(HttpServletResponse response, StoreCategoryAttrQueryCriteria criteria) throws IOException {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        storeCategoryAttrService.download(storeCategoryAttrService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询店铺分类属性")
    @ApiOperation("查询店铺分类属性")
    @PreAuthorize("@el.check('storeCategoryAttr:list')")
    public ResponseEntity<Object> getQshopStoreCategoryAttrs(StoreCategoryAttrQueryCriteria criteria,
                                                             Pageable pageable) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(storeCategoryAttrService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增店铺分类属性")
    @ApiOperation("新增店铺分类属性")
    @PreAuthorize("@el.check('storeCategoryAttr:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody StoreCategoryAttr resources) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        resources.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(storeCategoryAttrService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改店铺分类属性")
    @ApiOperation("修改店铺分类属性")
    @PreAuthorize("@el.check('storeCategoryAttr:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody StoreCategoryAttr resources) {
        storeCategoryAttrService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除店铺分类属性")
    @ApiOperation("删除店铺分类属性")
    @PreAuthorize("@el.check('storeCategoryAttr:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        storeCategoryAttrService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
