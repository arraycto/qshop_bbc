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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.lq.aop.log.Log;
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.modules.shop.domain.UserRecharge;
import co.lq.modules.shop.service.UserRechargeService;
import co.lq.modules.shop.service.dto.UserRechargeQueryCriteria;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-03-02
 */
@Api(tags = "充值管理管理")
@RestController
@RequestMapping("/api/userRecharge")
public class UserRechargeController {

    private final UserRechargeService userRechargeService;
    private final UserDetailsService  userDetailsService;

    public UserRechargeController(UserRechargeService userRechargeService, UserDetailsService userDetailsService) {
        this.userRechargeService = userRechargeService;
        this.userDetailsService = userDetailsService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('userRecharge:list')")
    public void download(HttpServletResponse response, UserRechargeQueryCriteria criteria) throws IOException {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        userRechargeService.download(userRechargeService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询充值管理")
    @ApiOperation("查询充值管理")
    @PreAuthorize("@el.check('userRecharge:list')")
    public ResponseEntity<Object> getUserRecharges(UserRechargeQueryCriteria criteria, Pageable pageable) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(userRechargeService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增充值管理")
    @ApiOperation("新增充值管理")
    @PreAuthorize("@el.check('userRecharge:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody UserRecharge resources) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        resources.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(userRechargeService.create(resources), HttpStatus.CREATED);
    }

    @Log("删除充值管理")
    @ApiOperation("删除充值管理")
    @PreAuthorize("@el.check('userRecharge:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        userRechargeService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
