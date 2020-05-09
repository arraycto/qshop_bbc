package co.lq.modules.shop.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.modules.shop.entity.SystemUser;
import co.lq.modules.shop.mapper.SystemUserMapper;
import co.lq.modules.shop.mapping.SellerUserMap;
import co.lq.modules.shop.service.ApiSellerUserService;
import co.lq.modules.shop.web.param.SellerUserQueryParam;
import co.lq.modules.shop.web.vo.SellerUserQueryVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 卖家帐号服务实现
 *
 * @author songbin
 * @since 2020年3月31日 下午3:22:25
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ApiSellerUserServiceImpl extends BaseServiceImpl<SystemUserMapper, SystemUser>
        implements ApiSellerUserService {

    private final SystemUserMapper systemUserMapper;

    private final SellerUserMap    sellerUserMap;

    @Override
    public SellerUserQueryVo getSellerUserById(Serializable id) {
        return systemUserMapper.getSellerUserById(id);
    }

    @Override
    public List<SellerUserQueryVo> getList() {
        QueryWrapper<SystemUser> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0).orderByDesc("add_time");
        List<SellerUserQueryVo> list = sellerUserMap.toDto(baseMapper.selectList(wrapper));
        return list;
    }

    @Override
    public SellerUserQueryVo getSellerUserByUsername(SellerUserQueryParam sellerUserQueryParam) {
        return systemUserMapper.getSellerUserByUsername(sellerUserQueryParam);
    }

    @Override
    public Boolean saveSellerUser(SystemUser systemUser) {
        if (systemUser.getId() == null) {
            systemUserMapper.insert(systemUser);
        } else {
            systemUserMapper.updateById(systemUser);
        }

        return true;
    }
}
