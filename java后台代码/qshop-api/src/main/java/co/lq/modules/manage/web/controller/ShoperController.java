package co.lq.modules.manage.web.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import co.lq.annotation.AnonymousAccess;
import co.lq.common.api.ApiResult;
import co.lq.common.web.controller.BaseController;
import co.lq.modules.manage.service.ExpressService;
import co.lq.modules.manage.web.dto.OrderTimeDataDTO;
import co.lq.modules.manage.web.param.OrderDeliveryParam;
import co.lq.modules.manage.web.param.OrderPriceParam;
import co.lq.modules.manage.web.param.OrderRefundParam;
import co.lq.modules.manage.web.param.ShoperQueryParam;
import co.lq.modules.order.service.StoreOrderService;
import co.lq.modules.order.web.dto.OrderCountDTO;
import co.lq.modules.order.web.vo.StoreOrderQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName ShoperController
 * @Author billy
 * @Date 2019/11/25
 **/
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "商家管理", tags = "商城:商家管理", description = "商家管理")
public class ShoperController extends BaseController {

    private final StoreOrderService storeOrderService;
    private final ExpressService    expressService;

    /**
     * 订单数据统计
     */
    @GetMapping("/admin/order/statistics")
    @ApiOperation(value = "订单数据统计", notes = "订单数据统计")
    public ApiResult statistics() {
        OrderCountDTO orderCountDTO = storeOrderService.orderData(0);
        OrderTimeDataDTO orderTimeDataDTO = storeOrderService.getOrderTimeData();

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("orderCount", orderCountDTO);
        map.put("orderTimeCount", orderTimeDataDTO);
        return ApiResult.ok(map);
    }

    /**
     * 订单每月统计数据
     */
    @GetMapping("/admin/order/data")
    @ApiOperation(value = "订单每月统计数据", notes = "订单每月统计数据")
    public ApiResult data(ShoperQueryParam queryParam) {
        return ApiResult.ok(storeOrderService.getOrderDataPriceCount(queryParam.getPage(), queryParam.getLimit()));
    }

    /**
     * 订单列表
     */
    @GetMapping("/admin/order/list")
    @ApiOperation(value = "订单列表", notes = "订单列表")
    public ApiResult orderList(ShoperQueryParam queryParam) {
        List<StoreOrderQueryVo> orderList = storeOrderService.orderList(0, queryParam.getStatus(), queryParam.getPage(),
                queryParam.getLimit());
        //        List<StoreOrderQueryVo> orderList1 = orderList.stream().map(order -> {
        //            List<StoreOrderQueryVo> children = storeOrderService.orderList(0, queryParam.getStatus(), 0, 200);
        //            order.setChildren(children);
        //            return order;
        //        }).collect(Collectors.toList());
        return ApiResult.ok(orderList);
    }

    /**
     * 订单详情
     */
    @GetMapping("/admin/order/detail/{key}")
    @ApiOperation(value = "订单详情", notes = "订单详情")
    public ApiResult orderDetail(@PathVariable String key) {
        if (StrUtil.isEmpty(key)) {
            return ApiResult.fail("参数错误");
        }
        StoreOrderQueryVo storeOrder = storeOrderService.getOrderInfo(key, 0);
        if (ObjectUtil.isNull(storeOrder)) {
            return ApiResult.fail("订单不存在");
        }

        return ApiResult.ok(storeOrderService.handleOrder(storeOrder));
    }

    /**
     * 订单改价
     */
    @PostMapping("/admin/order/price")
    @ApiOperation(value = "订单改价", notes = "订单改价")
    public ApiResult orderPrice(@Validated @RequestBody OrderPriceParam param) {
        storeOrderService.editOrderPrice(param);
        return ApiResult.ok("ok");
    }

    /**
     * 快递公司
     */
    @AnonymousAccess
    @GetMapping("/logistics")
    @ApiOperation(value = "快递公司", notes = "快递公司")
    public ApiResult express() {
        return ApiResult.ok(expressService.list());
    }

    /**
     * 订单发货
     */
    @PostMapping("/admin/order/delivery/keep")
    @ApiOperation(value = "订单发货", notes = "订单发货")
    public ApiResult orderDelivery(@Validated @RequestBody OrderDeliveryParam param) {
        storeOrderService.orderDelivery(param);
        return ApiResult.ok("ok");
    }

    /**
     * 订单退款
     */
    @PostMapping("/admin/order/refund")
    @ApiOperation(value = "订单退款", notes = "订单退款")
    public ApiResult orderRefund(@Validated @RequestBody OrderRefundParam param) {
        storeOrderService.orderRefund(param);
        return ApiResult.ok("ok");
    }

    /**
     * 订单交易额/订单数量时间chart统计
     */
    @GetMapping("/admin/order/time")
    @ApiOperation(value = "chart统计", notes = "chart统计")
    public ApiResult chartCount(ShoperQueryParam queryParam) {
        return ApiResult.ok(storeOrderService.chartCount(queryParam.getCate(), queryParam.getType()));
    }

}
