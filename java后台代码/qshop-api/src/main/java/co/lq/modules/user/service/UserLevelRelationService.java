package co.lq.modules.user.service;

import java.io.Serializable;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.user.entity.UserLevelRelation;
import co.lq.modules.user.web.dto.UserLevelInfoDTO;
import co.lq.modules.user.web.param.UserLevelRelationQueryParam;
import co.lq.modules.user.web.vo.UserLevelRelationQueryVo;

/**
 * <p>
 * 用户等级记录表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-12-06
 */
public interface UserLevelRelationService extends BaseService<UserLevelRelation> {

    void setUserLevel(long uid, long levelId);

    void setLevelComplete(long uid);

    UserLevelInfoDTO getUserLevelInfo(long id);

    long getUserLevel(long uid, int grade);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    UserLevelRelationQueryVo getUserLevelById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param userLevelRelationQueryParam
     * @return
     */
    Paging<UserLevelRelationQueryVo> getUserLevelPageList(UserLevelRelationQueryParam userLevelRelationQueryParam)
            throws Exception;

}
