package co.lq.modules.shop.service;

import java.io.Serializable;
import java.util.List;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.StoreCouponIssue;
import co.lq.modules.shop.web.param.StoreCouponIssueQueryParam;
import co.lq.modules.shop.web.vo.StoreCouponIssueQueryVo;

/**
 * <p>
 * 优惠券前台领取表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-10-27
 */
public interface ApiStoreCouponIssueService extends BaseService<StoreCouponIssue> {

    int couponCount(long id, long uid);

    void issueUserCoupon(long id, long uid);

    List<StoreCouponIssueQueryVo> getCouponList(int page, int limit, long uid);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreCouponIssueQueryVo getStoreCouponIssueById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param storeCouponIssueQueryParam
     * @return
     */
    Paging<StoreCouponIssueQueryVo> getStoreCouponIssuePageList(StoreCouponIssueQueryParam storeCouponIssueQueryParam)
            throws Exception;

}
