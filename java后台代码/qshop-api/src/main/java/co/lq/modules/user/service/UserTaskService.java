package co.lq.modules.user.service;

import java.io.Serializable;
import java.util.List;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.user.entity.UserTask;
import co.lq.modules.user.web.dto.TaskDTO;
import co.lq.modules.user.web.dto.UserLevelInfoDTO;
import co.lq.modules.user.web.param.UserTaskQueryParam;
import co.lq.modules.user.web.vo.UserTaskQueryVo;

/**
 * <p>
 * 等级任务设置 服务类
 * </p>
 *
 * @author billy
 * @since 2019-12-06
 */
public interface UserTaskService extends BaseService<UserTask> {

    List<UserTaskQueryVo> tidyTask(List<UserTaskQueryVo> task, long uid);

    int getTaskComplete(long levelId, long uid);

    TaskDTO getTaskList(long levelId, long uid, UserLevelInfoDTO level);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    UserTaskQueryVo getUserTaskById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param userTaskQueryParam
     * @return
     */
    Paging<UserTaskQueryVo> getUserTaskPageList(UserTaskQueryParam userTaskQueryParam) throws Exception;

}
