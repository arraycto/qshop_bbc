package co.lq.modules.shop.service.impl;

import java.io.Serializable;
import java.util.List;

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
import co.lq.modules.shop.entity.StoreCouponIssue;
import co.lq.modules.shop.entity.StoreCouponIssueUser;
import co.lq.modules.shop.mapper.StoreCouponIssueMapper;
import co.lq.modules.shop.mapper.StoreCouponIssueUserMapper;
import co.lq.modules.shop.service.ApiStoreCouponIssueService;
import co.lq.modules.shop.service.ApiStoreCouponIssueUserService;
import co.lq.modules.shop.service.ApiStoreCouponUserService;
import co.lq.modules.shop.web.param.StoreCouponIssueQueryParam;
import co.lq.modules.shop.web.vo.StoreCouponIssueQueryVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 优惠券前台领取表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-10-27
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ApiStoreCouponIssueServiceImpl extends BaseServiceImpl<StoreCouponIssueMapper, StoreCouponIssue>
        implements ApiStoreCouponIssueService {

    private final StoreCouponIssueMapper         storeCouponIssueMapper;
    private final StoreCouponIssueUserMapper     storeCouponIssueUserMapper;

    private final ApiStoreCouponUserService      storeCouponUserService;
    private final ApiStoreCouponIssueUserService storeCouponIssueUserService;

    /**
     * 领取优惠券
     *
     * @param id id
     * @param uid uid
     */
    @Override
    public void issueUserCoupon(long id, long uid) {
        StoreCouponIssueQueryVo couponIssueQueryVo = storeCouponIssueMapper.selectOne(id);
        if (ObjectUtil.isNull(couponIssueQueryVo))
            throw new ErrorRequestException("领取的优惠劵已领完或已过期");

        int count = couponCount(id, uid);
        if (count > 0)
            throw new ErrorRequestException("已领取过该优惠劵");

        if (couponIssueQueryVo.getRemainCount() <= 0 && couponIssueQueryVo.getIsPermanent() == 0) {
            throw new ErrorRequestException("抱歉优惠卷已经领取完了");
        }

        storeCouponUserService.addUserCoupon(uid, couponIssueQueryVo.getCid());

        storeCouponIssueUserService.addUserIssue(uid, id);

        if (couponIssueQueryVo.getTotalCount() > 0) {
            storeCouponIssueMapper.decCount(id);
        }

    }

    @Override
    public int couponCount(long id, long uid) {
        QueryWrapper<StoreCouponIssueUser> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid).eq("issue_coupon_id", id);
        int count = storeCouponIssueUserMapper.selectCount(wrapper);
        return count;
    }

    /**
     * 优惠券列表
     *
     * @param page
     * @param limit
     * @param uid
     * @return
     */
    @Override
    public List<StoreCouponIssueQueryVo> getCouponList(int page, int limit, long uid) {
        Page<StoreCouponIssue> pageModel = new Page<>(page, limit);
        List<StoreCouponIssueQueryVo> list = storeCouponIssueMapper.selectList(pageModel);
        for (StoreCouponIssueQueryVo couponIssue : list) {
            int count = couponCount(couponIssue.getId(), uid);
            if (count > 0) {
                couponIssue.setIsUse(true);
            } else {
                couponIssue.setIsUse(false);
            }

        }
        return list;
    }

    @Override
    public StoreCouponIssueQueryVo getStoreCouponIssueById(Serializable id) throws Exception {
        return storeCouponIssueMapper.getStoreCouponIssueById(id);
    }

    @Override
    public Paging<StoreCouponIssueQueryVo> getStoreCouponIssuePageList(StoreCouponIssueQueryParam storeCouponIssueQueryParam)
            throws Exception {
        Page page = setPageParam(storeCouponIssueQueryParam, OrderItem.desc("create_time"));
        IPage<StoreCouponIssueQueryVo> iPage = storeCouponIssueMapper.getStoreCouponIssuePageList(page,
                storeCouponIssueQueryParam);
        return new Paging(iPage);
    }

}
