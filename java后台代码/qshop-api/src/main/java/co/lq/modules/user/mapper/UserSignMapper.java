package co.lq.modules.user.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.user.entity.UserSign;
import co.lq.modules.user.web.param.UserSignQueryParam;
import co.lq.modules.user.web.vo.UserSignQueryVo;

/**
 * <p>
 * 签到记录表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-12-05
 */
@Repository
public interface UserSignMapper extends BaseMapper<UserSign> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    UserSignQueryVo getUserSignById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param userSignQueryParam
     * @return
     */
    IPage<UserSignQueryVo> getUserSignPageList(@Param("page") Page page,
                                               @Param("param") UserSignQueryParam userSignQueryParam);

}
