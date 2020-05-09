package co.lq.modules.activity.service;

import java.io.Serializable;
import java.util.List;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.activity.entity.StoreBargainUserHelp;
import co.lq.modules.activity.web.param.StoreBargainUserHelpQueryParam;
import co.lq.modules.activity.web.vo.StoreBargainUserHelpQueryVo;

/**
 * <p>
 * 砍价用户帮助表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-12-21
 */
public interface ApiStoreBargainUserHelpService extends BaseService<StoreBargainUserHelp> {

    List<StoreBargainUserHelpQueryVo> getList(long bargainId, long bargainUserUid, int page, int limit);

    int getBargainUserHelpPeopleCount(long bargainId, long bargainUserUid);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreBargainUserHelpQueryVo getStoreBargainUserHelpById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param storeBargainUserHelpQueryParam
     * @return
     */
    Paging<StoreBargainUserHelpQueryVo> getStoreBargainUserHelpPageList(StoreBargainUserHelpQueryParam storeBargainUserHelpQueryParam)
            throws Exception;

}
