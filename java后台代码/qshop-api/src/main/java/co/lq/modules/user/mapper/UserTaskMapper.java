package co.lq.modules.user.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.user.entity.UserTask;
import co.lq.modules.user.web.param.UserTaskQueryParam;
import co.lq.modules.user.web.vo.UserTaskQueryVo;

/**
 * <p>
 * 等级任务设置 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-12-06
 */
@Repository
public interface UserTaskMapper extends BaseMapper<UserTask> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    UserTaskQueryVo getSystemUserTaskById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param userTaskQueryParam
     * @return
     */
    IPage<UserTaskQueryVo> getSystemUserTaskPageList(@Param("page") Page page,
                                                     @Param("param") UserTaskQueryParam userTaskQueryParam);

}
