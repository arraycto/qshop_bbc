package co.lq.modules.shop.rest;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Base64Utils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import co.lq.annotation.AnonymousAccess;
import co.lq.aop.log.Log;
import co.lq.constant.ShopConstants;
import co.lq.enums.OrderInfoEnum;
import co.lq.exception.BadRequestException;
import co.lq.express.config.ExpressProperties;
import co.lq.modules.activity.service.StorePinkService;
import co.lq.modules.activity.service.dto.StorePinkDTO;
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.modules.shop.domain.StoreOrder;
import co.lq.modules.shop.domain.StoreOrderStatus;
import co.lq.modules.shop.service.ExpressService;
import co.lq.modules.shop.service.StoreOrderService;
import co.lq.modules.shop.service.StoreOrderStatusService;
import co.lq.modules.shop.service.WechatUserService;
import co.lq.modules.shop.service.dto.ExpressDTO;
import co.lq.modules.shop.service.dto.StoreOrderDTO;
import co.lq.modules.shop.service.dto.StoreOrderQueryCriteria;
import co.lq.modules.shop.service.dto.WechatUserDTO;
import co.lq.modules.shop.service.param.ExpressInfoParam;
import co.lq.modules.shop.service.param.ExpressParam;
import co.lq.mp.service.TemplateService;
import co.lq.utils.OrderUtil;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author billy
 * @date 2019-10-14
 */
@Api(tags = "店铺:订单管理")
@RestController
@RequestMapping("api")
@Slf4j
public class OrderController {

    private final StoreOrderService             storeOrderService;
    private final StoreOrderStatusService       storeOrderStatusService;
    private final ExpressService                expressService;
    private final WechatUserService             wechatUserService;
    private final RedisTemplate<String, String> redisTemplate;
    private final TemplateService               templateService;
    private final StorePinkService              storePinkService;
    private final UserDetailsService            userDetailsService;
    private final ExpressProperties             properties;
    //请求url
    private String                              ReqURL = "http://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";

    public OrderController(StoreOrderService storeOrderService, StoreOrderStatusService storeOrderStatusService,
                           ExpressService expressService, WechatUserService wechatUserService,
                           RedisTemplate<String, String> redisTemplate, TemplateService templateService,
                           StorePinkService storePinkService, UserDetailsService userDetailsService,
                           ExpressProperties properties) {
        this.storeOrderService = storeOrderService;
        this.storeOrderStatusService = storeOrderStatusService;
        this.expressService = expressService;
        this.wechatUserService = wechatUserService;
        this.redisTemplate = redisTemplate;
        this.templateService = templateService;
        this.storePinkService = storePinkService;
        this.userDetailsService = userDetailsService;
        this.properties = properties;
    }

    @GetMapping(value = "/data/count")
    @AnonymousAccess
    public ResponseEntity getCount() {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        return new ResponseEntity<>(storeOrderService.getStoreOrderTimeData(jwtUser.getStoreId()), HttpStatus.OK);
    }

    @GetMapping(value = "/data/chart")
    @AnonymousAccess
    public ResponseEntity getChart() {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        return new ResponseEntity<>(storeOrderService.chartStoreCount(jwtUser.getStoreId()), HttpStatus.OK);
    }

    @ApiOperation(value = "查询订单")
    @GetMapping(value = "/storeOrder")
    @PreAuthorize("@el.check('admin','STOREORDER_ALL','STOREORDER_SELECT')")
    public ResponseEntity getStoreOrders(StoreOrderQueryCriteria criteria, Pageable pageable,
                                         @RequestParam(name = "orderStatus") String orderStatus,
                                         @RequestParam(name = "orderType") String orderType) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setShopId(jwtUser.getStoreId());
        //默认查询所有快递订单
        criteria.setShippingType(1);
        //订单状态查询
        if (StrUtil.isNotEmpty(orderStatus)) {
            switch (orderStatus) {
                case "0":
                    criteria.setIsDel(0);
                    criteria.setPaid(0);
                    criteria.setStatus(0);
                    criteria.setRefundStatus(0);
                    break;
                case "1":
                    criteria.setIsDel(0);
                    criteria.setPaid(1);
                    criteria.setStatus(0);
                    criteria.setRefundStatus(0);
                    break;
                case "2":
                    criteria.setIsDel(0);
                    criteria.setPaid(1);
                    criteria.setStatus(1);
                    criteria.setRefundStatus(0);
                    break;
                case "3":
                    criteria.setIsDel(0);
                    criteria.setPaid(1);
                    criteria.setStatus(2);
                    criteria.setRefundStatus(0);
                    break;
                case "4":
                    criteria.setIsDel(0);
                    criteria.setPaid(1);
                    criteria.setStatus(3);
                    criteria.setRefundStatus(0);
                    break;
                case "-1":
                    criteria.setIsDel(0);
                    criteria.setPaid(1);
                    criteria.setRefundStatus(1);
                    break;
                case "-2":
                    criteria.setIsDel(0);
                    criteria.setPaid(1);
                    criteria.setRefundStatus(2);
                    break;
                case "-4":
                    criteria.setIsDel(1);
                    break;
                default:
                    criteria.setIsDel(0);
                    //                    criteria.setPaid(0);
                    //                    criteria.setStatus(0);
                    //                    criteria.setRefundStatus(0);
                    break;
            }
        }
        //订单类型查询
        if (StrUtil.isNotEmpty(orderType)) {
            switch (orderType) {
                case "1":
                    criteria.setBargainId(0L);
                    criteria.setCombinationId(0L);
                    criteria.setSeckillId(0L);
                    break;
                case "2":
                    criteria.setNewCombinationId(0);
                    break;
                case "3":
                    criteria.setNewSeckillId(0L);
                    break;
                case "4":
                    criteria.setNewBargainId(0L);
                    break;
                case "5":
                    criteria.setShippingType(2);
                    break;
                default:
                    //                    criteria.setBargainId(0L);
                    //                    criteria.setCombinationId(0L);
                    //                    criteria.setSeckillId(0L);
                    break;
            }
        }

        return new ResponseEntity<>(storeOrderService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "发货")
    @PutMapping(value = "/storeOrder")
    @PreAuthorize("@el.check('admin','STOREORDER_ALL','STOREORDER_EDIT')")
    public ResponseEntity update(@Validated @RequestBody StoreOrder resources) {
        if (StrUtil.isBlank(resources.getDeliveryName())) {
            throw new BadRequestException("请选择快递公司");
        }
        if (StrUtil.isBlank(resources.getDeliveryId())) {
            throw new BadRequestException("快递单号不能为空");
        }
        ExpressDTO expressDTO = expressService.findById(Integer.valueOf(resources.getDeliveryName()));
        if (ObjectUtil.isNull(expressDTO)) {
            throw new BadRequestException("请先添加快递公司");
        }
        resources.setStatus(1);
        resources.setDeliveryType("express");
        resources.setDeliveryName(expressDTO.getName());
        resources.setDeliverySn(expressDTO.getCode());

        storeOrderService.update(resources);

        StoreOrderStatus storeOrderStatus = new StoreOrderStatus();
        storeOrderStatus.setOid(resources.getId());
        storeOrderStatus.setChangeType("delivery_goods");
        storeOrderStatus
                .setChangeMessage("已发货 快递公司：" + resources.getDeliveryName() + " 快递单号：" + resources.getDeliveryId());
        storeOrderStatus.setChangeTime(OrderUtil.getSecondTimestampTwo());

        storeOrderStatusService.create(storeOrderStatus);

        //模板消息通知
        try {
            WechatUserDTO wechatUser = wechatUserService.findById(resources.getUid());
            if (ObjectUtil.isNotNull(wechatUser)) {
                if (StrUtil.isNotBlank(wechatUser.getOpenid())) {
                    templateService.deliverySuccessNotice(resources.getOrderId(), expressDTO.getName(),
                            resources.getDeliveryId(), wechatUser.getOpenid());
                } else if (StrUtil.isNotBlank(wechatUser.getRoutineOpenid())) {
                    //todo 小程序通知
                }
            }
        } catch (Exception e) {
            log.info("当前用户不是微信用户不能发送模板消息哦!");
        }

        //加入redis，7天后自动确认收货
        String redisKey = String
                .valueOf(StrUtil.format("{}{}", ShopConstants.REDIS_ORDER_OUTTIME_UNCONFIRM, resources.getId()));
        redisTemplate.opsForValue().set(redisKey, resources.getOrderId(), ShopConstants.ORDER_OUTTIME_UNCONFIRM,
                TimeUnit.DAYS);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "订单核销")
    @PutMapping(value = "/storeOrder/check")
    @PreAuthorize("@el.check('admin','STOREORDER_ALL','STOREORDER_EDIT')")
    public ResponseEntity check(@Validated @RequestBody StoreOrder resources) {
        if (StrUtil.isBlank(resources.getVerifyCode())) {
            throw new BadRequestException("核销码不能为空");
        }
        StoreOrderDTO storeOrderDTO = storeOrderService.findById(resources.getId());
        if (!resources.getVerifyCode().equals(storeOrderDTO.getVerifyCode())) {
            throw new BadRequestException("核销码不对");
        }
        if (OrderInfoEnum.PAY_STATUS_0.getValue().equals(storeOrderDTO.getPaid())) {
            throw new BadRequestException("订单未支付");
        }
        if (storeOrderDTO.getStatus() > 0) {
            throw new BadRequestException("订单已核销");
        }

        if (storeOrderDTO.getCombinationId() > 0 && storeOrderDTO.getPinkId() > 0) {
            StorePinkDTO storePinkDTO = storePinkService.findById(storeOrderDTO.getPinkId());
            if (!OrderInfoEnum.PINK_STATUS_2.getValue().equals(storePinkDTO.getStatus())) {
                throw new BadRequestException("拼团订单暂未成功无法核销");
            }
        }

        resources.setStatus(OrderInfoEnum.STATUS_2.getValue());
        storeOrderService.update(resources);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "退款")
    @PostMapping(value = "/storeOrder/refund")
    @PreAuthorize("@el.check('admin','STOREORDER_ALL','STOREORDER_EDIT')")
    public ResponseEntity refund(@Validated @RequestBody StoreOrder resources) {
        storeOrderService.refund(resources);

        //模板消息通知
        try {
            WechatUserDTO wechatUser = wechatUserService.findById(resources.getUid());
            if (ObjectUtil.isNotNull(wechatUser)) {
                if (StrUtil.isNotBlank(wechatUser.getOpenid())) {
                    templateService.refundSuccessNotice(resources.getOrderId(), resources.getPayPrice().toString(),
                            wechatUser.getOpenid(), OrderUtil.stampToDate(resources.getAddTime().toString()));
                } else if (StrUtil.isNotBlank(wechatUser.getRoutineOpenid())) {
                    //todo 小程序通知
                }
            }
        } catch (Exception e) {
            log.info("当前用户不是微信用户不能发送模板消息哦!");
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "/storeOrder/{id}")
    @PreAuthorize("@el.check('admin','STOREORDER_ALL','STOREORDER_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        storeOrderService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("修改订单")
    @ApiOperation(value = "修改订单")
    @PostMapping(value = "/storeOrder/edit")
    @PreAuthorize("hasAnyRole('admin','STOREORDER_ALL','STOREORDER_EDIT')")
    public ResponseEntity editOrder(@RequestBody StoreOrder resources) {
        if (ObjectUtil.isNull(resources.getPayPrice())) {
            throw new BadRequestException("请输入支付金额");
        }
        if (resources.getPayPrice().doubleValue() < 0) {
            throw new BadRequestException("金额不能低于0");
        }

        StoreOrderDTO storeOrder = storeOrderService.findById(resources.getId());
        //判断金额是否有变动,生成一个额外订单号去支付

        int res = NumberUtil.compare(storeOrder.getPayPrice().doubleValue(), resources.getPayPrice().doubleValue());
        if (res != 0) {
            String orderSn = IdUtil.getSnowflake(0, 0).nextIdStr();
            resources.setExtendOrderId(orderSn);
        }

        storeOrderService.update(resources);

        StoreOrderStatus storeOrderStatus = new StoreOrderStatus();
        storeOrderStatus.setOid(resources.getId());
        storeOrderStatus.setChangeType("order_edit");
        storeOrderStatus.setChangeMessage("修改订单价格为：" + resources.getPayPrice());
        storeOrderStatus.setChangeTime(OrderUtil.getSecondTimestampTwo());

        storeOrderStatusService.create(storeOrderStatus);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("修改订单备注")
    @ApiOperation(value = "修改订单备注")
    @PostMapping(value = "/storeOrder/remark")
    @PreAuthorize("hasAnyRole('admin','STOREORDER_ALL','STOREORDER_EDIT')")
    public ResponseEntity editOrderRemark(@RequestBody StoreOrder resources) {
        if (StrUtil.isBlank(resources.getRemark())) {
            throw new BadRequestException("请输入备注");
        }
        storeOrderService.update(resources);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * @Valid 获取物流信息,根据传的订单编号 ShipperCode快递公司编号 和物流单号,
     */
    @PostMapping("/storeOrder/express")
    @ApiOperation(value = "获取物流信息", notes = "获取物流信息", response = ExpressParam.class)
    public ResponseEntity express(@RequestBody ExpressParam expressInfoDo) {
        ExpressInfoParam expressInfo = getExpressInfo(expressInfoDo.getOrderCode(), expressInfoDo.getShipperCode(),
                expressInfoDo.getLogisticCode());
        if (!expressInfo.isSuccess()) {
            return new ResponseEntity<>(expressInfo.getReason(), HttpStatus.OK);
        }
        return new ResponseEntity<>(expressInfo, HttpStatus.OK);
    }

    private ExpressInfoParam getExpressInfo(String OrderCode, String ShipperCode, String LogisticCode) {
        try {
            String result = getOrderTracesByJson(OrderCode, ShipperCode, LogisticCode);
            ObjectMapper objMap = new ObjectMapper();
            ExpressInfoParam ei = objMap.readValue(result, ExpressInfoParam.class);
            ei.setShipperName(getVendorName(ShipperCode));
            return ei;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * Json方式 查询订单物流轨迹
     *
     * @throws Exception
     */
    private String getOrderTracesByJson(String OrderCode, String ShipperCode, String LogisticCode) throws Exception {
        if (!properties.isEnable()) {
            return null;
        }

        String requestData = "{'OrderCode':'" + OrderCode + "','ShipperCode':'" + ShipperCode + "','LogisticCode':'"
                + LogisticCode + "'}";

        Map<String, Object> params = new HashMap<>();
        params.put("RequestData", URLEncoder.encode(requestData, "UTF-8"));
        params.put("EBusinessID", properties.getAppId());
        params.put("RequestType", "1002");
        String dataSign = encrypt(requestData, properties.getAppKey(), "UTF-8");
        params.put("DataSign", URLEncoder.encode(dataSign, "UTF-8"));
        params.put("DataType", "2");

        String result = HttpUtil.post(ReqURL, params);

        //根据公司业务处理返回的信息......

        return result;
    }

    /**
     * 获取物流供应商名
     *
     * @param vendorCode
     * @return
     */
    public String getVendorName(String vendorCode) {
        for (Map<String, String> item : properties.getVendors()) {
            if (item.get("code").equals(vendorCode))
                return item.get("name");
        }
        return null;
    }

    /**
     * Sign签名生成
     *
     * @param content 内容
     * @param keyValue Appkey
     * @param charset 编码方式
     * @return DataSign签名
     */
    private String encrypt(String content, String keyValue, String charset) {
        if (keyValue != null) {
            content = content + keyValue;
        }
        byte[] src;
        try {
            src = MD5(content, charset).getBytes(charset);
            return Base64Utils.encodeToString(src);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * MD5加密
     *
     * @param str 内容
     * @param charset 编码方式
     * @throws Exception
     */
    private String MD5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuilder sb = new StringBuilder(32);
        for (int i = 0; i < result.length; i++) {
            int val = result[i] & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }
}
