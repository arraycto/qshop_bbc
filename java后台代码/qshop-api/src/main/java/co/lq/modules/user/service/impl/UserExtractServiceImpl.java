package co.lq.modules.user.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.enums.BillEnum;
import co.lq.exception.ErrorRequestException;
import co.lq.modules.user.entity.User;
import co.lq.modules.user.entity.UserBill;
import co.lq.modules.user.entity.UserExtract;
import co.lq.modules.user.mapper.UserExtractMapper;
import co.lq.modules.user.service.UserBillService;
import co.lq.modules.user.service.UserExtractService;
import co.lq.modules.user.service.UserService;
import co.lq.modules.user.web.param.UserExtParam;
import co.lq.modules.user.web.param.UserExtractQueryParam;
import co.lq.modules.user.web.vo.UserExtractQueryVo;
import co.lq.modules.user.web.vo.UserQueryVo;
import co.lq.utils.OrderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户提现表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-11-11
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserExtractServiceImpl extends BaseServiceImpl<UserExtractMapper, UserExtract>
        implements UserExtractService {

    private final UserExtractMapper userExtractMapper;

    private final UserService       userService;
    private final UserBillService   billService;

    /**
     * 开始提现
     *
     * @param uid
     * @param param
     */
    @Override
    public void userExtract(long uid, UserExtParam param) {
        UserQueryVo userInfo = userService.getUserById(uid);
        double extractPrice = userInfo.getBrokeragePrice().doubleValue();
        if (extractPrice <= 0)
            throw new ErrorRequestException("提现佣金不足");

        double money = Double.valueOf(param.getMoney());
        if (money > extractPrice)
            throw new ErrorRequestException("提现佣金不足");

        if (money <= 0)
            throw new ErrorRequestException("提现佣金大于0");

        double balance = NumberUtil.sub(extractPrice, money);
        if (balance < 0)
            balance = 0;

        UserExtract userExtract = new UserExtract();
        userExtract.setUid(uid);
        userExtract.setExtractType(param.getExtractType());
        userExtract.setExtractPrice(new BigDecimal(param.getMoney()));
        userExtract.setAddTime(OrderUtil.getSecondTimestampTwo());
        userExtract.setBalance(BigDecimal.valueOf(balance));
        userExtract.setStatus(0);

        if (StrUtil.isNotEmpty(param.getName())) {
            userExtract.setRealName(param.getName());
        } else {
            userExtract.setRealName(userInfo.getNickname());
        }

        if (StrUtil.isNotEmpty(param.getWeixin())) {
            userExtract.setWechat(param.getWeixin());
        } else {
            userExtract.setWechat(userInfo.getNickname());
        }

        String mark = "";
        if (param.getExtractType().equals("alipay")) {
            if (StrUtil.isEmpty(param.getAlipayCode())) {
                throw new ErrorRequestException("请输入支付宝账号");
            }
            userExtract.setAlipayCode(param.getAlipayCode());
            mark = "使用支付宝提现" + param.getMoney() + "元";
        } else if (param.getExtractType().equals("weixin")) {
            if (StrUtil.isEmpty(param.getWeixin())) {
                throw new ErrorRequestException("请输入微信账号");
            }

            mark = "使用微信提现" + param.getMoney() + "元";
        }

        userExtractMapper.insert(userExtract);

        //更新佣金
        User user = new User();
        user.setBrokeragePrice(BigDecimal.valueOf(balance));
        user.setUid(uid);
        userService.updateById(user);
        //插入流水
        UserBill userBill = new UserBill();
        userBill.setUid(uid);
        userBill.setTitle("佣金提现");
        userBill.setLinkId(userExtract.getId().toString());
        userBill.setCategory("now_money");
        userBill.setType("extract");
        userBill.setNumber(BigDecimal.valueOf(money));
        userBill.setBalance(BigDecimal.valueOf(balance));
        userBill.setMark(mark);
        userBill.setStatus(BillEnum.STATUS_1.getValue());
        userBill.setPm(BillEnum.PM_0.getValue());
        userBill.setAddTime(OrderUtil.getSecondTimestampTwo());
        billService.save(userBill);

    }

    @Override
    public double extractSum(long uid) {
        return userExtractMapper.sumPrice(uid);
    }

    @Override
    public UserExtractQueryVo getUserExtractById(Serializable id) throws Exception {
        return userExtractMapper.getUserExtractById(id);
    }

    @Override
    public Paging<UserExtractQueryVo> getUserExtractPageList(UserExtractQueryParam userExtractQueryParam)
            throws Exception {
        Page page = setPageParam(userExtractQueryParam, OrderItem.desc("create_time"));
        IPage<UserExtractQueryVo> iPage = userExtractMapper.getUserExtractPageList(page, userExtractQueryParam);
        return new Paging(iPage);
    }

}
