package co.lq.modules.shop.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.util.ObjectUtil;
import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.exception.ErrorRequestException;
import co.lq.modules.shop.entity.StoreCouponUser;
import co.lq.modules.shop.mapper.StoreCouponUserMapper;
import co.lq.modules.shop.mapping.CouponMap;
import co.lq.modules.shop.service.ApiStoreCouponService;
import co.lq.modules.shop.service.ApiStoreCouponUserService;
import co.lq.modules.shop.web.param.StoreCouponUserQueryParam;
import co.lq.modules.shop.web.vo.StoreCouponQueryVo;
import co.lq.modules.shop.web.vo.StoreCouponUserQueryVo;
import co.lq.utils.OrderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 优惠券发放记录表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-10-27
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ApiStoreCouponUserServiceImpl extends BaseServiceImpl<StoreCouponUserMapper, StoreCouponUser>
        implements ApiStoreCouponUserService {

    private final StoreCouponUserMapper storeCouponUserMapper;

    private final ApiStoreCouponService storeCouponService;

    private final CouponMap             couponMap;

    @Override
    public int getUserValidCouponCount(long uid) {
        checkInvalidCoupon(uid);
        QueryWrapper<StoreCouponUser> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 0).eq("uid", uid);
        return storeCouponUserMapper.selectCount(wrapper);
    }

    @Override
    public List<StoreCouponUser> beUsableCouponList(long uid, double price) {
        QueryWrapper<StoreCouponUser> wrapper = new QueryWrapper<>();
        wrapper.eq("is_fail", 0).eq("status", 0).le("use_min_price", price).eq("uid", uid);
        return storeCouponUserMapper.selectList(wrapper);
    }

    /**
     * 获取可用优惠券
     *
     * @param uid
     * @param price
     * @return
     */
    @Override
    public StoreCouponUser beUsableCoupon(long uid, double price) {
        QueryWrapper<StoreCouponUser> wrapper = new QueryWrapper<>();
        wrapper.eq("is_fail", 0).eq("status", 0).eq("uid", uid).le("use_min_price", price).last("limit 1");
        return getOne(wrapper);
    }

    @Override
    public StoreCouponUser getCoupon(long id, long uid) {
        QueryWrapper<StoreCouponUser> wrapper = new QueryWrapper<>();
        wrapper.eq("is_fail", 0).eq("status", 0).eq("uid", uid).eq("id", id);
        return getOne(wrapper);
    }

    @Override
    public void useCoupon(long id) {
        StoreCouponUser couponUser = new StoreCouponUser();
        couponUser.setId(id);
        couponUser.setStatus(1);
        couponUser.setUseTime(OrderUtil.getSecondTimestampTwo());
        storeCouponUserMapper.updateById(couponUser);
    }

    /**
     * 检查优惠券状态
     *
     * @param uid
     */
    @Override
    public void checkInvalidCoupon(long uid) {
        int nowTime = OrderUtil.getSecondTimestampTwo();
        QueryWrapper<StoreCouponUser> wrapper = new QueryWrapper<>();
        wrapper.lt("end_time", nowTime).eq("status", 0);
        StoreCouponUser couponUser = new StoreCouponUser();
        couponUser.setStatus(2);
        storeCouponUserMapper.update(couponUser, wrapper);

    }

    /**
     * 获取用户优惠券
     *
     * @param uid uid
     * @param type type
     * @return
     */
    @Override
    public List<StoreCouponUserQueryVo> getUserCoupon(long uid, int type) {

        checkInvalidCoupon(uid);
        QueryWrapper<StoreCouponUser> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid);//默认获取所有
        if (type == 1) {//获取用户优惠券（未使用）
            wrapper.eq("status", 0);
        } else if (type == 2) {//获取用户优惠券（已使用）
            wrapper.eq("status", 1);
        } else if (type > 2) {//获取用户优惠券（已过期）
            wrapper.eq("status", 2);
        }
        List<StoreCouponUser> storeCouponUsers = storeCouponUserMapper.selectList(wrapper);

        List<StoreCouponUserQueryVo> storeCouponUserQueryVoList = new ArrayList<>();
        int nowTime = OrderUtil.getSecondTimestampTwo();
        for (StoreCouponUser couponUser : storeCouponUsers) {
            StoreCouponUserQueryVo queryVo = couponMap.toDto(couponUser);
            if (couponUser.getIsFail() == 1) {
                queryVo.set_type(0);
                queryVo.set_msg("已失效");
            } else if (couponUser.getStatus() == 1) {
                queryVo.set_type(0);
                queryVo.set_msg("已使用");
            } else if (couponUser.getStatus() == 2) {
                queryVo.set_type(0);
                queryVo.set_msg("已过期");
            } else if (couponUser.getAddTime() > nowTime || couponUser.getEndTime() < nowTime) {
                queryVo.set_type(0);
                queryVo.set_msg("已过期");
            } else {
                if (couponUser.getAddTime() + 3600 * 24 > nowTime) {
                    queryVo.set_type(2);
                    queryVo.set_msg("可使用");
                } else {
                    queryVo.set_type(1);
                    queryVo.set_msg("可使用");
                }
            }

            storeCouponUserQueryVoList.add(queryVo);

        }
        return storeCouponUserQueryVoList;
    }

    @Override
    public void addUserCoupon(long uid, long cid) {
        StoreCouponQueryVo storeCouponQueryVo = storeCouponService.getStoreCouponById(cid);
        if (ObjectUtil.isNull(storeCouponQueryVo))
            throw new ErrorRequestException("优惠劵不存在");

        StoreCouponUser couponUser = new StoreCouponUser();
        couponUser.setCid(cid);
        couponUser.setUid(uid);
        couponUser.setCouponTitle(storeCouponQueryVo.getTitle());
        couponUser.setCouponPrice(storeCouponQueryVo.getCouponPrice());
        couponUser.setUseMinPrice(storeCouponQueryVo.getUseMinPrice());
        int addTime = OrderUtil.getSecondTimestampTwo();
        couponUser.setAddTime(addTime);
        int endTime = addTime + storeCouponQueryVo.getCouponTime() * 86400;
        couponUser.setEndTime(endTime);
        couponUser.setType("get");

        save(couponUser);

    }

    @Override
    public StoreCouponUserQueryVo getStoreCouponUserById(Serializable id) throws Exception {
        return storeCouponUserMapper.getStoreCouponUserById(id);
    }

    @Override
    public Paging<StoreCouponUserQueryVo> getStoreCouponUserPageList(StoreCouponUserQueryParam storeCouponUserQueryParam)
            throws Exception {
        Page page = setPageParam(storeCouponUserQueryParam, OrderItem.desc("create_time"));
        IPage<StoreCouponUserQueryVo> iPage = storeCouponUserMapper.getStoreCouponUserPageList(page,
                storeCouponUserQueryParam);
        return new Paging(iPage);
    }

    @Override
    public List<StoreCouponUser> getUsableStoreCouponList(List<StoreCouponUser> storeCouponUserList,
                                                          Double goodsAmount) {
        return storeCouponUserList.stream()
                .filter(storeCouponUser -> storeCouponUser.getUseMinPrice().doubleValue() < goodsAmount)
                .collect(Collectors.toList());
    }

}
