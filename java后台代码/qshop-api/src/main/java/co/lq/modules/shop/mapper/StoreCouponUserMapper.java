package co.lq.modules.shop.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.entity.StoreCouponUser;
import co.lq.modules.shop.web.param.StoreCouponUserQueryParam;
import co.lq.modules.shop.web.vo.StoreCouponUserQueryVo;

/**
 * <p>
 * 优惠券发放记录表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-10-27
 */
@Repository
public interface StoreCouponUserMapper extends BaseMapper<StoreCouponUser> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreCouponUserQueryVo getStoreCouponUserById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param storeCouponUserQueryParam
     * @return
     */
    IPage<StoreCouponUserQueryVo> getStoreCouponUserPageList(@Param("page") Page page,
                                                             @Param("param") StoreCouponUserQueryParam storeCouponUserQueryParam);

}
