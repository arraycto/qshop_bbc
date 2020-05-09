package co.lq.modules.user.service;

import java.io.Serializable;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.user.entity.UserAddress;
import co.lq.modules.user.web.param.UserAddressQueryParam;
import co.lq.modules.user.web.vo.UserAddressQueryVo;

/**
 * <p>
 * 用户地址表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-10-28
 */
public interface UserAddressService extends BaseService<UserAddress> {

    UserAddress getUserDefaultAddress(long uid);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    UserAddressQueryVo getUserAddressById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param userAddressQueryParam
     * @return
     */
    Paging<UserAddressQueryVo> getUserAddressPageList(UserAddressQueryParam userAddressQueryParam);

}
