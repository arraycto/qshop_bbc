package co.lq.modules.user.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.user.entity.UserRecharge;
import co.lq.modules.user.web.param.UserRechargeQueryParam;
import co.lq.modules.user.web.vo.UserRechargeQueryVo;

/**
 * <p>
 * 用户充值表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-12-08
 */
@Repository
public interface UserRechargeMapper extends BaseMapper<UserRecharge> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    UserRechargeQueryVo getUserRechargeById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param userRechargeQueryParam
     * @return
     */
    IPage<UserRechargeQueryVo> getUserRechargePageList(@Param("page") Page page,
                                                       @Param("param") UserRechargeQueryParam userRechargeQueryParam);

}
