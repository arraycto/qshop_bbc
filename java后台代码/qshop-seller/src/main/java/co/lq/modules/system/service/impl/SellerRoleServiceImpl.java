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
import co.lq.modules.system.domain.SellerMenu;
import co.lq.modules.system.domain.SellerRole;
import co.lq.modules.system.repository.SellerRoleRepository;
import co.lq.modules.system.service.SellerRoleService;
import co.lq.modules.system.service.dto.SellerDTO;
import co.lq.modules.system.service.dto.SellerRoleDTO;
import co.lq.modules.system.service.dto.SellerRoleQueryCriteria;
import co.lq.modules.system.service.dto.SellerRoleSmallDTO;
import co.lq.modules.system.service.mapper.SellerRoleMapper;
import co.lq.modules.system.service.mapper.SellerRoleSmallMapper;
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
public class SellerRoleServiceImpl implements SellerRoleService {

    private final SellerRoleRepository  sellerRoleRepository;

    private final SellerRoleMapper      sellerRoleMapper;

    private final SellerRoleSmallMapper sellerRoleSmallMapper;

    public SellerRoleServiceImpl(SellerRoleRepository sellerRoleRepository, SellerRoleMapper sellerRoleMapper,
                                 SellerRoleSmallMapper sellerRoleSmallMapper) {
        this.sellerRoleRepository = sellerRoleRepository;
        this.sellerRoleMapper = sellerRoleMapper;
        this.sellerRoleSmallMapper = sellerRoleSmallMapper;
    }

    @Override
    public Object queryAll(Pageable pageable) {
        return sellerRoleMapper.toDto(sellerRoleRepository.findAll(pageable).getContent());
    }

    @Override
    public List<SellerRoleDTO> queryAll(SellerRoleQueryCriteria criteria) {
        return sellerRoleMapper.toDto(sellerRoleRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public Object queryAll(SellerRoleQueryCriteria criteria, Pageable pageable) {
        Page<SellerRole> page = sellerRoleRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(sellerRoleMapper::toDto));
    }

    @Override
    public SellerRoleDTO findById(long id) {
        SellerRole sellerRole = sellerRoleRepository.findById(id).orElseGet(SellerRole::new);
        ValidationUtil.isNull(sellerRole.getId(), "SellerRole", "id", id);
        return sellerRoleMapper.toDto(sellerRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SellerRoleDTO create(SellerRole resources) {
        if (sellerRoleRepository.findByName(resources.getName()) != null) {
            throw new EntityExistException(SellerRole.class, "username", resources.getName());
        }
        return sellerRoleMapper.toDto(sellerRoleRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SellerRole resources) {
        SellerRole sellerRole = sellerRoleRepository.findById(resources.getId()).orElseGet(SellerRole::new);
        ValidationUtil.isNull(sellerRole.getId(), "SellerRole", "id", resources.getId());

        SellerRole sellerRole1 = sellerRoleRepository.findByName(resources.getName());

        if (sellerRole1 != null && !sellerRole1.getId().equals(sellerRole.getId())) {
            throw new EntityExistException(SellerRole.class, "username", resources.getName());
        }
        sellerRole1 = sellerRoleRepository.findByPermission(resources.getPermission());
        if (sellerRole1 != null && !sellerRole1.getId().equals(sellerRole.getId())) {
            throw new EntityExistException(SellerRole.class, "permission", resources.getPermission());
        }
        sellerRole.setName(resources.getName());
        sellerRole.setRemark(resources.getRemark());
        sellerRole.setDataScope(resources.getDataScope());
        sellerRole.setSellerDepts(resources.getSellerDepts());
        sellerRole.setLevel(resources.getLevel());
        sellerRole.setPermission(resources.getPermission());
        sellerRoleRepository.save(sellerRole);
    }

    @Override
    public void updateMenu(SellerRole resources, SellerRoleDTO sellerRoleDTO) {
        SellerRole sellerRole = sellerRoleMapper.toEntity(sellerRoleDTO);
        sellerRole.setSellerMenus(resources.getSellerMenus());
        sellerRoleRepository.save(sellerRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void untiedMenu(Long id) {
        sellerRoleRepository.untiedMenu(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            sellerRoleRepository.deleteById(id);
        }
    }

    @Override
    public List<SellerRoleSmallDTO> findByUsersId(Long id) {
        return sellerRoleSmallMapper.toDto(new ArrayList<>(sellerRoleRepository.findByUsers_Id(id)));
    }

    @Override
    public Integer findByRoles(Set<SellerRole> sellerRoles) {
        Set<SellerRoleDTO> sellerRoleDtos = new HashSet<>();
        for (SellerRole sellerRole : sellerRoles) {
            sellerRoleDtos.add(findById(sellerRole.getId()));
        }
        return Collections.min(sellerRoleDtos.stream().map(SellerRoleDTO::getLevel).collect(Collectors.toList()));
    }

    @Override
    public Collection<GrantedAuthority> mapToGrantedAuthorities(SellerDTO user) {
        Set<SellerRole> sellerRoles = sellerRoleRepository.findByUsers_Id(user.getId());
        Set<String> permissions = sellerRoles.stream()
                .filter(role -> StringUtils.isNotBlank(role.getPermission()))
                .map(SellerRole::getPermission)
                .collect(Collectors.toSet());
        permissions.addAll(sellerRoles.stream()
                .flatMap(role -> role.getSellerMenus().stream())
                .filter(menu -> StringUtils.isNotBlank(menu.getPermission()))
                .map(SellerMenu::getPermission)
                .collect(Collectors.toSet()));
        return permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public void download(List<SellerRoleDTO> roles, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SellerRoleDTO role : roles) {
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
