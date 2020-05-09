package co.lq.modules.shop.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import co.lq.modules.shop.domain.StoreOrder;
import co.lq.modules.shop.domain.User;
import co.lq.modules.shop.domain.UserBill;
import co.lq.modules.shop.repository.StoreOrderRepository;
import co.lq.modules.shop.repository.UserRepository;
import co.lq.modules.shop.service.SystemConfigService;
import co.lq.modules.shop.service.UserBillService;
import co.lq.modules.shop.service.UserService;
import co.lq.modules.shop.service.dto.UserDTO;
import co.lq.modules.shop.service.dto.UserMoneyDTO;
import co.lq.modules.shop.service.dto.UserQueryCriteria;
import co.lq.modules.shop.service.mapper.UserMapper;
import co.lq.utils.OrderUtil;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-10-06
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private final UserRepository       userRepository;

    private final UserMapper           userMapper;

    private final UserBillService      userBillService;

    private final SystemConfigService  systemConfigService;

    private final StoreOrderRepository storeOrderRepository;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, UserBillService userBillService,
                           SystemConfigService systemConfigService, StoreOrderRepository storeOrderRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userBillService = userBillService;
        this.systemConfigService = systemConfigService;
        this.storeOrderRepository = storeOrderRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMoney(UserMoneyDTO param) {
        UserDTO userDTO = findById(param.getUid());
        Double newMoney = 0d;
        String mark = "";
        String type = "system_add";
        Integer pm = 1;
        String title = "系统增加余额";
        if (param.getPtype() == 1) {
            mark = "系统增加了" + param.getMoney() + "余额";
            newMoney = NumberUtil.add(userDTO.getNowMoney(), param.getMoney()).doubleValue();
        } else {
            title = "系统减少余额";
            mark = "系统扣除了" + param.getMoney() + "余额";
            type = "system_sub";
            pm = 0;
            newMoney = NumberUtil.sub(userDTO.getNowMoney(), param.getMoney()).doubleValue();
            if (newMoney < 0) {
                newMoney = 0d;
            }

        }
        User user = new User();
        user.setUid(userDTO.getUid());
        user.setNowMoney(BigDecimal.valueOf(newMoney));
        update(user);

        UserBill userBill = new UserBill();
        userBill.setUid(userDTO.getUid());
        userBill.setLinkId("0");
        userBill.setPm(pm);
        userBill.setTitle(title);
        userBill.setCategory("now_money");
        userBill.setType(type);
        userBill.setNumber(BigDecimal.valueOf(param.getMoney()));
        userBill.setBalance(BigDecimal.valueOf(newMoney));
        userBill.setMark(mark);
        userBill.setAddTime(OrderUtil.getSecondTimestampTwo());
        userBill.setStatus(1);
        userBillService.create(userBill);
    }

    @Override
    public Map<String, Object> queryAll(UserQueryCriteria criteria, Pageable pageable) {
        Page<User> page = userRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(userMapper::toDto));
    }

    @Override
    public List<UserDTO> queryAll(UserQueryCriteria criteria) {
        return userMapper.toDto(userRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public UserDTO findById(Long uid) {
        Optional<User> user = userRepository.findById(uid);
        ValidationUtil.isNull(user, "User", "uid", uid);
        return userMapper.toDto(user.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDTO create(User resources) {
        return userMapper.toDto(userRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(User resources) {
        Optional<User> optionalUser = userRepository.findById(resources.getUid());
        ValidationUtil.isNull(optionalUser, "User", "id", resources.getUid());
        User user = optionalUser.get();
        user.copy(resources);
        userRepository.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long uid) {
        userRepository.deleteById(uid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onStatus(Long uid, Integer status) {
        if (status == 1) {
            status = 0;
        } else {
            status = 1;
        }

        userRepository.updateOnstatus(status, uid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incBrokeragePrice(double price, Long uid) {
        userRepository.incBrokeragePrice(price, uid);
    }

    /**
     * 一级返佣
     *
     * @param order
     * @return
     */
    @Override
    public boolean backOrderBrokerage(StoreOrder order) {
        //如果分销没开启直接返回
        String open = systemConfigService.getData("store_brokerage_open");
        if (StrUtil.isEmpty(open) || open.equals("2"))
            return false;
        //支付金额减掉邮费
        double payPrice = 0d;
        payPrice = NumberUtil.sub(order.getPayPrice(), order.getPayPostage()).doubleValue();

        //获取购买商品的用户
        User userInfo = getUserById(order.getUid());
        //当前用户不存在 没有上级  直接返回
        if (ObjectUtil.isNull(userInfo) || userInfo.getSpreadUid() == 0) {
            return true;
        }

        //获取后台分销类型  1 指定分销 2 人人分销
        int storeBrokerageStatus = 1;
        if (StrUtil.isNotEmpty(systemConfigService.getData("store_brokerage_statu"))) {
            storeBrokerageStatus = Integer.valueOf(systemConfigService.getData("store_brokerage_statu"));
        }

        //指定分销 判断 上级是否时推广员  如果不是推广员直接跳转二级返佣
        User preUser = getUserById(userInfo.getSpreadUid());
        if (storeBrokerageStatus == 1) {

            if (preUser.getIsPromoter() == 0) {
                return backOrderBrokerageTwo(order);
            }
        }

        //获取后台一级返佣比例
        String storeBrokerageRatioStr = systemConfigService.getData("store_brokerage_ratio");
        int storeBrokerageRatio = 0;
        if (StrUtil.isNotEmpty(storeBrokerageRatioStr)) {
            storeBrokerageRatio = Integer.valueOf(storeBrokerageRatioStr);
        }
        //一级返佣比例 等于零时直接返回 不返佣
        if (storeBrokerageRatio == 0) {
            return true;
        }

        //计算获取一级返佣比例
        double brokerageRatio = NumberUtil.div(storeBrokerageRatio, 100);
        //成本价
        double cost = order.getCost().doubleValue();

        //成本价大于等于支付价格时直接返回
        if (cost >= payPrice) {
            return true;
        }

        //获取订单毛利
        payPrice = NumberUtil.sub(payPrice, cost);

        //返佣金额 = 毛利 / 一级返佣比例
        double brokeragePrice = NumberUtil.mul(payPrice, brokerageRatio);

        //返佣金额小于等于0 直接返回不返佣金
        if (brokeragePrice <= 0)
            return true;

        //计算上级推广员返佣之后的金额
        double balance = NumberUtil.add(preUser.getBrokeragePrice(), brokeragePrice).doubleValue();
        String mark = userInfo.getNickname() + "成功消费" + order.getPayPrice() + "元,奖励推广佣金" + brokeragePrice;
        //插入流水
        UserBill userBill = new UserBill();
        userBill.setUid(userInfo.getSpreadUid());
        userBill.setTitle("获得推广佣金");
        userBill.setLinkId(order.getId().toString());
        userBill.setCategory("now_money");
        userBill.setType("brokerage");
        userBill.setNumber(BigDecimal.valueOf(brokeragePrice));
        userBill.setBalance(BigDecimal.valueOf(balance));
        userBill.setMark(mark);
        userBill.setStatus(1);
        userBill.setPm(1);
        userBill.setAddTime(OrderUtil.getSecondTimestampTwo());
        userBillService.create(userBill);

        //添加用户余额
        userRepository.incBrokeragePrice(brokeragePrice, userInfo.getSpreadUid());

        //设置订单佣金
        StoreOrder storeOrder = new StoreOrder();
        storeOrder.setId(order.getId());
        storeOrder.setCommission(BigDecimal.valueOf(brokeragePrice));
        storeOrderRepository.save(storeOrder);

        //一级返佣成功 跳转二级返佣
        backOrderBrokerageTwo(order);

        return false;
    }

    /**
     * 二级返佣
     *
     * @param order
     * @return
     */
    @Override
    public boolean backOrderBrokerageTwo(StoreOrder order) {

        double payPrice = 0d;
        payPrice = NumberUtil.sub(order.getPayPrice(), order.getPayPostage()).doubleValue();

        User userInfo = getUserById(order.getUid());

        //获取上推广人
        User userInfoTwo = getUserById(userInfo.getSpreadUid());

        //上推广人不存在 或者 上推广人没有上级    直接返回
        if (ObjectUtil.isNull(userInfoTwo) || userInfoTwo.getSpreadUid() == 0) {
            return true;
        }

        //获取后台分销类型  1 指定分销 2 人人分销
        int storeBrokerageStatus = 1;
        if (StrUtil.isNotEmpty(systemConfigService.getData("store_brokerage_statu"))) {
            storeBrokerageStatus = Integer.valueOf(systemConfigService.getData("store_brokerage_statu"));
        }
        //指定分销 判断 上上级是否时推广员  如果不是推广员直接返回
        User preUser = getUserById(userInfoTwo.getSpreadUid());
        if (storeBrokerageStatus == 1) {

            if (preUser.getIsPromoter() == 0) {
                return true;
            }
        }

        //获取二级返佣比例
        String storeBrokerageTwoStr = systemConfigService.getData("store_brokerage_two");
        int storeBrokerageTwo = 0;
        if (StrUtil.isNotEmpty(storeBrokerageTwoStr)) {
            storeBrokerageTwo = Integer.valueOf(storeBrokerageTwoStr);
        }
        //一级返佣比例 等于零时直接返回 不返佣
        if (storeBrokerageTwo == 0) {
            return true;
        }

        //计算获取二级返佣比例
        double brokerageRatio = NumberUtil.div(storeBrokerageTwo, 100);
        //成本价
        double cost = order.getCost().doubleValue();

        //成本价大于等于支付价格时直接返回
        if (cost >= payPrice) {
            return true;
        }

        //获取订单毛利
        payPrice = NumberUtil.sub(payPrice, cost);

        //返佣金额 = 毛利 / 二级返佣比例
        double brokeragePrice = NumberUtil.mul(payPrice, brokerageRatio);

        //返佣金额小于等于0 直接返回不返佣金
        if (brokeragePrice <= 0)
            return true;

        //获取上上级推广员信息
        double balance = NumberUtil.add(preUser.getBrokeragePrice(), brokeragePrice).doubleValue();
        String mark = "二级推广人" + userInfo.getNickname() + "成功消费" + order.getPayPrice() + "元,奖励推广佣金" + brokeragePrice;
        //插入流水
        UserBill userBill = new UserBill();
        userBill.setUid(userInfoTwo.getSpreadUid());
        userBill.setTitle("获得推广佣金");
        userBill.setLinkId(order.getId().toString());
        userBill.setCategory("now_money");
        userBill.setType("brokerage");
        userBill.setNumber(BigDecimal.valueOf(brokeragePrice));
        userBill.setBalance(BigDecimal.valueOf(balance));
        userBill.setMark(mark);
        userBill.setStatus(1);
        userBill.setPm(1);
        userBill.setAddTime(OrderUtil.getSecondTimestampTwo());
        userBillService.create(userBill);

        //添加用户余额
        userRepository.incBrokeragePrice(brokeragePrice, userInfoTwo.getSpreadUid());

        //设置订单佣金
        StoreOrder storeOrder = new StoreOrder();
        storeOrder.setId(order.getId());
        storeOrder.setCommission(BigDecimal.valueOf(brokeragePrice));
        storeOrderRepository.save(storeOrder);

        return false;
    }

    private User getUserById(Long id) {
        return userRepository.getOne(id);
    }
}
