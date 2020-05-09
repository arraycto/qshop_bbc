package co.lq.modules.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import co.lq.modules.user.entity.User;

@Service
public class JwtPermissionService {

    /**
     * key的名称如有修改，请同步修改 UserServiceImpl 中的 update 方法
     *
     * @param user
     * @return
     */

    public Collection<GrantedAuthority> mapToGrantedAuthorities(User user) {

        System.out.println("--------------------loadPermissionByUser:" + user.getUsername() + "---------------------");

        List<String> list = new ArrayList<>();
        list.add("yshop");

        return list.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
