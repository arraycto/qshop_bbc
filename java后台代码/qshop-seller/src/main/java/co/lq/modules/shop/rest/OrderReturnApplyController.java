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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.lq.aop.log.Log;
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.modules.shop.domain.OrderReturnApply;
import co.lq.modules.shop.service.OrderReturnApplyService;
import co.lq.modules.shop.service.dto.OrderReturnApplyQueryCriteria;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-03-27
 */
@Api(tags = "订单退货管理")
@RestController
@RequestMapping("/api/orderReturnApply")
public class OrderReturnApplyController {

    private final OrderReturnApplyService orderReturnApplyService;
    private final UserDetailsService      userDetailsService;

    public OrderReturnApplyController(OrderReturnApplyService orderReturnApplyService,
                                      UserDetailsService userDetailsService) {
        this.orderReturnApplyService = orderReturnApplyService;
        this.userDetailsService = userDetailsService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('orderReturnApply:list')")
    public void download(HttpServletResponse response, OrderReturnApplyQueryCriteria criteria) throws IOException {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        orderReturnApplyService.download(orderReturnApplyService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询订单退货")
    @ApiOperation("查询订单退货")
    @PreAuthorize("@el.check('orderReturnApply:list')")
    public ResponseEntity<Object> getOrderReturnApplys(OrderReturnApplyQueryCriteria criteria, Pageable pageable) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(orderReturnApplyService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/detail/{id}")
    @Log("查询订单退货")
    @ApiOperation("查询订单退货")
    @PreAuthorize("@el.check('orderReturnApply:list')")
    public ResponseEntity<Object> getOrderReturnApplyInfo(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(orderReturnApplyService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增订单退货")
    @ApiOperation("新增订单退货")
    @PreAuthorize("@el.check('orderReturnApply:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody OrderReturnApply resources) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        resources.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(orderReturnApplyService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改订单退货")
    @ApiOperation("修改订单退货")
    @PreAuthorize("@el.check('orderReturnApply:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody OrderReturnApply resources) {
        resources.setHandleMan(SecurityUtils.getUsername());
        orderReturnApplyService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除订单退货")
    @ApiOperation("删除订单退货")
    @PreAuthorize("@el.check('orderReturnApply:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        orderReturnApplyService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
