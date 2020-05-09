package co.lq.modules.user.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.user.entity.UserTaskFinish;
import co.lq.modules.user.web.param.UserTaskFinishQueryParam;
import co.lq.modules.user.web.vo.UserTaskFinishQueryVo;

/**
 * <p>
 * 用户任务完成记录表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-12-07
 */
@Repository
public interface UserTaskFinishMapper extends BaseMapper<UserTaskFinish> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    UserTaskFinishQueryVo getUserTaskFinishById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param userTaskFinishQueryParam
     * @return
     */
    IPage<UserTaskFinishQueryVo> getUserTaskFinishPageList(@Param("page") Page page,
                                                           @Param("param") UserTaskFinishQueryParam userTaskFinishQueryParam);

}
