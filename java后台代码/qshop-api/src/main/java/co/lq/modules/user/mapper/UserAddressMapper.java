package co.lq.modules.user.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.user.entity.UserAddress;
import co.lq.modules.user.web.param.UserAddressQueryParam;
import co.lq.modules.user.web.vo.UserAddressQueryVo;

/**
 * <p>
 * 用户地址表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-10-28
 */
@Repository
public interface UserAddressMapper extends BaseMapper<UserAddress> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    UserAddressQueryVo getUserAddressById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param userAddressQueryParam
     * @return
     */
    IPage<UserAddressQueryVo> getUserAddressPageList(@Param("page") Page page,
                                                     @Param("param") UserAddressQueryParam userAddressQueryParam);

}
