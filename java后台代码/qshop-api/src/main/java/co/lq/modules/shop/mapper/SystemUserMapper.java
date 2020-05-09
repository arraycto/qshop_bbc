package co.lq.modules.shop.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.entity.SystemUser;
import co.lq.modules.shop.web.param.SellerUserQueryParam;
import co.lq.modules.shop.web.vo.SellerUserQueryVo;

/**
 * 卖家帐号
 *
 * @author songbin
 * @since 2020年3月31日 下午2:57:03
 */
@Repository
public interface SystemUserMapper extends BaseMapper<SystemUser> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SellerUserQueryVo getSellerUserById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param sellerUserQueryParam
     * @return
     */
    IPage<SellerUserQueryVo> getSellerUserPageList(@Param("page") Page page,
                                                   @Param("param") SellerUserQueryParam sellerUserQueryParam);

    /**
     * 根据ID获取查询对象
     *
     * @param sellerUserQueryParam
     * @return
     */
    SellerUserQueryVo getSellerUserByUsername(@Param("param") SellerUserQueryParam sellerUserQueryParam);

}
