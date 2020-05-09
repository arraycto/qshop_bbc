package co.lq.modules.user.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.enums.BillDetailEnum;
import co.lq.enums.BillEnum;
import co.lq.enums.OrderInfoEnum;
import co.lq.enums.PayTypeEnum;
import co.lq.modules.user.entity.User;
import co.lq.modules.user.entity.UserBill;
import co.lq.modules.user.entity.UserRecharge;
import co.lq.modules.user.mapper.UserMapper;
import co.lq.modules.user.mapper.UserRechargeMapper;
import co.lq.modules.user.service.UserBillService;
import co.lq.modules.user.service.UserRechargeService;
import co.lq.modules.user.service.WechatUserService;
import co.lq.modules.user.web.param.RechargeParam;
import co.lq.modules.user.web.param.UserRechargeQueryParam;
import co.lq.modules.user.web.vo.UserRechargeQueryVo;
import co.lq.modules.user.web.vo.WechatUserQueryVo;
import co.lq.mp.service.TemplateService;
import co.lq.utils.OrderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户充值表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2020-03-02
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserRechargeServiceImpl extends BaseServiceImpl<UserRechargeMapper, UserRecharge>
        implements UserRechargeService {

    private final UserRechargeMapper userRechargeMapper;
    private final UserBillService    billService;
    private final UserMapper         userMapper;
    private final WechatUserService  wechatUserService;
    private final TemplateService    templateService;

    @Override
    public void updateRecharge(UserRecharge userRecharge) {
        User user = userMapper.selectById(userRecharge.getUid());

        //修改状态
        userRecharge.setPaid(OrderInfoEnum.PAY_STATUS_1.getValue());
        userRecharge.setPayTime(OrderUtil.getSecondTimestampTwo());
        userRechargeMapper.updateById(userRecharge);

        //增加流水
        UserBill userBill = new UserBill();
        userBill.setUid(userRecharge.getUid());
        userBill.setTitle("用户余额充值");
        userBill.setLinkId(userRecharge.getId().toString());
        userBill.setCategory(BillDetailEnum.CATEGORY_1.getValue());
        userBill.setType(BillDetailEnum.TYPE_1.getValue());
        userBill.setNumber(userRecharge.getPrice());
        userBill.setBalance(NumberUtil.add(userRecharge.getPrice(), user.getNowMoney()));
        userBill.setMark("成功充值余额" + userRecharge.getPrice());
        userBill.setStatus(BillEnum.STATUS_1.getValue());
        userBill.setPm(BillEnum.PM_1.getValue());
        userBill.setAddTime(OrderUtil.getSecondTimestampTwo());
        billService.save(userBill);

        //update 余额
        user.setNowMoney(NumberUtil.add(userRecharge.getPrice(), user.getNowMoney()));
        userMapper.updateById(user);

        //模板消息推送
        WechatUserQueryVo wechatUser = wechatUserService.getWechatUserById(userRecharge.getUid());
        if (ObjectUtil.isNotNull(wechatUser)) {
            //公众号模板通知
            if (StrUtil.isNotBlank(wechatUser.getOpenid())) {
                templateService.rechargeSuccessNotice(OrderUtil.stampToDate(userRecharge.getPayTime().toString()),
                        userRecharge.getPrice().toString(), wechatUser.getOpenid());
            } else if (StrUtil.isNotBlank(wechatUser.getRoutineOpenid())) {
                //todo 小程序模板通知

            }
        }
    }

    @Override
    public UserRecharge getInfoByOrderId(String orderId) {
        UserRecharge userRecharge = new UserRecharge();
        userRecharge.setOrderId(orderId);

        return userRechargeMapper.selectOne(Wrappers.query(userRecharge));
    }

    /**
     * 充值
     *
     * @param param
     */
    @Override
    public void addRecharge(RechargeParam param, long uid) {
        UserRecharge userRecharge = new UserRecharge();

        User user = userMapper.selectById(uid);

        userRecharge.setNickname(user.getNickname());
        userRecharge.setOrderId(param.getOrderSn());
        userRecharge.setUid(uid);
        userRecharge.setPrice(BigDecimal.valueOf(param.getPrice()));
        userRecharge.setRechargeType(PayTypeEnum.WEIXIN.getValue());
        userRecharge.setPaid(OrderInfoEnum.PAY_STATUS_0.getValue());
        userRecharge.setAddTime(OrderUtil.getSecondTimestampTwo());

        userRechargeMapper.insert(userRecharge);

    }

    @Override
    public UserRechargeQueryVo getUserRechargeById(Serializable id) throws Exception {
        return userRechargeMapper.getUserRechargeById(id);
    }

    @Override
    public Paging<UserRechargeQueryVo> getUserRechargePageList(UserRechargeQueryParam userRechargeQueryParam)
            throws Exception {
        Page page = setPageParam(userRechargeQueryParam, OrderItem.desc("create_time"));
        IPage<UserRechargeQueryVo> iPage = userRechargeMapper.getUserRechargePageList(page, userRechargeQueryParam);
        return new Paging(iPage);
    }

}
