package co.lq.modules.user.service;

import java.io.Serializable;
import java.util.List;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.user.entity.UserSign;
import co.lq.modules.user.web.dto.SignDTO;
import co.lq.modules.user.web.param.UserSignQueryParam;
import co.lq.modules.user.web.vo.UserSignQueryVo;

/**
 * <p>
 * 签到记录表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-12-05
 */
public interface UserSignService extends BaseService<UserSign> {

    int sign(long uid);

    List<SignDTO> getSignList(long uid, int page, int limit);

    boolean getYesterDayIsSign(long uid);

    boolean getToDayIsSign(long uid);

    int getSignSumDay(long uid);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    UserSignQueryVo getUserSignById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param userSignQueryParam
     * @return
     */
    Paging<UserSignQueryVo> getUserSignPageList(UserSignQueryParam userSignQueryParam) throws Exception;

}
