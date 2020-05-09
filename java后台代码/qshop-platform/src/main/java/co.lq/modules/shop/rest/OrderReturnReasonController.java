package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.OrderReturnReason;
import co.lq.modules.shop.service.OrderReturnReasonService;
import co.lq.modules.shop.service.dto.OrderReturnReasonQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author billy
* @date 2020-03-27
*/
@Api(tags = "订单退货原因管理")
@RestController
@RequestMapping("/api/orderReturnReason")
public class OrderReturnReasonController {

    private final OrderReturnReasonService orderReturnReasonService;

    public OrderReturnReasonController(OrderReturnReasonService orderReturnReasonService) {
        this.orderReturnReasonService = orderReturnReasonService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('orderReturnReason:list')")
    public void download(HttpServletResponse response, OrderReturnReasonQueryCriteria criteria) throws IOException {
        orderReturnReasonService.download(orderReturnReasonService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询订单退货原因")
    @ApiOperation("查询订单退货原因")
    @PreAuthorize("@el.check('orderReturnReason:list')")
    public ResponseEntity<Object> getOrderReturnReasons(OrderReturnReasonQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(orderReturnReasonService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增订单退货原因")
    @ApiOperation("新增订单退货原因")
    @PreAuthorize("@el.check('orderReturnReason:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody OrderReturnReason resources){
        return new ResponseEntity<>(orderReturnReasonService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改订单退货原因")
    @ApiOperation("修改订单退货原因")
    @PreAuthorize("@el.check('orderReturnReason:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody OrderReturnReason resources){
        orderReturnReasonService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除订单退货原因")
    @ApiOperation("删除订单退货原因")
    @PreAuthorize("@el.check('orderReturnReason:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        orderReturnReasonService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}