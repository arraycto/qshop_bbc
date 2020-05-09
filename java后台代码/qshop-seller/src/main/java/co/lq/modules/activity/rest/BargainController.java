package co.lq.modules.activity.rest;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;
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

import cn.hutool.core.util.ObjectUtil;
import co.lq.aop.log.Log;
import co.lq.exception.BadRequestException;
import co.lq.modules.activity.domain.StoreBargain;
import co.lq.modules.activity.service.StoreBargainService;
import co.lq.modules.activity.service.dto.StoreBargainDTO;
import co.lq.modules.activity.service.dto.StoreBargainQueryCriteria;
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.utils.OrderUtil;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2019-12-22
 */
@Api(tags = "商城:砍价管理")
@RestController
@RequestMapping("api")
public class BargainController {

    private final StoreBargainService storeBargainService;
    private final UserDetailsService  userDetailsService;

    public BargainController(StoreBargainService storeBargainService, UserDetailsService userDetailsService) {
        this.storeBargainService = storeBargainService;
        this.userDetailsService = userDetailsService;
    }

    @Log("查询砍价")
    @ApiOperation(value = "查询砍价")
    @GetMapping(value = "/storeBargain")
    @PreAuthorize("@el.check('admin','STOREBARGAIN_ALL','STOREBARGAIN_SELECT')")
    public ResponseEntity getStoreBargains(StoreBargainQueryCriteria criteria, Pageable pageable) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(storeBargainService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("新增砍价商品")
    @ApiOperation("新增砍价商品")
    @PostMapping(value = "/storeBargain")
    @PreAuthorize("@el.check('STOREBARGAIN_CREATE')")
    public ResponseEntity<Object> create(@Validated @RequestBody StoreBargain resources) {
        if (resources.getIsPostage() == 1) {
            resources.setPostage(BigDecimal.ZERO);
        }
        if (ObjectUtil.isNotNull(resources.getStartTimeDate())) {
            resources.setStartTime(OrderUtil.dateToTimestamp(resources.getStartTimeDate()));
        }
        if (ObjectUtil.isNotNull(resources.getEndTimeDate())) {
            resources.setStopTime(OrderUtil.dateToTimestamp(resources.getEndTimeDate()));
        }
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        resources.setStoreId(jwtUser.getStoreId());
        if (resources.getProductList() != null) {
            resources.setProductId(resources.getProductList().get(0).getId());
        }
        return new ResponseEntity<>(storeBargainService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改砍价")
    @ApiOperation(value = "修改砍价")
    @PutMapping(value = "/storeBargain")
    @PreAuthorize("@el.check('admin','STOREBARGAIN_ALL','STOREBARGAIN_EDIT')")
    public ResponseEntity update(HttpServletRequest request, @Validated @RequestBody StoreBargain resources) {
        if (resources.getIsPostage() == 1) {
            resources.setPostage(BigDecimal.ZERO);
        }
        if (ObjectUtil.isNotNull(resources.getStartTimeDate())) {
            resources.setStartTime(OrderUtil.dateToTimestamp(resources.getStartTimeDate()));
        }
        if (ObjectUtil.isNotNull(resources.getEndTimeDate())) {
            resources.setStopTime(OrderUtil.dateToTimestamp(resources.getEndTimeDate()));
        }
        StoreBargainQueryCriteria criteria = new StoreBargainQueryCriteria();
        criteria.setProductId(resources.getProductId());
        if (resources.getProductList() != null) {
            resources.setProductId(resources.getProductList().get(0).getId());
        }
        storeBargainService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除砍价")
    @ApiOperation(value = "删除砍价")
    @DeleteMapping(value = "/storeBargain/{id}")
    @PreAuthorize("@el.check('admin','STOREBARGAIN_ALL','STOREBARGAIN_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        StoreBargainDTO categoryDTO = storeBargainService.findById(id);
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        if (jwtUser.getStoreId() == null || !jwtUser.getStoreId().equals(categoryDTO.getStoreId())) {
            throw new BadRequestException("您无权修改此砍价商品");
        }
        storeBargainService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
