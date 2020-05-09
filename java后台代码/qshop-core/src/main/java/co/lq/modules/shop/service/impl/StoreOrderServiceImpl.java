package co.lq.modules.shop.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.exception.WxPayException;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import co.lq.enums.OrderInfoEnum;
import co.lq.exception.BadRequestException;
import co.lq.exception.EntityExistException;
import co.lq.modules.activity.domain.StorePink;
import co.lq.modules.activity.repository.StorePinkRepository;
import co.lq.modules.shop.domain.Shop;
import co.lq.modules.shop.domain.StoreOrder;
import co.lq.modules.shop.domain.StoreOrderCartInfo;
import co.lq.modules.shop.domain.StoreOrderStatus;
import co.lq.modules.shop.domain.UserBill;
import co.lq.modules.shop.repository.StoreOrderCartInfoRepository;
import co.lq.modules.shop.repository.StoreOrderRepository;
import co.lq.modules.shop.repository.StoreRepository;
import co.lq.modules.shop.repository.UserRepository;
import co.lq.modules.shop.service.StoreOrderService;
import co.lq.modules.shop.service.StoreOrderStatusService;
import co.lq.modules.shop.service.UserBillService;
import co.lq.modules.shop.service.UserService;
import co.lq.modules.shop.service.dto.OrderTimeDataDTO;
import co.lq.modules.shop.service.dto.StoreOrderCartInfoDTO;
import co.lq.modules.shop.service.dto.StoreOrderDTO;
import co.lq.modules.shop.service.dto.StoreOrderQueryCriteria;
import co.lq.modules.shop.service.dto.StoreOrderTimeDataDTO;
import co.lq.modules.shop.service.dto.UserDTO;
import co.lq.modules.shop.service.mapper.StoreOrderMapper;
import co.lq.mp.service.MiniPayService;
import co.lq.mp.service.PayService;
import co.lq.utils.OrderUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author billy
 * @date 2019-10-14
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StoreOrderServiceImpl implements StoreOrderService {

    private final StoreOrderRepository         storeOrderRepository;
    private final StoreOrderCartInfoRepository storeOrderCartInfoRepository;
    private final UserRepository               userRepository;
    private final StorePinkRepository          storePinkRepository;

    private final StoreOrderMapper             storeOrderMapper;
    private final StoreRepository              storeRepository;

    private final UserBillService              userBillService;
    private final StoreOrderStatusService      storeOrderStatusService;
    private final UserService                  userService;
    private final PayService                   payService;
    private final MiniPayService               miniPayService;

    public StoreOrderServiceImpl(StoreOrderRepository storeOrderRepository,
                                 StoreOrderCartInfoRepository storeOrderCartInfoRepository,
                                 UserRepository userRepository, StorePinkRepository storePinkRepository,
                                 StoreOrderMapper storeOrderMapper, StoreRepository storeRepository,
                                 UserBillService userBillService, StoreOrderStatusService storeOrderStatusService,
                                 UserService userService, PayService payService, MiniPayService miniPayService) {
        this.storeOrderRepository = storeOrderRepository;
        this.storeOrderCartInfoRepository = storeOrderCartInfoRepository;
        this.userRepository = userRepository;
        this.storePinkRepository = storePinkRepository;
        this.storeOrderMapper = storeOrderMapper;
        this.storeRepository = storeRepository;
        this.userBillService = userBillService;
        this.storeOrderStatusService = storeOrderStatusService;
        this.userService = userService;
        this.payService = payService;
        this.miniPayService = miniPayService;
    }

    @Override
    public OrderTimeDataDTO getOrderTimeData() {
        int today = OrderUtil.dateToTimestampT(DateUtil.beginOfDay(new Date()));
        int yesterday = OrderUtil.dateToTimestampT(DateUtil.beginOfDay(DateUtil.yesterday()));
        int lastWeek = OrderUtil.dateToTimestampT(DateUtil.beginOfDay(DateUtil.lastWeek()));
        int nowMonth = OrderUtil.dateToTimestampT(DateUtil.beginOfMonth(new Date()));
        OrderTimeDataDTO orderTimeDataDTO = new OrderTimeDataDTO();

        orderTimeDataDTO.setTodayCount(storeOrderRepository.countByPayTimeGreaterThanEqual(today));
        orderTimeDataDTO.setTodayPrice(storeOrderRepository.sumPrice(today));

        orderTimeDataDTO
                .setProCount(storeOrderRepository.countByPayTimeLessThanAndPayTimeGreaterThanEqual(today, yesterday));
        orderTimeDataDTO.setProPrice(storeOrderRepository.sumTPrice(today, yesterday));

        orderTimeDataDTO.setLastWeekCount(storeOrderRepository.countByPayTimeGreaterThanEqual(lastWeek));
        orderTimeDataDTO.setLastWeekPrice(storeOrderRepository.sumPrice(lastWeek));

        orderTimeDataDTO.setMonthCount(storeOrderRepository.countByPayTimeGreaterThanEqual(nowMonth));
        orderTimeDataDTO.setMonthPrice(storeOrderRepository.sumPrice(nowMonth));

        return orderTimeDataDTO;
    }

    @Override
    public OrderTimeDataDTO getStoreOrderTimeData(Long shopId) {
        int today = OrderUtil.dateToTimestampT(DateUtil.beginOfDay(new Date()));
        int yesterday = OrderUtil.dateToTimestampT(DateUtil.beginOfDay(DateUtil.yesterday()));
        int lastWeek = OrderUtil.dateToTimestampT(DateUtil.beginOfDay(DateUtil.lastWeek()));
        int nowMonth = OrderUtil.dateToTimestampT(DateUtil.beginOfMonth(new Date()));
        OrderTimeDataDTO orderTimeDataDTO = new OrderTimeDataDTO();

        orderTimeDataDTO.setTodayCount(storeOrderRepository.countByShopIdAndPayTimeGreaterThanEqual(shopId, today));
        orderTimeDataDTO.setTodayPrice(storeOrderRepository.sumStorePrice(shopId, today));

        orderTimeDataDTO.setProCount(storeOrderRepository
                .countByShopIdAndPayTimeLessThanAndPayTimeGreaterThanEqual(shopId, today, yesterday));
        orderTimeDataDTO.setProPrice(storeOrderRepository.sumStoreTPrice(shopId, today, yesterday));

        orderTimeDataDTO
                .setLastWeekCount(storeOrderRepository.countByShopIdAndPayTimeGreaterThanEqual(shopId, lastWeek));
        orderTimeDataDTO.setLastWeekPrice(storeOrderRepository.sumStorePrice(shopId, lastWeek));

        orderTimeDataDTO.setMonthCount(storeOrderRepository.countByShopIdAndPayTimeGreaterThanEqual(shopId, nowMonth));
        orderTimeDataDTO.setMonthPrice(storeOrderRepository.sumStorePrice(shopId, nowMonth));

        return orderTimeDataDTO;
    }

    @Override
    public Map<String, Object> chartCount(Date time, Long storeId) {
        Map<String, Object> map = new LinkedHashMap<>();
        int nowMonth = 0;
        if (time == null) {
            nowMonth = OrderUtil.dateToTimestampT(DateUtil.beginOfMonth(new Date()));
        } else {
            nowMonth = OrderUtil.dateToTimestampT(DateUtil.beginOfMonth(time));
        }

        if (storeId == null) {
            map.put("chart", storeOrderRepository.chartList(nowMonth));
            map.put("chartT", storeOrderRepository.chartListT(nowMonth));
        } else {
            map.put("chart", storeOrderRepository.storeChartList(storeId, nowMonth));
            map.put("chartT", storeOrderRepository.storeChartListT(storeId, nowMonth));
        }

        return map;
    }

    @Override
    public Map<String, Object> chartStoreCount(Long shopId) {
        Map<String, Object> map = new LinkedHashMap<>();
        int nowMonth = OrderUtil.dateToTimestampT(DateUtil.beginOfMonth(new Date()));

        map.put("chart", storeOrderRepository.storeChartList(shopId, nowMonth));
        map.put("chartT", storeOrderRepository.storeChartListT(shopId, nowMonth));

        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refund(StoreOrder resources) {
        if (resources.getPayPrice().doubleValue() < 0) {
            throw new BadRequestException("请输入退款金额");
        }

        if (resources.getPayType().equals("yue")) {
            //修改状态
            resources.setRefundStatus(2);
            resources.setRefundPrice(resources.getPayPrice());
            update(resources);

            //退款到余额
            UserDTO userDTO = userService.findById(resources.getUid());
            userRepository.updateMoney(resources.getPayPrice().doubleValue(), resources.getUid());

            UserBill userBill = new UserBill();
            userBill.setUid(resources.getUid());

            userBill.setLinkId(resources.getId().toString());
            userBill.setPm(1);
            userBill.setTitle("商品退款");
            userBill.setCategory("now_money");
            userBill.setType("pay_product_refund");
            userBill.setNumber(resources.getPayPrice());
            userBill.setBalance(NumberUtil.add(resources.getPayPrice(), userDTO.getNowMoney()));
            userBill.setMark("订单退款到余额");
            userBill.setAddTime(OrderUtil.getSecondTimestampTwo());
            userBill.setStatus(1);
            userBillService.create(userBill);

            StoreOrderStatus storeOrderStatus = new StoreOrderStatus();
            storeOrderStatus.setOid(resources.getId());
            storeOrderStatus.setChangeType("refund_price");
            storeOrderStatus.setChangeMessage("退款给用户：" + resources.getPayPrice() + "元");
            storeOrderStatus.setChangeTime(OrderUtil.getSecondTimestampTwo());

            storeOrderStatusService.create(storeOrderStatus);
        } else {
            BigDecimal bigDecimal = new BigDecimal("100");
            try {
                if (OrderInfoEnum.PAY_CHANNEL_1.getValue().equals(resources.getIsChannel())) {
                    miniPayService.refundOrder(resources.getOrderId(),
                            bigDecimal.multiply(resources.getPayPrice()).intValue());
                } else {
                    payService.refundOrder(resources.getOrderId(),
                            bigDecimal.multiply(resources.getPayPrice()).intValue());
                }

            } catch (WxPayException e) {
                log.info("refund-error:{}", e.getMessage());
            }

        }

    }

    @Override
    public String orderType(long id, long pinkId, long combinationId, long seckillId, long bargainId,
                            int shippingType) {
        String str = "[普通订单]";
        if (pinkId > 0 || combinationId > 0) {
            StorePink storePink = storePinkRepository.findByOrderIdKey(id);
            if (ObjectUtil.isNull(storePink)) {
                str = "[拼团订单]";
            } else {
                switch (storePink.getStatus()) {
                    case 1:
                        str = "[拼团订单]正在进行中";
                        break;
                    case 2:
                        str = "[拼团订单]已完成";
                        break;
                    case 3:
                        str = "[拼团订单]未完成";
                        break;
                    default:
                        str = "[拼团订单]历史订单";
                        break;
                }
            }

        } else if (seckillId > 0) {
            str = "[秒杀订单]";
        } else if (bargainId > 0) {
            str = "[砍价订单]";
        }
        if (shippingType == 2) {
            str = "[核销订单]";
        }
        return str;
    }

    @Override
    public Map<String, Object> queryAll(StoreOrderQueryCriteria criteria, Pageable pageable) {

        criteria.setHidden(0);
        Page<StoreOrder> page = storeOrderRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        List<StoreOrderDTO> storeOrderDTOS = new ArrayList<>();
        for (StoreOrder storeOrder : page.getContent()) {
            StoreOrderDTO storeOrderDTO = storeOrderMapper.toDto(storeOrder);

            Integer _status = OrderUtil.orderStatus(storeOrder.getPaid(), storeOrder.getStatus(),
                    storeOrder.getRefundStatus());
            Optional<Shop> optionalShop = storeRepository.findById(storeOrder.getShopId());
            Shop shop = optionalShop.get();
            if (shop != null) {
                storeOrderDTO.setShopName(shop.getName());
                storeOrderDTO.setContactName(shop.getContactName());
                storeOrderDTO.setContactMobile(shop.getContactMobile());
            }

            //订单状态
            String orderStatusStr = OrderUtil.orderStatusStr(storeOrder.getPaid(), storeOrder.getStatus(),
                    storeOrder.getShippingType(), storeOrder.getRefundStatus());
            if (_status == 3) {
                String refundTime = OrderUtil.stampToDate(String.valueOf(storeOrder.getRefundReasonTime()));
                String str = "<b style='color:#f124c7'>申请退款</b><br/>" + "<span>退款原因：" + storeOrder.getRefundReasonWap()
                        + "</span><br/>" + "<span>备注说明：" + storeOrder.getRefundReasonWapExplain() + "</span><br/>"
                        + "<span>退款时间：" + refundTime + "</span><br/>";
                orderStatusStr = str;
            }
            storeOrderDTO.setStatusName(orderStatusStr);

            storeOrderDTO.set_status(_status);

            String payTypeName = OrderUtil.payTypeName(storeOrder.getPayType(), storeOrder.getPaid());
            storeOrderDTO.setPayTypeName(payTypeName);

            storeOrderDTO
                    .setPinkName(orderType(storeOrder.getId(), storeOrder.getPinkId(), storeOrder.getCombinationId(),
                            storeOrder.getSeckillId(), storeOrder.getBargainId(), storeOrder.getShippingType()));

            List<StoreOrderCartInfo> cartInfos = storeOrderCartInfoRepository.findByOid(storeOrder.getId());
            List<StoreOrderCartInfoDTO> cartInfoDTOS = new ArrayList<>();
            for (StoreOrderCartInfo cartInfo : cartInfos) {
                StoreOrderCartInfoDTO cartInfoDTO = new StoreOrderCartInfoDTO();
                cartInfoDTO.setCartInfoMap(JSON.parseObject(cartInfo.getCartInfo()));

                cartInfoDTOS.add(cartInfoDTO);
            }
            storeOrderDTO.setCartInfoList(cartInfoDTOS);

            storeOrderDTOS.add(storeOrderDTO);

        }

        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", storeOrderDTOS);
        map.put("totalElements", page.getTotalElements());

        return map;
    }

    @Override
    public List<StoreOrderDTO> queryAll(StoreOrderQueryCriteria criteria) {
        criteria.setHidden(0);
        return storeOrderMapper.toDto(storeOrderRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public StoreOrderDTO findById(Long id) {
        Optional<StoreOrder> storeOrder = storeOrderRepository.findById(id);
        ValidationUtil.isNull(storeOrder, "StoreOrder", "id", id);
        return storeOrderMapper.toDto(storeOrder.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreOrderDTO create(StoreOrder resources) {
        if (storeOrderRepository.findByUnique(resources.getUnique()) != null) {
            throw new EntityExistException(StoreOrder.class, "unique", resources.getUnique());
        }
        return storeOrderMapper.toDto(storeOrderRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreOrder resources) {
        Optional<StoreOrder> optionalStoreOrder = storeOrderRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalStoreOrder, "StoreOrder", "id", resources.getId());
        StoreOrder storeOrder = optionalStoreOrder.get();
        StoreOrder storeOrder1 = storeOrderRepository.findByUnique(resources.getUnique());
        if (storeOrder1 != null && !storeOrder1.getId().equals(storeOrder.getId())) {
            throw new EntityExistException(StoreOrder.class, "unique", resources.getUnique());
        }
        storeOrder.copy(resources);
        storeOrderRepository.save(storeOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        storeOrderRepository.deleteById(id);
    }

    @Override
    public List<StoreOrderTimeDataDTO> getShopSalesAmount(int timeS, int timeE) {
        List<Map<String, Object>> orderList;
        if (timeE > 0) {
            orderList = storeOrderRepository.getShopOrderSalesByPayTimeLastDay(timeS, timeE);
        } else {
            orderList = storeOrderRepository.getShopOrderSalesByPayTimeGreaterThanEqual(timeS);
        }

        List<StoreOrderTimeDataDTO> storeOrderTimeDataDTOList = orderList.stream().map(order -> {
            StoreOrderTimeDataDTO storeOrderTimeDataDTO = new StoreOrderTimeDataDTO();
            storeOrderTimeDataDTO.setShopId(Long.valueOf(String.valueOf(order.get("shop_id"))));
            storeOrderTimeDataDTO.setTotalAmount(
                    BigDecimal.valueOf(Double.parseDouble(String.valueOf(String.valueOf(order.get("totalAmount"))))));
            storeOrderTimeDataDTO.setShopName(String.valueOf(String.valueOf(order.get("shopName"))));
            return storeOrderTimeDataDTO;
        }).collect(Collectors.toList());
        return storeOrderTimeDataDTOList;
    }

    @Override
    public List<Map<String, Object>> getShopCartListBetweenTimes(int timeS, int timeE) {
        return storeOrderRepository.getShopOrderSettlementByPayTimeLastDay(timeS, timeE);
    }

    @Override
    public int countSettlemnetOrder(Integer hidden, Integer isDel, Integer paid, Integer settlementStatus, int timeS,
                                    int timeE) {
        List<Map<String, Object>> mapList = storeOrderRepository.countSettlementOrder(hidden, isDel, paid,
                settlementStatus, timeS, timeE);
        return ((BigInteger) mapList.get(0).get("cnt")).intValue();
    }

    @Override
    public List<StoreOrder> getSettlementOrderList(Integer hidden, Integer isDel, Integer paid,
                                                   Integer settlementStatus, int timeS, int timeE, int limitStart,
                                                   int limit) {
        List<Map<String, Object>> mapList = storeOrderRepository.getSettlementOrderList(hidden, isDel, paid,
                settlementStatus, timeS, timeE, limitStart, limit);
        return mapList.stream().map(map -> {
            StoreOrder storeOrder = new StoreOrder();
            storeOrder.setId(Long.parseLong(String.valueOf(map.get("id"))));
            storeOrder.setAddTime(Integer.parseInt(String.valueOf(map.get("add_time"))));
            if (map.get("back_integral") != null) {
                storeOrder.setBackIntegral(
                        BigDecimal.valueOf(Double.parseDouble(String.valueOf(map.get("back_integral")))));
            }
            storeOrder.setBargainId(Long.parseLong(String.valueOf(map.get("bargain_id"))));
            storeOrder.setCartId(String.valueOf(map.get("cart_id")));
            storeOrder.setChargePrice((BigDecimal) map.get("charge_price"));
            storeOrder.setCombinationId(Long.parseLong(String.valueOf(map.get("combination_id"))));
            storeOrder.setCommission((BigDecimal) map.get("commission"));
            storeOrder.setCost((BigDecimal) map.get("cost"));
            storeOrder.setCouponId((Integer) map.get("coupon_id"));
            storeOrder.setCouponPrice((BigDecimal) map.get("coupon_price"));
            storeOrder.setDeductionPrice((BigDecimal) map.get("deduction_price"));
            if (map.get("delivery_id") != null) {
                storeOrder.setDeliveryId(String.valueOf(map.get("delivery_id")));
            }
            if (map.get("delivery_id") != null) {
                storeOrder.setDeliveryName(String.valueOf(map.get("delivery_name")));
            }
            if (map.get("delivery_sn") != null) {
                storeOrder.setDeliverySn(String.valueOf(map.get("delivery_sn")));
            }
            if (map.get("delivery_type") != null) {
                storeOrder.setDeliveryType(String.valueOf(map.get("delivery_type")));
            }
            if (map.get("extend_order_id") != null) {
                storeOrder.setExtendOrderId(String.valueOf(map.get("extend_order_id")));
            }
            storeOrder.setFreightPrice((BigDecimal) map.get("freight_price"));
            storeOrder.setGainIntegral((BigDecimal) map.get("gain_integral"));
            Boolean ohidden = (Boolean) map.get("hidden");
            storeOrder.setHidden(ohidden ? 1 : 0);
            Boolean isChannel = (Boolean) map.get("is_channel");
            storeOrder.setIsChannel(isChannel ? 1 : 0);
            Boolean bIsDel = (Boolean) map.get("is_del");
            storeOrder.setIsDel(bIsDel ? 1 : 0);
            Byte isMerCheck = (Byte) map.get("is_mer_check");
            int iisMerCheck = isMerCheck & 0xff;
            storeOrder.setIsMerCheck(iisMerCheck);
            Boolean isRemind = (Boolean) map.get("is_remind");
            storeOrder.setIsRemind(isRemind ? 1 : 0);
            Boolean isSystemDel = (Boolean) map.get("is_system_del");
            storeOrder.setIsSystemDel(isSystemDel ? 1 : 0);
            storeOrder.setMark(String.valueOf(map.get("setMark")));
            storeOrder.setMerId((Integer) map.get("mer_id"));
            storeOrder.setOrderId(String.valueOf(map.get("order_id")));
            Boolean oPaid = (Boolean) map.get("paid");
            storeOrder.setPaid(oPaid ? 1 : 0);
            storeOrder.setPayPostage((BigDecimal) map.get("pay_postage"));
            storeOrder.setPayPrice((BigDecimal) map.get("pay_price"));
            if (map.get("pay_time") != null) {
                storeOrder.setPayTime((Integer) map.get("pay_time"));
            }
            storeOrder.setPayType(String.valueOf(map.get("pay_type")));
            Byte oSettlementStatus = (Byte) map.get("settlement_status");
            int iSettlementStatus = oSettlementStatus & 0xff;
            storeOrder.setSettlementStatus(iSettlementStatus);
            storeOrder.setVerifyCode(String.valueOf(map.get("verify_code")));
            storeOrder.setUserAddress(String.valueOf(map.get("user_address")));
            storeOrder.setUserPhone(String.valueOf(map.get("user_phone")));
            storeOrder.setUseIntegral((BigDecimal) map.get("use_integral"));
            storeOrder.setUnique(String.valueOf(map.get("unique")));
            storeOrder.setTotalPrice((BigDecimal) map.get("total_price"));
            storeOrder.setTotalPostage((BigDecimal) map.get("total_postage"));
            storeOrder.setUid(Long.parseLong(String.valueOf(map.get("uid"))));
            storeOrder.setTotalNum((Integer) map.get("total_num"));
            storeOrder.setStoreId(Long.parseLong(String.valueOf(map.get("store_id"))));
            Byte orderStatus = (Byte) map.get("status");
            int iOrderStatus = orderStatus & 0xff;
            storeOrder.setStatus(iOrderStatus);
            storeOrder.setShopId(Long.parseLong(String.valueOf(map.get("shop_id"))));
            Byte shippigType = (Byte) map.get("shipping_type");
            int iShippingType = shippigType & 0xff;
            storeOrder.setShippingType(iShippingType);
            if (map.get("remark") != null) {
                storeOrder.setRemark(String.valueOf(map.get("remark")));
            }
            Byte refundStatus = (Byte) map.get("refund_status");
            int irefundStatus = refundStatus & 0xff;
            storeOrder.setRefundStatus(irefundStatus);
            if (map.get("refund_reason_wap_img") != null) {
                storeOrder.setRefundReasonWapImg(String.valueOf(map.get("refund_reason_wap_img")));
            }
            if (map.get("refund_reason_wap_explain") != null) {
                storeOrder.setRefundReasonWapExplain(String.valueOf(map.get("refund_reason_wap_explain")));
            }
            if (map.get("refund_reason_time") != null) {
                storeOrder.setRefundReasonTime((Integer) map.get("refund_reason_time"));
            }
            storeOrder.setRealName(String.valueOf(map.get("real_name")));
            storeOrder.setPinkId(Long.parseLong(String.valueOf(map.get("pink_id"))));
            storeOrder.setPromotionPrice((BigDecimal) map.get("promotion_price"));
            storeOrder.setStorePromotionPrice((BigDecimal) map.get("store_promotion_price"));
            storeOrder.setIntegralId(Long.parseLong(String.valueOf(map.get("integral_id"))));

            return storeOrder;
        }).collect(Collectors.toList());
    }
}
