package co.lq.modules.user.service.impl;

import java.io.Serializable;

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
import co.lq.modules.user.entity.User;
import co.lq.modules.user.entity.UserLevelRelation;
import co.lq.modules.user.mapper.UserLevelRelationMapper;
import co.lq.modules.user.mapper.UserTaskMapper;
import co.lq.modules.user.service.UserLevelRelationService;
import co.lq.modules.user.service.UserLevelService;
import co.lq.modules.user.service.UserService;
import co.lq.modules.user.service.UserTaskService;
import co.lq.modules.user.web.dto.UserLevelInfoDTO;
import co.lq.modules.user.web.param.UserLevelRelationQueryParam;
import co.lq.modules.user.web.vo.UserLevelQueryVo;
import co.lq.modules.user.web.vo.UserLevelRelationQueryVo;
import co.lq.modules.user.web.vo.UserQueryVo;
import co.lq.utils.OrderUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户等级记录表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-12-06
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserLevelRelationServiceImpl extends BaseServiceImpl<UserLevelRelationMapper, UserLevelRelation>
        implements UserLevelRelationService {

    @Autowired
    private UserLevelRelationMapper userLevelRelationMapper;
    @Autowired
    private UserTaskMapper          userTaskMapper;

    @Autowired
    private UserService             userService;
    @Autowired
    private UserLevelService        userLevelService;
    @Autowired
    private UserTaskService         userTaskService;

    /**
     * 设置会员等级
     *
     * @param uid
     * @param levelId
     */
    @Override
    public void setUserLevel(long uid, long levelId) {
        UserLevelQueryVo userLevelQueryVo = userLevelService.getUserLevelById(levelId);
        if (ObjectUtil.isNull(userLevelQueryVo)) {
            return;
        }

        int validTime = userLevelQueryVo.getValidDate() * 86400;

        QueryWrapper<UserLevelRelation> wrapper = new QueryWrapper<>();
        wrapper.eq("is_del", 0).eq("status", 1).eq("uid", uid).eq("level_id", levelId).last("limit 1");
        UserLevelRelation userLevelRelation = userLevelRelationMapper.selectOne(wrapper);
        if (ObjectUtil.isNotNull(userLevelRelation)) {
            //todo
        } else {
            UserLevelRelation UserLevelRelation = new UserLevelRelation();
            UserLevelRelation.setIsForever(userLevelQueryVo.getIsForever());
            UserLevelRelation.setStatus(1);
            UserLevelRelation.setIsDel(0);
            UserLevelRelation.setGrade(userLevelQueryVo.getGrade());
            UserLevelRelation.setUid(uid);
            UserLevelRelation.setAddTime(OrderUtil.getSecondTimestampTwo());
            UserLevelRelation.setLevelId(levelId);
            UserLevelRelation.setDiscount(userLevelQueryVo.getDiscount().intValue());

            if (userLevelQueryVo.getIsForever() == 1) {
                UserLevelRelation.setValidTime(0);
            } else {
                UserLevelRelation.setValidTime(validTime + OrderUtil.getSecondTimestampTwo());
            }

            UserLevelRelation.setMark("恭喜你成为了" + userLevelQueryVo.getName());
            userLevelRelationMapper.insert(UserLevelRelation);

            User user = new User();
            user.setLevel(levelId);
            user.setUid(uid);
            userService.updateById(user);
        }

    }

    /**
     * 检查是否能成为会员
     *
     * @param uid
     */
    @Override
    public void setLevelComplete(long uid) {
        UserQueryVo userQueryVo = userService.getUserById(uid);
        if (ObjectUtil.isNull(userQueryVo)) {
            return;
        }

        long levelId = getUserLevel(uid, 9);

        int nextLevelId = userLevelService.getNextLevelId(levelId);
        if (nextLevelId == 0) {
            return;
        }

        int finishCount = userTaskService.getTaskComplete(nextLevelId, uid);
        if (finishCount == 3) {
            setUserLevel(uid, nextLevelId);
        }

    }

    @Override
    public UserLevelInfoDTO getUserLevelInfo(long id) {
        return userLevelRelationMapper.getUserLevelInfo(id);
    }

    /**
     * 获取当前用户会员等级返回当前用户等级id
     *
     * @param uid
     * @param grade
     * @return
     */
    @Override
    public long getUserLevel(long uid, int grade) {
        QueryWrapper<UserLevelRelation> wrapper = new QueryWrapper<>();
        wrapper.eq("is_del", 0).eq("status", 1).eq("uid", uid).orderByDesc("grade");
        if (grade > 0)
            wrapper.lt("grade", grade);
        UserLevelRelation userLevelRelation = userLevelRelationMapper.selectOne(wrapper);
        if (ObjectUtil.isNull(userLevelRelation))
            return 0;
        if (userLevelRelation.getIsForever() == 1)
            return userLevelRelation.getId();
        int nowTime = OrderUtil.getSecondTimestampTwo();
        if (nowTime > userLevelRelation.getValidTime()) {
            if (userLevelRelation.getStatus() == 1) {
                userLevelRelation.setStatus(0);
                userLevelRelationMapper.updateById(userLevelRelation);
            }

            return getUserLevel(uid, userLevelRelation.getGrade());
        }
        return userLevelRelation.getId();
    }

    @Override
    public UserLevelRelationQueryVo getUserLevelById(Serializable id) throws Exception {
        return userLevelRelationMapper.getUserLevelById(id);
    }

    @Override
    public Paging<UserLevelRelationQueryVo> getUserLevelPageList(UserLevelRelationQueryParam userLevelRelationQueryParam)
            throws Exception {
        Page page = setPageParam(userLevelRelationQueryParam, OrderItem.desc("create_time"));
        IPage<UserLevelRelationQueryVo> iPage = userLevelRelationMapper.getUserLevelPageList(page,
                userLevelRelationQueryParam);
        return new Paging(iPage);
    }

}
