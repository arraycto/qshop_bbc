package co.lq.modules.user.service;

import java.io.Serializable;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.user.entity.UserRecharge;
import co.lq.modules.user.web.param.RechargeParam;
import co.lq.modules.user.web.param.UserRechargeQueryParam;
import co.lq.modules.user.web.vo.UserRechargeQueryVo;

/**
 * <p>
 * 用户充值表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-12-08
 */
public interface UserRechargeService extends BaseService<UserRecharge> {

    void updateRecharge(UserRecharge userRecharge);

    UserRecharge getInfoByOrderId(String orderId);

    void addRecharge(RechargeParam param, long uid);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    UserRechargeQueryVo getUserRechargeById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param userRechargeQueryParam
     * @return
     */
    Paging<UserRechargeQueryVo> getUserRechargePageList(UserRechargeQueryParam userRechargeQueryParam) throws Exception;

}
