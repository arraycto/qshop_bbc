package co.lq.modules.user.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.exception.ErrorRequestException;
import co.lq.modules.order.service.StoreOrderService;
import co.lq.modules.shop.service.ApiStoreCouponUserService;
import co.lq.modules.shop.service.ApiSystemConfigService;
import co.lq.modules.user.entity.User;
import co.lq.modules.user.entity.UserLevelRelation;
import co.lq.modules.user.mapper.UserMapper;
import co.lq.modules.user.service.UserBillService;
import co.lq.modules.user.service.UserLevelRelationService;
import co.lq.modules.user.service.UserService;
import co.lq.modules.user.web.dto.PromUserDTO;
import co.lq.modules.user.web.param.PromParam;
import co.lq.modules.user.web.param.UserQueryParam;
import co.lq.modules.user.web.vo.UserQueryVo;
import co.lq.utils.OrderUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-10-16
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper                userMapper;

    @Autowired
    private StoreOrderService         orderService;
    @Autowired
    private ApiSystemConfigService    systemConfigService;
    @Autowired
    private UserBillService           billService;
    @Autowired
    private UserLevelRelationService  userLevelRelationService;
    @Autowired
    private ApiStoreCouponUserService storeCouponUserService;

    /**
     * 返回会员价
     *
     * @param price
     * @param uid
     * @return
     */
    @Override
    public double setLevelPrice(double price, long uid) {
        QueryWrapper<UserLevelRelation> wrapper = new QueryWrapper<>();
        wrapper.eq("is_del", 0).eq("status", 1).eq("uid", uid).orderByDesc("grade").last("limit 1");
        UserLevelRelation userLevelRelation = userLevelRelationService.getOne(wrapper);
        int discount = 100;
        if (ObjectUtil.isNotNull(userLevelRelation))
            discount = userLevelRelation.getDiscount();
        return NumberUtil.mul(NumberUtil.div(discount, 100), price);
    }

    @Override
    public UserLevelRelation getUserLeve(long uid) {
        QueryWrapper<UserLevelRelation> wrapper = new QueryWrapper<>();
        wrapper.eq("is_del", 0).eq("status", 1).eq("uid", uid).orderByDesc("grade").last("limit 1");
        UserLevelRelation userLevelRelation = userLevelRelationService.getOne(wrapper);
        return userLevelRelation;
    }

    /**
     * 更新用户余额
     *
     * @param uid
     * @param price
     */
    @Override
    public void incMoney(long uid, double price) {
        userMapper.incMoney(uid, price);
    }

    @Override
    public void incIntegral(long uid, double integral) {
        userMapper.incIntegral(integral, uid);
    }

    @Override
    public void setUserSpreadCount(long uid) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("spread_uid", uid);
        int count = userMapper.selectCount(wrapper);

        User user = new User();
        user.setUid(uid);
        user.setSpreadCount(count);

        userMapper.updateById(user);
    }

    @Override
    public int getSpreadCount(long uid, int type) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("spread_uid", uid);
        int count = 0;
        if (type == 1) {
            count = userMapper.selectCount(wrapper);
        } else {
            List<User> userList = userMapper.selectList(wrapper);
            List<Long> userIds = userList.stream().map(User::getUid).collect(Collectors.toList());
            if (userIds.isEmpty()) {
                count = 0;
            } else {
                QueryWrapper<User> wrapperT = new QueryWrapper<>();
                wrapperT.in("spread_uid", userIds);

                count = userMapper.selectCount(wrapperT);
            }

        }
        return count;
    }

    @Override
    public List<PromUserDTO> getUserSpreadGrade(PromParam promParam, long uid) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("spread_uid", uid);
        List<User> userList = userMapper.selectList(wrapper);
        List<Long> userIds = userList.stream().map(User::getUid).collect(Collectors.toList());
        List<PromUserDTO> list = new ArrayList<>();
        if (userIds.isEmpty())
            return list;
        String sort;
        if (StrUtil.isEmpty(promParam.getSort())) {
            sort = "u.add_time desc";
        } else {
            sort = promParam.getSort();
        }
        String keyword = null;
        if (StrUtil.isNotEmpty(promParam.getKeyword())) {
            keyword = promParam.getKeyword();
        }
        Page<User> pageModel = new Page<>(promParam.getPage(), promParam.getLimit());
        if (promParam.getGrade() == 0) {//-级
            list = userMapper.getUserSpreadCountList(pageModel, userIds, keyword, sort);
        } else {//二级
            QueryWrapper<User> wrapperT = new QueryWrapper<>();
            wrapperT.in("spread_uid", userIds);
            List<User> userListT = userMapper.selectList(wrapperT);
            List<Long> userIdsT = userListT.stream().map(User::getUid).collect(Collectors.toList());
            if (userIdsT.isEmpty())
                return list;
            list = userMapper.getUserSpreadCountList(pageModel, userIdsT, keyword, sort);

        }
        return list;
    }

    /**
     * 设置推广关系
     *
     * @param spread
     * @param uid
     */
    @Override
    public boolean setSpread(long spread, long uid) {
        //如果分销没开启直接返回
        String open = systemConfigService.getData("store_brokerage_open");
        if (StrUtil.isEmpty(open) || open.equals("2"))
            return false;
        //当前用户信息
        UserQueryVo userInfo = getUserById(uid);
        if (ObjectUtil.isNull(userInfo))
            return true;

        //当前用户有上级直接返回
        if (userInfo.getSpreadUid() > 0)
            return true;
        //没有推广编号直接返回
        if (spread == 0)
            return true;
        if (spread == uid)
            return true;

        //不能互相成为上下级
        UserQueryVo userInfoT = getUserById(spread);
        if (ObjectUtil.isNull(userInfoT))
            return true;

        if (userInfoT.getSpreadUid() == uid)
            return true;

        //1-指定分销 2-人人分销
        int storeBrokerageStatus = Integer.valueOf(systemConfigService.getData("store_brokerage_statu"));
        //如果是指定分销，如果 推广人不是分销员不能形成关系
        if (storeBrokerageStatus == 1 && userInfoT.getIsPromoter() == 0) {
            return true;
        }
        User user = new User();

        user.setSpreadUid(spread);
        user.setSpreadTime(OrderUtil.getSecondTimestampTwo());
        user.setUid(uid);
        userMapper.updateById(user);

        return true;

    }

    @Override
    public void incPayCount(long uid) {
        userMapper.incPayCount(uid);
    }

    @Override
    public void decPrice(long uid, double payPrice) {
        userMapper.decPrice(payPrice, uid);
    }

    @Override
    public void decIntegral(long uid, double integral) {
        userMapper.decIntegral(integral, uid);
    }

    @Override
    public UserQueryVo getUserById(Serializable id) {
        UserQueryVo userQueryVo = userMapper.getUserById(id);
        userQueryVo.setOrderStatusNum(orderService.orderData((long) id));
        return userQueryVo;
    }

    @Override
    public UserQueryVo getNewUserById(Serializable id) {
        UserQueryVo userQueryVo = userMapper.getUserById(id);
        if (userQueryVo == null) {
            throw new ErrorRequestException("用户不存在");
        }
        userQueryVo.setOrderStatusNum(orderService.orderData((int) id));
        userQueryVo.setCouponCount(storeCouponUserService.getUserValidCouponCount((int) id));
        //判断分销类型
        String statu = systemConfigService.getData("store_brokerage_statu");
        if (StrUtil.isNotEmpty(statu)) {
            userQueryVo.setStatu(Integer.valueOf(statu));
        } else {
            userQueryVo.setStatu(0);
        }
        //todo 测试环境设置所有人是管理员,生成环境记得去掉
        //userQueryVo.setAdminid(1);
        return userQueryVo;
    }

    @Override
    public Paging<UserQueryVo> getUserPageList(UserQueryParam userQueryParam) throws Exception {
        Page page = setPageParam(userQueryParam, OrderItem.desc("add_time"));
        IPage<UserQueryVo> iPage = userMapper.getUserPageList(page, userQueryParam);
        return new Paging(iPage);
    }

    @Override
    public User findByName(String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", name);
        return getOne(wrapper);
    }
}
