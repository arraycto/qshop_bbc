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
import co.lq.modules.shop.domain.StoreOrderReturnReason;
import co.lq.modules.shop.service.StoreOrderReturnReasonService;
import co.lq.modules.shop.service.dto.StoreOrderReturnReasonQueryCriteria;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-03-27
 */
@Api(tags = "订单退货原因管理")
@RestController
@RequestMapping("/api/orderReturnReason")
public class OrderReturnReasonController {

    private final StoreOrderReturnReasonService storeOrderReturnReasonService;
    private final UserDetailsService            userDetailsService;

    public OrderReturnReasonController(StoreOrderReturnReasonService storeOrderReturnReasonService,
                                       UserDetailsService userDetailsService) {
        this.storeOrderReturnReasonService = storeOrderReturnReasonService;
        this.userDetailsService = userDetailsService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('orderReturnReason:list')")
    public void download(HttpServletResponse response, StoreOrderReturnReasonQueryCriteria criteria)
            throws IOException {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        storeOrderReturnReasonService.download(storeOrderReturnReasonService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询订单退货原因")
    @ApiOperation("查询订单退货原因")
    @PreAuthorize("@el.check('orderReturnReason:list')")
    public ResponseEntity<Object> getOrderReturnReasons(StoreOrderReturnReasonQueryCriteria criteria,
                                                        Pageable pageable) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(storeOrderReturnReasonService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增订单退货原因")
    @ApiOperation("新增订单退货原因")
    @PreAuthorize("@el.check('orderReturnReason:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody StoreOrderReturnReason resources) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        resources.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(storeOrderReturnReasonService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改订单退货原因")
    @ApiOperation("修改订单退货原因")
    @PreAuthorize("@el.check('orderReturnReason:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody StoreOrderReturnReason resources) {
        storeOrderReturnReasonService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除订单退货原因")
    @ApiOperation("删除订单退货原因")
    @PreAuthorize("@el.check('orderReturnReason:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        storeOrderReturnReasonService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
