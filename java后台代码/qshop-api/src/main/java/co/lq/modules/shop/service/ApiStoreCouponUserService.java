package co.lq.modules.shop.service;

import java.io.Serializable;
import java.util.List;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.StoreCouponUser;
import co.lq.modules.shop.web.param.StoreCouponUserQueryParam;
import co.lq.modules.shop.web.vo.StoreCouponUserQueryVo;

/**
 * <p>
 * 优惠券发放记录表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-10-27
 */
public interface ApiStoreCouponUserService extends BaseService<StoreCouponUser> {

    int getUserValidCouponCount(long uid);

    void useCoupon(long id);

    StoreCouponUser getCoupon(long id, long uid);

    List<StoreCouponUser> beUsableCouponList(long uid, double price);

    StoreCouponUser beUsableCoupon(long uid, double price);

    void checkInvalidCoupon(long uid);

    List<StoreCouponUserQueryVo> getUserCoupon(long uid, int type);

    void addUserCoupon(long uid, long cid);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreCouponUserQueryVo getStoreCouponUserById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param storeCouponUserQueryParam
     * @return
     */
    Paging<StoreCouponUserQueryVo> getStoreCouponUserPageList(StoreCouponUserQueryParam storeCouponUserQueryParam)
            throws Exception;

    /**
     * 筛选当前用户可以用的优惠券
     *
     * @param storeCouponUserList
     * @param goodsAmount
     * @return
     */
    List<StoreCouponUser> getUsableStoreCouponList(List<StoreCouponUser> storeCouponUserList, Double goodsAmount);

}
