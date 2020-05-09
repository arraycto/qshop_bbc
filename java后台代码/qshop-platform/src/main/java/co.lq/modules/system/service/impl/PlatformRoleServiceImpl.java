package co.lq.modules.system.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.exception.EntityExistException;
import co.lq.modules.system.domain.PlatformMenu;
import co.lq.modules.system.domain.PlatformRole;
import co.lq.modules.system.repository.PlatformRoleRepository;
import co.lq.modules.system.service.PlatformRoleService;
import co.lq.modules.system.service.dto.PlatformRoleDTO;
import co.lq.modules.system.service.dto.PlatformUserDTO;
import co.lq.modules.system.service.dto.RoleQueryCriteria;
import co.lq.modules.system.service.dto.RoleSmallDTO;
import co.lq.modules.system.service.mapper.RoleMapper;
import co.lq.modules.system.service.mapper.RoleSmallMapper;
import co.lq.utils.FileUtil;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.StringUtils;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2018-12-03
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PlatformRoleServiceImpl implements PlatformRoleService {

    private final PlatformRoleRepository platformRoleRepository;

    private final RoleMapper             roleMapper;

    private final RoleSmallMapper        roleSmallMapper;

    public PlatformRoleServiceImpl(PlatformRoleRepository platformRoleRepository, RoleMapper roleMapper,
                                   RoleSmallMapper roleSmallMapper) {
        this.platformRoleRepository = platformRoleRepository;
        this.roleMapper = roleMapper;
        this.roleSmallMapper = roleSmallMapper;
    }

    @Override
    public Object queryAll(Pageable pageable) {
        return roleMapper.toDto(platformRoleRepository.findAll(pageable).getContent());
    }

    @Override
    public List<PlatformRoleDTO> queryAll(RoleQueryCriteria criteria) {
        return roleMapper.toDto(platformRoleRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public Object queryAll(RoleQueryCriteria criteria, Pageable pageable) {
        Page<PlatformRole> page = platformRoleRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(roleMapper::toDto));
    }

    @Override
    public PlatformRoleDTO findById(long id) {
        PlatformRole platformRole = platformRoleRepository.findById(id).orElseGet(PlatformRole::new);
        ValidationUtil.isNull(platformRole.getId(), "PlatformRole", "id", id);
        return roleMapper.toDto(platformRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PlatformRoleDTO create(PlatformRole resources) {
        if (platformRoleRepository.findByName(resources.getName()) != null) {
            throw new EntityExistException(PlatformRole.class, "username", resources.getName());
        }
        return roleMapper.toDto(platformRoleRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PlatformRole resources) {
        PlatformRole platformRole = platformRoleRepository.findById(resources.getId()).orElseGet(PlatformRole::new);
        ValidationUtil.isNull(platformRole.getId(), "PlatformRole", "id", resources.getId());

        PlatformRole platformRole1 = platformRoleRepository.findByName(resources.getName());

        if (platformRole1 != null && !platformRole1.getId().equals(platformRole.getId())) {
            throw new EntityExistException(PlatformRole.class, "username", resources.getName());
        }
        platformRole1 = platformRoleRepository.findByPermission(resources.getPermission());
        if (platformRole1 != null && !platformRole1.getId().equals(platformRole.getId())) {
            throw new EntityExistException(PlatformRole.class, "permission", resources.getPermission());
        }
        platformRole.setName(resources.getName());
        platformRole.setRemark(resources.getRemark());
        platformRole.setDataScope(resources.getDataScope());
        platformRole.setPlatformDepts(resources.getPlatformDepts());
        platformRole.setLevel(resources.getLevel());
        platformRole.setPermission(resources.getPermission());
        platformRoleRepository.save(platformRole);
    }

    @Override
    public void updateMenu(PlatformRole resources, PlatformRoleDTO platformRoleDTO) {
        PlatformRole platformRole = roleMapper.toEntity(platformRoleDTO);
        platformRole.setPlatformMenus(resources.getPlatformMenus());
        platformRoleRepository.save(platformRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void untiedMenu(Long id) {
        platformRoleRepository.untiedMenu(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            platformRoleRepository.deleteById(id);
        }
    }

    @Override
    public List<RoleSmallDTO> findByUsersId(Long id) {
        return roleSmallMapper.toDto(new ArrayList<>(platformRoleRepository.findByUsers_Id(id)));
    }

    @Override
    public Integer findByRoles(Set<PlatformRole> platformRoles) {
        Set<PlatformRoleDTO> platformRoleDtos = new HashSet<>();
        for (PlatformRole platformRole : platformRoles) {
            platformRoleDtos.add(findById(platformRole.getId()));
        }
        return Collections.min(platformRoleDtos.stream().map(PlatformRoleDTO::getLevel).collect(Collectors.toList()));
    }

    @Override
    public Collection<GrantedAuthority> mapToGrantedAuthorities(PlatformUserDTO user) {
        Set<PlatformRole> platformRoles = platformRoleRepository.findByUsers_Id(user.getId());
        Set<String> permissions = platformRoles.stream()
                .filter(role -> StringUtils.isNotBlank(role.getPermission()))
                .map(PlatformRole::getPermission)
                .collect(Collectors.toSet());
        permissions.addAll(platformRoles.stream()
                .flatMap(role -> role.getPlatformMenus().stream())
                .filter(menu -> StringUtils.isNotBlank(menu.getPermission()))
                .map(PlatformMenu::getPermission)
                .collect(Collectors.toSet()));
        return permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public void download(List<PlatformRoleDTO> roles, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PlatformRoleDTO role : roles) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("角色名称", role.getName());
            map.put("默认权限", role.getPermission());
            map.put("角色级别", role.getLevel());
            map.put("描述", role.getRemark());
            map.put("创建日期", role.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
