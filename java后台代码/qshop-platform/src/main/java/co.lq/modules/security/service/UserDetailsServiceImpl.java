package co.lq.modules.security.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.exception.BadRequestException;
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.modules.system.service.PlatformRoleService;
import co.lq.modules.system.service.PlatformUserService;
import co.lq.modules.system.service.dto.PlatformDeptDTO;
import co.lq.modules.system.service.dto.PlatformJobDTO;
import co.lq.modules.system.service.dto.PlatformUserDTO;

/**
 * @author billy
 * @date 2018-11-22
 */
@Service("userDetailsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PlatformUserService platformUserService;

    private final PlatformRoleService platformRoleService;

    public UserDetailsServiceImpl(PlatformUserService platformUserService, PlatformRoleService platformRoleService) {
        this.platformUserService = platformUserService;
        this.platformRoleService = platformRoleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        PlatformUserDTO user = platformUserService.findByName(username);
        if (user == null) {
            throw new BadRequestException("账号不存在");
        } else {
            if (!user.getEnabled()) {
                throw new BadRequestException("账号未激活");
            }
            return createJwtUser(user);
        }
    }

    private UserDetails createJwtUser(PlatformUserDTO user) {
        return new JwtUser(user.getId(), user.getUsername(), user.getNickName(), user.getSex(), user.getPassword(),
                user.getAvatar(), user.getEmail(), user.getPhone(),
                Optional.ofNullable(user.getPlatformDept()).map(PlatformDeptDTO::getName).orElse(null),
                Optional.ofNullable(user.getPlatformJob()).map(PlatformJobDTO::getName).orElse(null),
                platformRoleService.mapToGrantedAuthorities(user), user.getEnabled(), user.getCreateTime(),
                user.getLastPasswordResetTime(), user.getStoreId());
    }
}
