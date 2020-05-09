package co.lq.modules.user.service.impl;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.util.NumberUtil;
import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.modules.order.mapper.StoreOrderMapper;
import co.lq.modules.user.entity.UserTask;
import co.lq.modules.user.entity.UserTaskFinish;
import co.lq.modules.user.mapper.UserBillMapper;
import co.lq.modules.user.mapper.UserTaskFinishMapper;
import co.lq.modules.user.mapper.UserTaskMapper;
import co.lq.modules.user.mapping.UserTaskMap;
import co.lq.modules.user.service.UserBillService;
import co.lq.modules.user.service.UserTaskFinishService;
import co.lq.modules.user.service.UserTaskService;
import co.lq.modules.user.web.dto.TaskDTO;
import co.lq.modules.user.web.dto.UserLevelInfoDTO;
import co.lq.modules.user.web.param.UserTaskQueryParam;
import co.lq.modules.user.web.vo.UserTaskQueryVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 等级任务设置 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-12-06
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserTaskServiceImpl extends BaseServiceImpl<UserTaskMapper, UserTask> implements UserTaskService {

    private final UserTaskMapper        userTaskMapper;
    private final UserTaskFinishMapper  userTaskFinishMapper;
    private final UserBillMapper        userBillMapper;
    private final StoreOrderMapper      storeOrderMapper;

    private final UserTaskFinishService userTaskFinishService;
    private final UserBillService       userBillService;

    private final UserTaskMap           userTaskMap;

    /**
     * 设置任务内容完成情况
     *
     * @param task
     * @return
     */
    @Override
    public List<UserTaskQueryVo> tidyTask(List<UserTaskQueryVo> task, long uid) {
        QueryWrapper<UserTaskFinish> wrapper = new QueryWrapper<>();
        for (UserTaskQueryVo taskQueryVo : task) {
            wrapper.in("task_id", taskQueryVo.getId()).eq("uid", uid);
            int count = userTaskFinishMapper.selectCount(wrapper);
            if (count > 0) {
                taskQueryVo.setNewNumber(taskQueryVo.getNumber());
                taskQueryVo.setSpeed(100);
                taskQueryVo.setFinish(1);
                taskQueryVo.setTaskTypeTitle("");
            } else {
                double sumNumber = 0d;
                String title = "";
                switch (taskQueryVo.getTaskType()) {
                    case "SatisfactionIntegral":
                        sumNumber = userBillMapper.sumIntegral(uid);
                        title = "还需要{0}经验";
                        break;
                    case "ConsumptionAmount":
                        sumNumber = storeOrderMapper.sumPrice(uid);
                        title = "还需消费{0}元";
                        break;
                    case "CumulativeAttendance":
                        sumNumber = userBillService.cumulativeAttendance(uid);
                        title = "还需签到{0}天";
                        break;
                    default:
                        sumNumber = userBillMapper.sumIntegral(uid);
                        title = "还需要{0}经验";
                        break;
                }

                //System.out.println("sumNumber:"+sumNumber);
                //System.out.println("sumNumber2:"+taskQueryVo.getNumber());
                if (sumNumber >= taskQueryVo.getNumber()) {
                    userTaskFinishService.setFinish(uid, taskQueryVo.getId());
                    taskQueryVo.setFinish(1);
                    taskQueryVo.setSpeed(100);
                    taskQueryVo.setTaskTypeTitle("");
                    taskQueryVo.setNewNumber(taskQueryVo.getNumber());
                } else {
                    double numdata = NumberUtil.sub(taskQueryVo.getNumber().doubleValue(), sumNumber);
                    taskQueryVo.setTaskTypeTitle(MessageFormat.format(title, numdata));
                    double speed = NumberUtil.div(sumNumber, taskQueryVo.getNumber().doubleValue());
                    taskQueryVo.setSpeed(Double.valueOf(NumberUtil.mul(speed, 100)).intValue());
                    taskQueryVo.setFinish(0);
                    taskQueryVo.setNewNumber(Double.valueOf(sumNumber).intValue());
                }
            }
        }

        return task;
    }

    /**
     * 后去已经完成的任务数量
     *
     * @param levelId
     * @param uid
     * @return
     */
    @Override
    public int getTaskComplete(long levelId, long uid) {
        QueryWrapper<UserTask> wrapper = new QueryWrapper<>();
        wrapper.eq("level_id", levelId).eq("is_show", 1);
        List<UserTask> list = userTaskMapper.selectList(wrapper);
        List<Integer> taskIds = list.stream().map(UserTask::getId).collect(Collectors.toList());

        QueryWrapper<UserTaskFinish> wrapperT = new QueryWrapper<>();
        wrapperT.in("task_id", taskIds).eq("uid", uid);
        int count = userTaskFinishMapper.selectCount(wrapperT);
        //System.out.println("count:"+count);
        return count;
    }

    /**
     * 获取等级会员任务列表
     *
     * @param levelId
     * @param uid
     * @param level
     * @return
     */
    @Override
    public TaskDTO getTaskList(long levelId, long uid, UserLevelInfoDTO level) {
        QueryWrapper<UserTask> wrapper = new QueryWrapper<>();
        wrapper.eq("level_id", levelId).eq("is_show", 1).orderByDesc("sort");
        List<UserTaskQueryVo> list = userTaskMap.toDto(userTaskMapper.selectList(wrapper));

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setList(list);
        taskDTO.setReachCount(getTaskComplete(levelId, uid));
        taskDTO.setTask(tidyTask(list, uid));

        return taskDTO;
    }

    @Override
    public UserTaskQueryVo getUserTaskById(Serializable id) throws Exception {
        return userTaskMapper.getSystemUserTaskById(id);
    }

    @Override
    public Paging<UserTaskQueryVo> getUserTaskPageList(UserTaskQueryParam userTaskQueryParam) throws Exception {
        Page page = setPageParam(userTaskQueryParam, OrderItem.desc("create_time"));
        IPage<UserTaskQueryVo> iPage = userTaskMapper.getSystemUserTaskPageList(page, userTaskQueryParam);
        return new Paging(iPage);
    }

}
