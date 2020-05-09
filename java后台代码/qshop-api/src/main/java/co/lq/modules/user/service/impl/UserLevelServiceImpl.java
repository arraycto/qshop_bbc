package co.lq.modules.user.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.util.ObjectUtil;
import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.modules.user.entity.UserLevel;
import co.lq.modules.user.mapper.UserLevelMapper;
import co.lq.modules.user.mapping.UserLevelMap;
import co.lq.modules.user.service.UserLevelRelationService;
import co.lq.modules.user.service.UserLevelService;
import co.lq.modules.user.service.UserTaskService;
import co.lq.modules.user.web.dto.TaskDTO;
import co.lq.modules.user.web.dto.UserLevelDTO;
import co.lq.modules.user.web.dto.UserLevelInfoDTO;
import co.lq.modules.user.web.param.UserLevelQueryParam;
import co.lq.modules.user.web.vo.UserLevelQueryVo;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 设置用户等级表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-12-06
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserLevelServiceImpl extends BaseServiceImpl<UserLevelMapper, UserLevel> implements UserLevelService {

    @Autowired
    private UserLevelMapper          userLevelMapper;
    @Autowired
    private UserLevelMap             userLevelMap;
    @Autowired
    private UserLevelRelationService userLevelRelationService;
    @Autowired
    private UserTaskService          userTaskService;

    /**
     * 获取当前的下一个会员id
     *
     * @param levelId
     * @return
     */
    @Override
    public int getNextLevelId(long levelId) {
        QueryWrapper<UserLevel> wrapper = new QueryWrapper<>();
        wrapper.eq("is_del", 0).eq("is_show", 1).orderByAsc("grade");
        List<UserLevel> list = userLevelMapper.selectList(wrapper);
        int grade = 0;
        for (UserLevel userLevel : list) {
            if (userLevel.getId() == levelId)
                grade = userLevel.getGrade();
        }

        QueryWrapper<UserLevel> wrapperT = new QueryWrapper<>();
        wrapperT.eq("is_del", 0).eq("is_show", 1).orderByAsc("grade").gt("grade", grade).last("limit 1");
        UserLevel userLevel = userLevelMapper.selectOne(wrapperT);
        if (ObjectUtil.isNull(userLevel))
            return 0;
        return userLevel.getId();
    }

    @Override
    public boolean getClear(long levelId) {
        List<UserLevelQueryVo> userLevelQueryVos = getLevelListAndGrade(levelId, false);
        for (UserLevelQueryVo userLevelQueryVo : userLevelQueryVos) {
            if (userLevelQueryVo.getId() == levelId)
                return userLevelQueryVo.getIsClear();
        }
        return false;
    }

    /**
     * 获取会员等级列表
     *
     * @param levelId
     * @param isTask
     * @return
     */
    @Override
    public List<UserLevelQueryVo> getLevelListAndGrade(Long levelId, boolean isTask) {
        int grade = 0;
        QueryWrapper<UserLevel> wrapperT = new QueryWrapper<>();
        wrapperT.eq("is_del", 0).eq("is_show", 1).orderByAsc("grade");
        List<UserLevel> list = userLevelMapper.selectList(wrapperT);
        List<UserLevelQueryVo> newList = userLevelMap.toDto(list);
        for (UserLevelQueryVo userLevelQueryVo : newList) {
            if (userLevelQueryVo.getId().equals(levelId)) {
                grade = userLevelQueryVo.getGrade();
            }

            if (grade < userLevelQueryVo.getGrade()) {
                userLevelQueryVo.setIsClear(true);
            } else {
                userLevelQueryVo.setIsClear(false);//开启会员解锁
            }
        }
        return newList;
    }

    /**
     * 获取会员等级列表
     *
     * @return
     */
    @Override
    public UserLevelDTO getLevelInfo(long uid, boolean isTask) {
        //用户当前等级id
        long id = userLevelRelationService.getUserLevel(uid, 0);
        UserLevelInfoDTO userLevelInfoDTO = null;
        if (id > 0) {
            userLevelInfoDTO = userLevelRelationService.getUserLevelInfo(id);
        }
        long levelId = 0;
        if (ObjectUtil.isNotNull(userLevelInfoDTO)) {
            levelId = userLevelInfoDTO.getId();
        }
        List<UserLevelQueryVo> list = getLevelListAndGrade(levelId, false);
        TaskDTO taskDTO = userTaskService.getTaskList(list.get(0).getId(), uid, null);

        UserLevelDTO userLevelDTO = new UserLevelDTO();
        userLevelDTO.setList(list);
        userLevelDTO.setTask(taskDTO);

        return userLevelDTO;
    }

    @Override
    public UserLevelQueryVo getUserLevelById(Serializable id) {
        return userLevelMapper.getUserLevelById(id);
    }

    @Override
    public Paging<UserLevelQueryVo> getUserLevelPageList(UserLevelQueryParam userLevelQueryParam) throws Exception {
        Page page = setPageParam(userLevelQueryParam, OrderItem.desc("create_time"));
        IPage<UserLevelQueryVo> iPage = userLevelMapper.getUserLevelPageList(page, userLevelQueryParam);
        return new Paging(iPage);
    }

}
