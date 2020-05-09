package co.lq.modules.activity.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.lq.aop.log.Log;
import co.lq.modules.activity.service.StoreCouponUserService;
import co.lq.modules.activity.service.dto.StoreCouponUserQueryCriteria;
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

    public CouponUserController(StoreCouponUserService storeCouponUserService) {
        this.storeCouponUserService = storeCouponUserService;
    }

    @Log("查询Y")
    @ApiOperation(value = "查询")
    @GetMapping(value = "/storeCouponUser")
    @PreAuthorize("@el.check('admin','STORECOUPONUSER_ALL','STORECOUPONUSER_SELECT')")
    public ResponseEntity getStoreCouponUsers(StoreCouponUserQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity(storeCouponUserService.queryAll(criteria, pageable), HttpStatus.OK);
    }

}
