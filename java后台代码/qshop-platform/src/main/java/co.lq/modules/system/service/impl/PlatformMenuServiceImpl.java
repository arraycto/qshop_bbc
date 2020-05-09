package co.lq.modules.system.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import co.lq.exception.BadRequestException;
import co.lq.exception.EntityExistException;
import co.lq.modules.system.domain.PlatformMenu;
import co.lq.modules.system.domain.vo.MenuMetaVo;
import co.lq.modules.system.domain.vo.MenuVo;
import co.lq.modules.system.repository.PlatformMenuRepository;
import co.lq.modules.system.service.PlatformMenuService;
import co.lq.modules.system.service.PlatformRoleService;
import co.lq.modules.system.service.dto.MenuDTO;
import co.lq.modules.system.service.dto.MenuQueryCriteria;
import co.lq.modules.system.service.dto.RoleSmallDTO;
import co.lq.modules.system.service.mapper.MenuMapper;
import co.lq.utils.FileUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.StringUtils;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PlatformMenuServiceImpl implements PlatformMenuService {

    private final PlatformMenuRepository platformMenuRepository;

    private final MenuMapper             menuMapper;

    private final PlatformRoleService    platformRoleService;

    public PlatformMenuServiceImpl(PlatformMenuRepository platformMenuRepository, MenuMapper menuMapper,
                                   PlatformRoleService platformRoleService) {
        this.platformMenuRepository = platformMenuRepository;
        this.menuMapper = menuMapper;
        this.platformRoleService = platformRoleService;
    }

    @Override
    public List<MenuDTO> queryAll(MenuQueryCriteria criteria) {
        //        Sort sort = new Sort(Sort.Direction.DESC,"id");
        return menuMapper.toDto(platformMenuRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public MenuDTO findById(long id) {
        PlatformMenu platformMenu = platformMenuRepository.findById(id).orElseGet(PlatformMenu::new);
        ValidationUtil.isNull(platformMenu.getId(), "PlatformMenu", "id", id);
        return menuMapper.toDto(platformMenu);
    }

    @Override
    public List<MenuDTO> findByRoles(List<RoleSmallDTO> roles) {
        Set<Long> roleIds = roles.stream().map(RoleSmallDTO::getId).collect(Collectors.toSet());
        LinkedHashSet<PlatformMenu> platformMenus = platformMenuRepository
                .findByPlatformRoles_IdInAndTypeNotAndDeletedOrderBySortAsc(roleIds, 2, 0);
        return platformMenus.stream().map(menuMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public MenuDTO create(PlatformMenu resources) {
        if (platformMenuRepository.findByName(resources.getName()) != null) {
            throw new EntityExistException(PlatformMenu.class, "name", resources.getName());
        }
        if (StringUtils.isNotBlank(resources.getComponentName())) {
            if (platformMenuRepository.findByComponentName(resources.getComponentName()) != null) {
                throw new EntityExistException(PlatformMenu.class, "componentName", resources.getComponentName());
            }
        }
        if (resources.getIFrame()) {
            String http = "http://", https = "https://";
            if (!(resources.getPath().toLowerCase().startsWith(http)
                    || resources.getPath().toLowerCase().startsWith(https))) {
                throw new BadRequestException("外链必须以http://或者https://开头");
            }
        }
        return menuMapper.toDto(platformMenuRepository.save(resources));
    }

    @Override
    public void update(PlatformMenu resources) {
        if (resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        PlatformMenu platformMenu = platformMenuRepository.findById(resources.getId()).orElseGet(PlatformMenu::new);
        ValidationUtil.isNull(platformMenu.getId(), "Permission", "id", resources.getId());

        if (resources.getIFrame()) {
            String http = "http://", https = "https://";
            if (!(resources.getPath().toLowerCase().startsWith(http)
                    || resources.getPath().toLowerCase().startsWith(https))) {
                throw new BadRequestException("外链必须以http://或者https://开头");
            }
        }
        PlatformMenu platformMenu1 = platformMenuRepository.findByName(resources.getName());

        if (platformMenu1 != null && !platformMenu1.getId().equals(platformMenu.getId())) {
            throw new EntityExistException(PlatformMenu.class, "name", resources.getName());
        }

        if (StringUtils.isNotBlank(resources.getComponentName())) {
            platformMenu1 = platformMenuRepository.findByComponentName(resources.getComponentName());
            if (platformMenu1 != null && !platformMenu1.getId().equals(platformMenu.getId())) {
                throw new EntityExistException(PlatformMenu.class, "componentName", resources.getComponentName());
            }
        }
        platformMenu.setName(resources.getName());
        platformMenu.setComponent(resources.getComponent());
        platformMenu.setPath(resources.getPath());
        platformMenu.setIcon(resources.getIcon());
        platformMenu.setIFrame(resources.getIFrame());
        platformMenu.setPid(resources.getPid());
        platformMenu.setSort(resources.getSort());
        platformMenu.setCache(resources.getCache());
        platformMenu.setHidden(resources.getHidden());
        platformMenu.setComponentName(resources.getComponentName());
        platformMenu.setPermission(resources.getPermission());
        platformMenu.setType(resources.getType());
        platformMenuRepository.save(platformMenu);
    }

    @Override
    public Set<PlatformMenu> getDeleteMenus(List<PlatformMenu> platformMenuList, Set<PlatformMenu> platformMenuSet) {
        // 递归找出待删除的菜单
        for (PlatformMenu platformMenu1 : platformMenuList) {
            platformMenuSet.add(platformMenu1);
            List<PlatformMenu> platformMenus = platformMenuRepository.findByPid(platformMenu1.getId());
            if (platformMenus != null && platformMenus.size() != 0) {
                getDeleteMenus(platformMenus, platformMenuSet);
            }
        }
        return platformMenuSet;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<PlatformMenu> platformMenuSet) {
        for (PlatformMenu platformMenu : platformMenuSet) {
            platformRoleService.untiedMenu(platformMenu.getId());
            platformMenuRepository.deleteById(platformMenu.getId());
        }
    }

    @Override
    public Object getMenuTree(List<PlatformMenu> platformMenus) {
        List<Map<String, Object>> list = new LinkedList<>();
        platformMenus.forEach(menu -> {
            if (menu != null) {
                List<PlatformMenu> platformMenuList = platformMenuRepository.findByPid(menu.getId());
                Map<String, Object> map = new HashMap<>(16);
                map.put("id", menu.getId());
                map.put("label", menu.getName());
                if (platformMenuList != null && platformMenuList.size() != 0) {
                    map.put("children", getMenuTree(platformMenuList));
                }
                list.add(map);
            }
        });
        return list;
    }

    @Override
    public List<PlatformMenu> findByPid(long pid) {
        return platformMenuRepository.findByPid(pid);
    }

    @Override
    public Map<String, Object> buildTree(List<MenuDTO> menuDtos) {
        List<MenuDTO> trees = new ArrayList<>();
        Set<Long> ids = new HashSet<>();
        for (MenuDTO menuDTO : menuDtos) {
            if (menuDTO.getPid() == 0) {
                trees.add(menuDTO);
            }
            for (MenuDTO it : menuDtos) {
                if (it.getPid().equals(menuDTO.getId())) {
                    if (menuDTO.getChildren() == null) {
                        menuDTO.setChildren(new ArrayList<>());
                    }
                    menuDTO.getChildren().add(it);
                    ids.add(it.getId());
                }
            }
        }
        Map<String, Object> map = new HashMap<>(2);
        if (trees.size() == 0) {
            trees = menuDtos.stream().filter(s -> !ids.contains(s.getId())).collect(Collectors.toList());
        }
        map.put("content", trees);
        map.put("totalElements", menuDtos.size());
        return map;
    }

    @Override
    public List<MenuVo> buildMenus(List<MenuDTO> menuDtos) {
        List<MenuVo> list = new LinkedList<>();
        menuDtos.forEach(menuDTO -> {
            if (menuDTO != null) {
                List<MenuDTO> menuDtoList = menuDTO.getChildren();
                MenuVo menuVo = new MenuVo();
                menuVo.setName(ObjectUtil.isNotEmpty(menuDTO.getComponentName()) ? menuDTO.getComponentName()
                        : menuDTO.getName());
                // 一级目录需要加斜杠，不然会报警告
                menuVo.setPath(menuDTO.getPid() == 0 ? "/" + menuDTO.getPath() : menuDTO.getPath());
                menuVo.setHidden(menuDTO.getHidden());
                // 如果不是外链
                if (!menuDTO.getIFrame()) {
                    if (menuDTO.getPid() == 0) {
                        menuVo.setComponent(
                                StrUtil.isEmpty(menuDTO.getComponent()) ? "Layout" : menuDTO.getComponent());
                    } else if (!StrUtil.isEmpty(menuDTO.getComponent())) {
                        menuVo.setComponent(menuDTO.getComponent());
                    }
                }
                menuVo.setMeta(new MenuMetaVo(menuDTO.getName(), menuDTO.getIcon(), !menuDTO.getCache()));
                if (menuDtoList != null && menuDtoList.size() != 0) {
                    menuVo.setAlwaysShow(true);
                    menuVo.setRedirect("noredirect");
                    menuVo.setChildren(buildMenus(menuDtoList));
                    // 处理是一级菜单并且没有子菜单的情况
                } else if (menuDTO.getPid() == 0) {
                    MenuVo menuVo1 = new MenuVo();
                    menuVo1.setMeta(menuVo.getMeta());
                    // 非外链
                    if (!menuDTO.getIFrame()) {
                        menuVo1.setPath("index");
                        menuVo1.setName(menuVo.getName());
                        menuVo1.setComponent(menuVo.getComponent());
                    } else {
                        menuVo1.setPath(menuDTO.getPath());
                    }
                    menuVo.setName(null);
                    menuVo.setMeta(null);
                    menuVo.setComponent("Layout");
                    List<MenuVo> list1 = new ArrayList<>();
                    list1.add(menuVo1);
                    menuVo.setChildren(list1);
                }
                list.add(menuVo);
            }
        });
        return list;
    }

    @Override
    public PlatformMenu findOne(Long id) {
        PlatformMenu platformMenu = platformMenuRepository.findById(id).orElseGet(PlatformMenu::new);
        ValidationUtil.isNull(platformMenu.getId(), "PlatformMenu", "id", id);
        return platformMenu;
    }

    @Override
    public void download(List<MenuDTO> menuDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MenuDTO menuDTO : menuDtos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("菜单名称", menuDTO.getName());
            map.put("菜单类型", menuDTO.getType() == 0 ? "目录" : menuDTO.getType() == 1 ? "菜单" : "按钮");
            map.put("权限标识", menuDTO.getPermission());
            map.put("外链菜单", menuDTO.getIFrame() ? "是" : "否");
            map.put("菜单可见", menuDTO.getHidden() ? "否" : "是");
            map.put("是否缓存", menuDTO.getCache() ? "是" : "否");
            map.put("创建日期", menuDTO.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
