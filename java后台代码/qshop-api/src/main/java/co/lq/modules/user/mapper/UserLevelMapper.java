package co.lq.modules.user.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.user.entity.UserLevel;
import co.lq.modules.user.web.param.UserLevelQueryParam;
import co.lq.modules.user.web.vo.UserLevelQueryVo;

/**
 * <p>
 * 设置用户等级表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-12-06
 */
@Repository
public interface UserLevelMapper extends BaseMapper<UserLevel> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    UserLevelQueryVo getUserLevelById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param userLevelQueryParam
     * @return
     */
    IPage<UserLevelQueryVo> getUserLevelPageList(@Param("page") Page page,
                                                 @Param("param") UserLevelQueryParam userLevelQueryParam);

}
