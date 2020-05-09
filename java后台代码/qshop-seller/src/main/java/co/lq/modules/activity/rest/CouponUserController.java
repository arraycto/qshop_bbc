package co.lq.modules.activity.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.lq.aop.log.Log;
import co.lq.modules.activity.service.StoreCouponUserService;
import co.lq.modules.activity.service.dto.StoreCouponUserQueryCriteria;
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2019-11-10
 */
@Api(tags = "商城:优惠券发放记录管理")
@RestController
@RequestMapping("api")
public class CouponUserController {

    private final StoreCouponUserService storeCouponUserService;
    private final UserDetailsService     userDetailsService;

    public CouponUserController(StoreCouponUserService storeCouponUserService, UserDetailsService userDetailsService) {
        this.storeCouponUserService = storeCouponUserService;
        this.userDetailsService = userDetailsService;
    }

    @Log("查询Y")
    @ApiOperation(value = "查询")
    @GetMapping(value = "/storeCouponUser")
    @PreAuthorize("@el.check('admin','STORECOUPONUSER_ALL','STORECOUPONUSER_SELECT')")
    public ResponseEntity getStoreCouponUsers(StoreCouponUserQueryCriteria criteria, Pageable pageable) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(storeCouponUserService.queryAll(criteria, pageable), HttpStatus.OK);
    }

}
