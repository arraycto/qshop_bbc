package co.lq.modules.shop.web.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import co.lq.aop.log.Log;
import co.lq.common.api.ApiResult;
import co.lq.common.web.controller.BaseController;
import co.lq.common.web.param.IdParam;
import co.lq.common.web.vo.Paging;
import co.lq.modules.order.entity.StoreOrder;
import co.lq.modules.order.entity.StoreOrderCartInfo;
import co.lq.modules.order.service.StoreOrderCartInfoService;
import co.lq.modules.order.service.StoreOrderService;
import co.lq.modules.shop.entity.CompanyAddress;
import co.lq.modules.shop.entity.OrderReturnApply;
import co.lq.modules.shop.param.OrderReturnApplyItem;
import co.lq.modules.shop.service.ApiOrderReturnApplyService;
import co.lq.modules.shop.service.CompanyAddressService;
import co.lq.modules.shop.web.param.OrderReturnApplyQueryParam;
import co.lq.modules.shop.web.vo.OrderReturnApplyQueryVo;
import co.lq.modules.shop.web.vo.OrderReturnApplyVo;
import co.lq.modules.shop.web.vo.StoreCartQueryVo;
import co.lq.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 订单退货申请 前端控制器
 * </p>
 *
 * @author billy
 * @since 2020-04-04
 */
@Slf4j
@RestController
@RequestMapping("/orderReturnApply")
@Api("订单退货申请 API")
public class OrderReturnApplyController extends BaseController {

    @Autowired
    private ApiOrderReturnApplyService apiOrderReturnApplyService;

    @Autowired
    private StoreOrderService          orderService;

    @Autowired
    private StoreOrderCartInfoService  orderCartInfoService;

    @Autowired
    private CompanyAddressService      companyAddressService;

    /**
     * 添加订单退货申请
     */
    @Log(value = "添加订单退货申请", type = 1)
    @PostMapping("/add")
    @ApiOperation(value = "添加OrderReturnApply对象", notes = "添加订单退货申请", response = ApiResult.class)
    public ApiResult addOrderReturnApply(@Valid @RequestBody OrderReturnApplyVo orderReturnApplyVo) throws Exception {
        OrderReturnApply orderReturnApply = new OrderReturnApply();
        QueryWrapper<StoreOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", orderReturnApplyVo.getOrderId());
        final StoreOrder storeOrder = orderService.getOne(wrapper);
        QueryWrapper<CompanyAddress> wrapperCompanyAddress = new QueryWrapper<>();
        wrapperCompanyAddress.eq("store_id", storeOrder.getShopId()).eq("deleted", 0).eq("receive_status", 1);
        CompanyAddress companyAddress = companyAddressService.getOne(wrapperCompanyAddress);
        orderReturnApply.setOrderId(orderReturnApplyVo.getOrderId());
        orderReturnApply.setOid(storeOrder.getId());
        orderReturnApply.setMemberUsername(storeOrder.getRealName());
        orderReturnApply.setReturnType(orderReturnApplyVo.getType());
        orderReturnApply.setOrderAmount(storeOrder.getTotalPrice());
        orderReturnApply.setReturnAmount(BigDecimal.valueOf(Double.valueOf(orderReturnApplyVo.getReturnAmount())));
        orderReturnApply.setDescription(orderReturnApplyVo.getDesc());
        orderReturnApply.setStatus(0);
        if (companyAddress.getId() != null) {
            orderReturnApply.setAddressId(companyAddress.getId());
        }
        QueryWrapper<StoreOrderCartInfo> wrapper1 = new QueryWrapper<>();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        final Integer[] index = { 0 };
        orderReturnApplyVo.getItems().forEach(item -> {
            wrapper1.eq("cart_id", Long.parseLong(item));
            StoreOrderCartInfo cartInfo = orderCartInfoService.getOne(wrapper1);
            OrderReturnApplyItem orderReturnApplyItem = new OrderReturnApplyItem();
            orderReturnApplyItem.setProductId(cartInfo.getProductId());
            StoreCartQueryVo cartQueryVo = JSON.parseObject(cartInfo.getCartInfo(), StoreCartQueryVo.class);
            orderReturnApplyItem.setProductPic(cartQueryVo.getProductInfo().getImage());
            orderReturnApplyItem.setProductName(cartQueryVo.getProductInfo().getProductName());
            if (cartQueryVo.getProductInfo().getAttrInfo() != null
                    && cartQueryVo.getProductInfo().getAttrInfo().getSuk() != null) {
                orderReturnApplyItem.setProductAttr(cartQueryVo.getProductInfo().getAttrInfo().getSuk());
            }
            if (orderReturnApplyVo.getItemCounts() != null && orderReturnApplyVo.getItemCounts().size() > 0) {
                orderReturnApplyItem.setProductCount(orderReturnApplyVo.getItemCounts().get(index[0]));
            } else {
                orderReturnApplyItem.setProductCount(cartQueryVo.getCartNum());
            }
            orderReturnApplyItem.setProductPrice(BigDecimal.valueOf(cartQueryVo.getCostPrice()));
            orderReturnApplyItem.setProductRealPrice(BigDecimal.valueOf(cartQueryVo.getTruePrice()));

            orderReturnApply.setCartInfo(JSONObject.toJSONString(orderReturnApplyItem));
            index[0]++;
        });
        orderReturnApply.setProofPics(StringUtils.join(orderReturnApplyVo.getImages()));
        orderReturnApply.setAddTime(timestamp);
        orderReturnApply.setModifyTime(timestamp);

        boolean flag = apiOrderReturnApplyService.save(orderReturnApply);
        return ApiResult.result(flag);
    }

    /**
     * 修改订单退货申请
     */
    @Log(value = "修改订单退货申请", type = 1)
    @PostMapping("/update")
    @ApiOperation(value = "修改OrderReturnApply对象", notes = "修改订单退货申请", response = ApiResult.class)
    public ApiResult<Boolean> updateOrderReturnApply(@Valid @RequestBody OrderReturnApply orderReturnApply)
            throws Exception {
        boolean flag = apiOrderReturnApplyService.updateById(orderReturnApply);
        return ApiResult.result(flag);
    }

    /**
     * 删除订单退货申请
     */
    @Log(value = "删除订单退货申请", type = 1)
    @PostMapping("/delete")
    @ApiOperation(value = "删除OrderReturnApply对象", notes = "删除订单退货申请", response = ApiResult.class)
    public ApiResult<Boolean> deleteOrderReturnApply(@Valid @RequestBody IdParam idParam) throws Exception {
        boolean flag = apiOrderReturnApplyService.removeById(idParam.getId());
        return ApiResult.result(flag);
    }

    /**
     * 获取订单退货申请
     */
    @PostMapping("/info")
    @ApiOperation(value = "获取OrderReturnApply对象详情", notes = "查看订单退货申请", response = OrderReturnApplyQueryVo.class)
    public ApiResult getOrderReturnApply(@Valid @RequestBody IdParam idParam) throws Exception {
        OrderReturnApplyQueryVo orderReturnApplyQueryVo = apiOrderReturnApplyService
                .getOrderReturnApplyById(idParam.getId());
        return ApiResult.ok(orderReturnApplyQueryVo);
    }

    /**
     * 订单退货申请分页列表
     */
    @PostMapping("/getPageList")
    @ApiOperation(value = "获取OrderReturnApply分页列表", notes = "订单退货申请分页列表", response = OrderReturnApplyQueryVo.class)
    public ApiResult<Paging<OrderReturnApplyQueryVo>> getOrderReturnApplyPageList(@Valid @RequestBody(required = false) OrderReturnApplyQueryParam orderReturnApplyQueryParam)
            throws Exception {
        Paging<OrderReturnApplyQueryVo> paging = apiOrderReturnApplyService
                .getOrderReturnApplyPageList(orderReturnApplyQueryParam);
        return ApiResult.ok(paging);
    }

}
