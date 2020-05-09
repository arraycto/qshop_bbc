package co.lq.modules.shop.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.entity.StoreCouponIssueUser;
import co.lq.modules.shop.web.param.StoreCouponIssueUserQueryParam;
import co.lq.modules.shop.web.vo.StoreCouponIssueUserQueryVo;

/**
 * <p>
 * 优惠券前台用户领取记录表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-10-27
 */
@Repository
public interface StoreCouponIssueUserMapper extends BaseMapper<StoreCouponIssueUser> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreCouponIssueUserQueryVo getStoreCouponIssueUserById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param storeCouponIssueUserQueryParam
     * @return
     */
    IPage<StoreCouponIssueUserQueryVo> getStoreCouponIssueUserPageList(@Param("page") Page page,
                                                                       @Param("param") StoreCouponIssueUserQueryParam storeCouponIssueUserQueryParam);

}
