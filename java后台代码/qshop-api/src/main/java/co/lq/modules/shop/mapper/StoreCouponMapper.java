package co.lq.modules.shop.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.entity.StoreCoupon;
import co.lq.modules.shop.web.param.StoreCouponQueryParam;
import co.lq.modules.shop.web.vo.StoreCouponQueryVo;

/**
 * <p>
 * 优惠券表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-10-27
 */
@Repository
public interface StoreCouponMapper extends BaseMapper<StoreCoupon> {

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
     * @param page
     * @param storeCouponQueryParam
     * @return
     */
    IPage<StoreCouponQueryVo> getStoreCouponPageList(@Param("page") Page page,
                                                     @Param("param") StoreCouponQueryParam storeCouponQueryParam);

}
