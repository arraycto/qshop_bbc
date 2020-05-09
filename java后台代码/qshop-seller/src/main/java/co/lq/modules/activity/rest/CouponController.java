package co.lq.modules.activity.rest;

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

import co.lq.aop.log.Log;
import co.lq.modules.activity.domain.StoreCoupon;
import co.lq.modules.activity.service.StoreCouponService;
import co.lq.modules.activity.service.dto.StoreCouponQueryCriteria;
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2019-11-09
 */
@Api(tags = "商城:优惠券管理")
@RestController
@RequestMapping("api")
public class CouponController {

    private final StoreCouponService storeCouponService;
    private final UserDetailsService userDetailsService;

    public CouponController(StoreCouponService storeCouponService, UserDetailsService userDetailsService) {
        this.storeCouponService = storeCouponService;
        this.userDetailsService = userDetailsService;
    }

    @Log("查询")
    @ApiOperation(value = "查询")
    @GetMapping(value = "/storeCoupon")
    @PreAuthorize("@el.check('admin','STORECOUPON_ALL','STORECOUPON_SELECT')")
    public ResponseEntity getStoreCoupons(StoreCouponQueryCriteria criteria, Pageable pageable) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(storeCouponService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("新增")
    @ApiOperation(value = "新增")
    @PostMapping(value = "/storeCoupon")
    @PreAuthorize("@el.check('admin','STORECOUPON_ALL','STORECOUPON_CREATE')")
    public ResponseEntity create(@Validated @RequestBody StoreCoupon resources) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        resources.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(storeCouponService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改")
    @ApiOperation(value = "修改")
    @PutMapping(value = "/storeCoupon")
    @PreAuthorize("@el.check('admin','STORECOUPON_ALL','STORECOUPON_EDIT')")
    public ResponseEntity update(@Validated @RequestBody StoreCoupon resources) {
        storeCouponService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "/storeCoupon/{id}")
    @PreAuthorize("@el.check('admin','STORECOUPON_ALL','STORECOUPON_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        StoreCoupon resources = new StoreCoupon();
        resources.setId(id);
        resources.setIsDel(1);
        storeCouponService.update(resources);
        return new ResponseEntity(HttpStatus.OK);
    }
}
