package co.lq.modules.user.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.user.entity.UserLevelRelation;
import co.lq.modules.user.web.dto.UserLevelInfoDTO;
import co.lq.modules.user.web.param.UserLevelRelationQueryParam;
import co.lq.modules.user.web.vo.UserLevelRelationQueryVo;

/**
 * <p>
 * 用户等级记录表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-12-06
 */
@Repository
public interface UserLevelRelationMapper extends BaseMapper<UserLevelRelation> {

    @Select("SELECT l.id,a.add_time as addTime,l.discount,a.level_id as levelId,l.name,"
            + "l.icon,l.grade FROM user_level_relation a INNER JOIN user_level l "
            + "ON l.id=a.level_id WHERE a.status = 1 AND a.is_del = 0 AND a.id = #{id} LIMIT 1")
    UserLevelInfoDTO getUserLevelInfo(long id);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    UserLevelRelationQueryVo getUserLevelById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param userLevelRelationQueryParam
     * @return
     */
    IPage<UserLevelRelationQueryVo> getUserLevelPageList(@Param("page") Page page,
                                                         @Param("param") UserLevelRelationQueryParam userLevelRelationQueryParam);

}
