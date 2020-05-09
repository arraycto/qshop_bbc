package co.lq.modules.activity.service;

import java.io.Serializable;
import java.util.List;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.activity.entity.StoreBargainUser;
import co.lq.modules.activity.web.param.StoreBargainUserQueryParam;
import co.lq.modules.activity.web.vo.StoreBargainUserQueryVo;

/**
 * <p>
 * 用户参与砍价表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-12-21
 */
public interface ApiStoreBargainUserService extends BaseService<StoreBargainUser> {

    void setBargainUserStatus(long bargainId, long uid);

    void bargainCancel(long bargainId, long uid);

    List<StoreBargainUserQueryVo> bargainUserList(long bargainUserUid, int page, int limit);

    boolean isBargainUserHelp(long bargainId, long bargainUserUid, long uid);

    void setBargain(Long bargainId, Long uid);

    double getBargainUserDiffPrice(long id);

    StoreBargainUser getBargainUserInfo(long bargainId, long uid);

    List<StoreBargainUserQueryVo> getBargainUserList(long bargainId, int status);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreBargainUserQueryVo getStoreBargainUserById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param storeBargainUserQueryParam
     * @return
     */
    Paging<StoreBargainUserQueryVo> getStoreBargainUserPageList(StoreBargainUserQueryParam storeBargainUserQueryParam)
            throws Exception;

}
