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
import co.lq.modules.shop.domain.FeightTemplate;
import co.lq.modules.shop.service.FeightTemplateService;
import co.lq.modules.shop.service.dto.FeightTemplateQueryCriteria;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-03-27
 */
@Api(tags = "订单运费模板管理")
@RestController
@RequestMapping("/api/feightTemplate")
public class FeightTemplateController {

    private final FeightTemplateService feightTemplateService;
    private final UserDetailsService    userDetailsService;

    public FeightTemplateController(FeightTemplateService feightTemplateService,
                                    UserDetailsService userDetailsService) {
        this.feightTemplateService = feightTemplateService;
        this.userDetailsService = userDetailsService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('feightTemplate:list')")
    public void download(HttpServletResponse response, FeightTemplateQueryCriteria criteria) throws IOException {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        feightTemplateService.download(feightTemplateService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询订单运费模板")
    @ApiOperation("查询订单运费模板")
    @PreAuthorize("@el.check('feightTemplate:list')")
    public ResponseEntity<Object> getFeightTemplates(FeightTemplateQueryCriteria criteria, Pageable pageable) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(feightTemplateService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增订单运费模板")
    @ApiOperation("新增订单运费模板")
    @PreAuthorize("@el.check('feightTemplate:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody FeightTemplate resources) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        resources.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(feightTemplateService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改订单运费模板")
    @ApiOperation("修改订单运费模板")
    @PreAuthorize("@el.check('feightTemplate:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody FeightTemplate resources) {
        feightTemplateService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除订单运费模板")
    @ApiOperation("删除订单运费模板")
    @PreAuthorize("@el.check('feightTemplate:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        feightTemplateService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
