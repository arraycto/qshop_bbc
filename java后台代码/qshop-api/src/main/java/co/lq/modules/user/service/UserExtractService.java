package co.lq.modules.user.service;

import java.io.Serializable;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.user.entity.UserExtract;
import co.lq.modules.user.web.param.UserExtParam;
import co.lq.modules.user.web.param.UserExtractQueryParam;
import co.lq.modules.user.web.vo.UserExtractQueryVo;

/**
 * <p>
 * 用户提现表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-11-11
 */
public interface UserExtractService extends BaseService<UserExtract> {

    void userExtract(long uid, UserExtParam param);

    double extractSum(long uid);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    UserExtractQueryVo getUserExtractById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param userExtractQueryParam
     * @return
     */
    Paging<UserExtractQueryVo> getUserExtractPageList(UserExtractQueryParam userExtractQueryParam) throws Exception;

}
