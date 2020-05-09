package co.lq.modules.shop.service.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.StoreCouponIssueUser;
import co.lq.modules.shop.mapper.StoreCouponIssueUserMapper;
import co.lq.modules.shop.service.ApiStoreCouponIssueUserService;
import co.lq.modules.shop.web.param.StoreCouponIssueUserQueryParam;
import co.lq.modules.shop.web.vo.StoreCouponIssueUserQueryVo;
import co.lq.utils.OrderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 优惠券前台用户领取记录表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-10-27
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ApiStoreCouponIssueUserServiceImpl extends
        BaseServiceImpl<StoreCouponIssueUserMapper, StoreCouponIssueUser> implements ApiStoreCouponIssueUserService {

    private final StoreCouponIssueUserMapper storeCouponIssueUserMapper;

    @Override
    public void addUserIssue(long uid, long id) {
        StoreCouponIssueUser couponIssueUser = new StoreCouponIssueUser();
        couponIssueUser.setAddTime(OrderUtil.getSecondTimestampTwo());
        couponIssueUser.setIssueCouponId(id);
        couponIssueUser.setUid(uid);
        save(couponIssueUser);
    }

    @Override
    public StoreCouponIssueUserQueryVo getStoreCouponIssueUserById(Serializable id) throws Exception {
        return storeCouponIssueUserMapper.getStoreCouponIssueUserById(id);
    }

    @Override
    public Paging<StoreCouponIssueUserQueryVo> getStoreCouponIssueUserPageList(StoreCouponIssueUserQueryParam storeCouponIssueUserQueryParam)
            throws Exception {
        Page page = setPageParam(storeCouponIssueUserQueryParam, OrderItem.desc("create_time"));
        IPage<StoreCouponIssueUserQueryVo> iPage = storeCouponIssueUserMapper.getStoreCouponIssueUserPageList(page,
                storeCouponIssueUserQueryParam);
        return new Paging(iPage);
    }

}
