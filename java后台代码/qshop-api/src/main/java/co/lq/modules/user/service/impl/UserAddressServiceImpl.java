package co.lq.modules.user.service.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.modules.user.entity.UserAddress;
import co.lq.modules.user.mapper.UserAddressMapper;
import co.lq.modules.user.service.UserAddressService;
import co.lq.modules.user.web.param.UserAddressQueryParam;
import co.lq.modules.user.web.vo.UserAddressQueryVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户地址表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-10-28
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserAddressServiceImpl extends BaseServiceImpl<UserAddressMapper, UserAddress>
        implements UserAddressService {

    private final UserAddressMapper userAddressMapper;

    @Override
    public UserAddressQueryVo getUserAddressById(Serializable id) {
        return userAddressMapper.getUserAddressById(id);
    }

    @Override
    public UserAddress getUserDefaultAddress(long uid) {
        QueryWrapper<UserAddress> wrapper = new QueryWrapper<>();
        wrapper.eq("is_default", 1).eq("uid", uid).eq("is_del", 0).last("limit 1");
        return getOne(wrapper);
    }

    @Override
    public Paging<UserAddressQueryVo> getUserAddressPageList(UserAddressQueryParam userAddressQueryParam) {
        Page page = setPageParam(userAddressQueryParam, OrderItem.desc("add_time"));
        IPage<UserAddressQueryVo> iPage = userAddressMapper.getUserAddressPageList(page, userAddressQueryParam);
        return new Paging(iPage);
    }

}
