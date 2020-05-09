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
import co.lq.modules.shop.domain.OrderRefund;
import co.lq.modules.shop.service.OrderRefundService;
import co.lq.modules.shop.service.dto.OrderRefundQueryCriteria;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-03-29
 */
@Api(tags = "订单退货申请管理")
@RestController
@RequestMapping("/api/orderRefund")
public class OrderRefundController {

    private final OrderRefundService orderRefundService;
    private final UserDetailsService userDetailsService;

    public OrderRefundController(OrderRefundService orderRefundService, UserDetailsService userDetailsService) {
        this.orderRefundService = orderRefundService;
        this.userDetailsService = userDetailsService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('orderRefund:list')")
    public void download(HttpServletResponse response, OrderRefundQueryCriteria criteria) throws IOException {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        orderRefundService.download(orderRefundService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询订单退货申请")
    @ApiOperation("查询订单退货申请")
    @PreAuthorize("@el.check('orderRefund:list')")
    public ResponseEntity<Object> getOrderRefunds(OrderRefundQueryCriteria criteria, Pageable pageable) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(orderRefundService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增订单退货申请")
    @ApiOperation("新增订单退货申请")
    @PreAuthorize("@el.check('orderRefund:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody OrderRefund resources) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        resources.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(orderRefundService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改订单退货申请")
    @ApiOperation("修改订单退货申请")
    @PreAuthorize("@el.check('orderRefund:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody OrderRefund resources) {
        resources.setHandleMan(SecurityUtils.getUsername());
        orderRefundService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除订单退货申请")
    @ApiOperation("删除订单退货申请")
    @PreAuthorize("@el.check('orderRefund:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        orderRefundService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
