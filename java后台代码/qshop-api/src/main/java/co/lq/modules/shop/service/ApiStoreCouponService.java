package co.lq.modules.shop.service;

import java.io.Serializable;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.StoreCoupon;
import co.lq.modules.shop.web.param.StoreCouponQueryParam;
import co.lq.modules.shop.web.vo.StoreCouponQueryVo;

/**
 * <p>
 * 优惠券表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-10-27
 */
public interface ApiStoreCouponService extends BaseService<StoreCoupon> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreCouponQueryVo getStoreCouponById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param storeCouponQueryParam
     * @return
     */
    Paging<StoreCouponQueryVo> getStoreCouponPageList(StoreCouponQueryParam storeCouponQueryParam) throws Exception;

}
