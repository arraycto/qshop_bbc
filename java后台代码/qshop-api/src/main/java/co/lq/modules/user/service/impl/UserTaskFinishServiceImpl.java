package co.lq.modules.user.service.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.modules.user.entity.UserTaskFinish;
import co.lq.modules.user.mapper.UserTaskFinishMapper;
import co.lq.modules.user.service.UserTaskFinishService;
import co.lq.modules.user.web.param.UserTaskFinishQueryParam;
import co.lq.modules.user.web.vo.UserTaskFinishQueryVo;
import co.lq.utils.OrderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户任务完成记录表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-12-07
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserTaskFinishServiceImpl extends BaseServiceImpl<UserTaskFinishMapper, UserTaskFinish>
        implements UserTaskFinishService {

    private final UserTaskFinishMapper userTaskFinishMapper;

    /**
     * 设置任务完成
     *
     * @param uid
     * @param taskId
     */
    @Override
    public void setFinish(long uid, long taskId) {
        QueryWrapper<UserTaskFinish> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid).eq("task_id", taskId);
        int count = userTaskFinishMapper.selectCount(wrapper);
        if (count == 0) {
            UserTaskFinish userTaskFinish = new UserTaskFinish();
            userTaskFinish.setAddTime(OrderUtil.getSecondTimestampTwo());
            userTaskFinish.setUid(uid);
            userTaskFinish.setTaskId(taskId);
            userTaskFinish.setStatus(0);

            userTaskFinishMapper.insert(userTaskFinish);
        }

    }

    @Override
    public UserTaskFinishQueryVo getUserTaskFinishById(Serializable id) throws Exception {
        return userTaskFinishMapper.getUserTaskFinishById(id);
    }

    @Override
    public Paging<UserTaskFinishQueryVo> getUserTaskFinishPageList(UserTaskFinishQueryParam userTaskFinishQueryParam)
            throws Exception {
        Page page = setPageParam(userTaskFinishQueryParam, OrderItem.desc("create_time"));
        IPage<UserTaskFinishQueryVo> iPage = userTaskFinishMapper.getUserTaskFinishPageList(page,
                userTaskFinishQueryParam);
        return new Paging(iPage);
    }

}
