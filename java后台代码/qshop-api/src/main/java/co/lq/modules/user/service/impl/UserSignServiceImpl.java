package co.lq.modules.user.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.exception.ErrorRequestException;
import co.lq.modules.shop.service.ApiSystemGroupDataService;
import co.lq.modules.user.entity.User;
import co.lq.modules.user.entity.UserBill;
import co.lq.modules.user.entity.UserSign;
import co.lq.modules.user.mapper.UserBillMapper;
import co.lq.modules.user.mapper.UserSignMapper;
import co.lq.modules.user.service.UserBillService;
import co.lq.modules.user.service.UserService;
import co.lq.modules.user.service.UserSignService;
import co.lq.modules.user.web.dto.SignDTO;
import co.lq.modules.user.web.param.UserSignQueryParam;
import co.lq.modules.user.web.vo.UserQueryVo;
import co.lq.modules.user.web.vo.UserSignQueryVo;
import co.lq.utils.OrderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 签到记录表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-12-05
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserSignServiceImpl extends BaseServiceImpl<UserSignMapper, UserSign> implements UserSignService {

    private UserSignMapper            userSignMapper;
    private UserBillMapper            userBillMapper;

    private ApiSystemGroupDataService systemGroupDataService;
    private UserService               userService;
    private UserBillService           billService;

    /**
     * 用户签到
     *
     * @param uid
     */
    @Override
    public int sign(long uid) {
        List<Map<String, Object>> list = systemGroupDataService.getDatas("sign_day_num");
        if (ObjectUtil.isNull(list))
            throw new ErrorRequestException("请先配置签到天数");

        UserQueryVo userQueryVo = userService.getUserById(uid);
        int signNumber = 0; //积分
        int userSignNum = userQueryVo.getSignNum(); //签到次数
        if (getYesterDayIsSign(uid)) {
            if (userQueryVo.getSignNum() > (list.size() - 1)) {
                userSignNum = 0;
            }
        } else {
            userSignNum = 0;
        }
        int index = 0;
        for (Map<String, Object> map : list) {
            if (index == userSignNum) {
                signNumber = Integer.valueOf(map.get("sign_num").toString());
                break;
            }
            index++;
        }

        userSignNum += 1;

        UserSign userSign = new UserSign();
        userSign.setUid(uid);
        String title = "签到奖励";
        if (userSignNum == list.size()) {
            title = "连续签到奖励";
        }
        userSign.setTitle(title);
        userSign.setNumber(signNumber);
        userSign.setBalance(userQueryVo.getIntegral().intValue());
        userSign.setAddTime(OrderUtil.getSecondTimestampTwo());
        userSignMapper.insert(userSign);

        //用户积分增加
        User user = new User();
        user.setIntegral(NumberUtil.add(userQueryVo.getIntegral(), signNumber));
        user.setUid(uid);
        user.setSignNum(userSignNum);
        userService.updateById(user);

        //插入流水
        UserBill userBill = new UserBill();
        userBill.setUid(uid);
        userBill.setTitle(title);
        userBill.setLinkId("0");
        userBill.setCategory("integral");
        userBill.setType("sign");
        userBill.setNumber(BigDecimal.valueOf(signNumber));
        userBill.setBalance(userQueryVo.getIntegral());
        userBill.setMark("");
        userBill.setStatus(1);
        userBill.setPm(1);
        userBill.setAddTime(OrderUtil.getSecondTimestampTwo());
        billService.save(userBill);

        return signNumber;
    }

    /**
     * 分页获取用户签到数据
     *
     * @param uid
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<SignDTO> getSignList(long uid, int page, int limit) {
        Page<UserBill> pageModel = new Page<>(page, limit);
        return userBillMapper.getSignList(uid, pageModel);
    }

    /**
     * 获取用户昨天是否签到
     *
     * @param uid
     * @return
     */
    @Override
    public boolean getYesterDayIsSign(long uid) {
        int today = OrderUtil.dateToTimestampT(DateUtil.beginOfDay(new Date()));
        int yesterday = OrderUtil.dateToTimestampT(DateUtil.beginOfDay(DateUtil.yesterday()));

        QueryWrapper<UserSign> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid).lt("add_time", today).ge("add_time", yesterday);
        int count = userSignMapper.selectCount(wrapper);
        if (count > 0)
            return true;
        return false;
    }

    /**
     * 获取用户今天是否签到
     *
     * @param uid
     * @return
     */
    @Override
    public boolean getToDayIsSign(long uid) {
        int today = OrderUtil.dateToTimestampT(DateUtil.beginOfDay(new Date()));
        int yesterday = OrderUtil.dateToTimestampT(DateUtil.beginOfDay(DateUtil.yesterday()));

        QueryWrapper<UserSign> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid).ge("add_time", today);
        int count = userSignMapper.selectCount(wrapper);
        if (count > 0) {
            return true;
        }
        return false;
    }

    /**
     * 获取用户累计签到次数
     *
     * @param uid
     * @return
     */
    @Override
    public int getSignSumDay(long uid) {
        QueryWrapper<UserSign> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid);
        int count = userSignMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public UserSignQueryVo getUserSignById(Serializable id) throws Exception {
        return userSignMapper.getUserSignById(id);
    }

    @Override
    public Paging<UserSignQueryVo> getUserSignPageList(UserSignQueryParam userSignQueryParam) throws Exception {
        Page page = setPageParam(userSignQueryParam, OrderItem.desc("create_time"));
        IPage<UserSignQueryVo> iPage = userSignMapper.getUserSignPageList(page, userSignQueryParam);
        return new Paging(iPage);
    }

}
