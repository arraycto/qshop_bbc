package co.lq.modules.user.service;

import java.io.Serializable;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.user.entity.UserTaskFinish;
import co.lq.modules.user.web.param.UserTaskFinishQueryParam;
import co.lq.modules.user.web.vo.UserTaskFinishQueryVo;

/**
 * <p>
 * 用户任务完成记录表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-12-07
 */
public interface UserTaskFinishService extends BaseService<UserTaskFinish> {

    void setFinish(long uid, long taskId);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    UserTaskFinishQueryVo getUserTaskFinishById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param userTaskFinishQueryParam
     * @return
     */
    Paging<UserTaskFinishQueryVo> getUserTaskFinishPageList(UserTaskFinishQueryParam userTaskFinishQueryParam)
            throws Exception;

}
