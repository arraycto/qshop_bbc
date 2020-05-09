package co.lq.modules.user.service;

import java.io.Serializable;
import java.util.List;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.user.entity.UserLevel;
import co.lq.modules.user.web.dto.UserLevelDTO;
import co.lq.modules.user.web.param.UserLevelQueryParam;
import co.lq.modules.user.web.vo.UserLevelQueryVo;

/**
 * <p>
 * 设置用户等级表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-12-06
 */
public interface UserLevelService extends BaseService<UserLevel> {

    int getNextLevelId(long levelId);

    boolean getClear(long levelId);

    List<UserLevelQueryVo> getLevelListAndGrade(Long levelId, boolean isTask);

    UserLevelDTO getLevelInfo(long uid, boolean isTask);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    UserLevelQueryVo getUserLevelById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param userLevelQueryParam
     * @return
     */
    Paging<UserLevelQueryVo> getUserLevelPageList(UserLevelQueryParam userLevelQueryParam) throws Exception;

}
