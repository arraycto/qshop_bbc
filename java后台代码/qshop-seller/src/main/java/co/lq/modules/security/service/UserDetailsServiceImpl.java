package co.lq.modules.security.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.exception.BadRequestException;
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.modules.system.service.SellerRoleService;
import co.lq.modules.system.service.SellerService;
import co.lq.modules.system.service.dto.SellerDTO;
import co.lq.modules.system.service.dto.SellerDeptDTO;
import co.lq.modules.system.service.dto.SellerJobDTO;

/**
 * @author billy
 * @date 2018-11-22
 */
@Service("userDetailsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SellerService     sellerService;

    private final SellerRoleService sellerRoleService;

    public UserDetailsServiceImpl(SellerService sellerService, SellerRoleService sellerRoleService) {
        this.sellerService = sellerService;
        this.sellerRoleService = sellerRoleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        SellerDTO user = sellerService.findByName(username);
        if (user == null) {
            throw new BadRequestException("账号不存在");
        } else {
            if (!user.getEnabled()) {
                throw new BadRequestException("账号未激活");
            }
            return createJwtUser(user);
        }
    }

    private UserDetails createJwtUser(SellerDTO user) {
        return new JwtUser(user.getId(), user.getUsername(), user.getNickName(), user.getSex(), user.getPassword(),
                user.getAvatar(), user.getEmail(), user.getPhone(),
                Optional.ofNullable(user.getSellerDept()).map(SellerDeptDTO::getName).orElse(null),
                Optional.ofNullable(user.getSellerJob()).map(SellerJobDTO::getName).orElse(null),
                sellerRoleService.mapToGrantedAuthorities(user), user.getEnabled(), user.getCreateTime(),
                user.getLastPasswordResetTime(), user.getStoreId());
    }
}
