package co.lq.modules.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.exception.BadRequestException;
import co.lq.exception.ErrorRequestException;
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.modules.user.entity.User;
import co.lq.modules.user.service.UserService;
import lombok.AllArgsConstructor;

/**
 * @author billy
 * @date 2020/01/12
 */
@Service("userDetailsService")
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService          userService;
    private final JwtPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userService.findByName(username);
        if (user == null) {
            throw new BadRequestException("账号不存在");
        } else {
            if (!user.getStatus()) {
                throw new ErrorRequestException("账号未激活");
            }
            return createJwtUser(user);
        }
    }

    private UserDetails createJwtUser(User user) {
        return new JwtUser(Long.valueOf(user.getUid()), user.getUsername(), user.getNickname(), user.getPassword(),
                user.getAvatar(), user.getPhone(), permissionService.mapToGrantedAuthorities(user), user.getStatus(),
                user.getAddTime());
    }
}
