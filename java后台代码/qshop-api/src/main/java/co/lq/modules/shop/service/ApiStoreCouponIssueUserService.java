package co.lq.modules.shop.service;

import java.io.Serializable;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.StoreCouponIssueUser;
import co.lq.modules.shop.web.param.StoreCouponIssueUserQueryParam;
import co.lq.modules.shop.web.vo.StoreCouponIssueUserQueryVo;

/**
 * <p>
 * 优惠券前台用户领取记录表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-10-27
 */
public interface ApiStoreCouponIssueUserService extends BaseService<StoreCouponIssueUser> {

    void addUserIssue(long uid, long id);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreCouponIssueUserQueryVo getStoreCouponIssueUserById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param storeCouponIssueUserQueryParam
     * @return
     */
    Paging<StoreCouponIssueUserQueryVo> getStoreCouponIssueUserPageList(StoreCouponIssueUserQueryParam storeCouponIssueUserQueryParam)
            throws Exception;

}
