package co.lq.modules.user.service;

import java.io.Serializable;
import java.util.List;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.user.entity.User;
import co.lq.modules.user.entity.UserLevelRelation;
import co.lq.modules.user.web.dto.PromUserDTO;
import co.lq.modules.user.web.param.PromParam;
import co.lq.modules.user.web.param.UserQueryParam;
import co.lq.modules.user.web.vo.UserQueryVo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-10-16
 */
public interface UserService extends BaseService<User> {

    double setLevelPrice(double price, long uid);

    UserLevelRelation getUserLeve(long uid);

    void incMoney(long uid, double price);

    void incIntegral(long uid, double integral);

    UserQueryVo getNewUserById(Serializable id);

    void setUserSpreadCount(long uid);

    int getSpreadCount(long uid, int type);

    List<PromUserDTO> getUserSpreadGrade(PromParam promParam, long uid);

    boolean setSpread(long spread, long uid);

    void decIntegral(long uid, double integral);

    void incPayCount(long uid);

    void decPrice(long uid, double payPrice);

    User findByName(String name);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    UserQueryVo getUserById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param userQueryParam
     * @return
     */
    Paging<UserQueryVo> getUserPageList(UserQueryParam userQueryParam) throws Exception;

}
