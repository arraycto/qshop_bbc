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
import co.lq.modules.system.domain.SellerMenu;
import co.lq.modules.system.domain.vo.StoreMenuMetaVo;
import co.lq.modules.system.domain.vo.StoreMenuVo;
import co.lq.modules.system.repository.SellerMenuRepository;
import co.lq.modules.system.service.SellerMenuService;
import co.lq.modules.system.service.SellerRoleService;
import co.lq.modules.system.service.dto.SellerMenuDTO;
import co.lq.modules.system.service.dto.SellerMenuQueryCriteria;
import co.lq.modules.system.service.dto.SellerRoleSmallDTO;
import co.lq.modules.system.service.mapper.SellerMenuMapper;
import co.lq.utils.FileUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.StringUtils;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SellerMenuServiceImpl implements SellerMenuService {

    private final SellerMenuRepository sellerMenuRepository;

    private final SellerMenuMapper     sellerMenuMapper;

    private final SellerRoleService    sellerRoleService;

    public SellerMenuServiceImpl(SellerMenuRepository sellerMenuRepository, SellerMenuMapper sellerMenuMapper,
                                 SellerRoleService sellerRoleService) {
        this.sellerMenuRepository = sellerMenuRepository;
        this.sellerMenuMapper = sellerMenuMapper;
        this.sellerRoleService = sellerRoleService;
    }

    @Override
    public List<SellerMenuDTO> queryAll(SellerMenuQueryCriteria criteria) {
        //        Sort sort = new Sort(Sort.Direction.DESC,"id");
        criteria.setHidden(false);
        return sellerMenuMapper.toDto(sellerMenuRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public SellerMenuDTO findById(long id) {
        SellerMenu sellerMenu = sellerMenuRepository.findById(id).orElseGet(SellerMenu::new);
        ValidationUtil.isNull(sellerMenu.getId(), "SellerMenu", "id", id);
        return sellerMenuMapper.toDto(sellerMenu);
    }

    @Override
    public List<SellerMenuDTO> findByRoles(List<SellerRoleSmallDTO> role) {
        SellerRoleSmallDTO sellerRoleSmallDTO = role.get(0);
        if ("admin".equals(sellerRoleSmallDTO.getPermission())) {
            List<SellerMenu> menuList = sellerMenuRepository.findByTypeNotOrderBySortAsc(2);
            return menuList.stream().map(sellerMenuMapper::toDto).collect(Collectors.toList());
        } else {
            Set<Long> roleIds = role.stream().map(SellerRoleSmallDTO::getId).collect(Collectors.toSet());
            LinkedHashSet<SellerMenu> sellerMenus = sellerMenuRepository
                    .findBySellerRoles_IdInAndTypeNotOrderBySortAsc(roleIds, 2);
            return sellerMenus.stream().map(sellerMenuMapper::toDto).collect(Collectors.toList());
        }
    }

    @Override
    public SellerMenuDTO create(SellerMenu resources) {
        if (sellerMenuRepository.findByName(resources.getName()) != null) {
            throw new EntityExistException(SellerMenu.class, "name", resources.getName());
        }
        if (StringUtils.isNotBlank(resources.getComponentName())) {
            if (sellerMenuRepository.findByComponentName(resources.getComponentName()) != null) {
                throw new EntityExistException(SellerMenu.class, "componentName", resources.getComponentName());
            }
        }
        if (resources.getIFrame()) {
            String http = "http://", https = "https://";
            if (!(resources.getPath().toLowerCase().startsWith(http)
                    || resources.getPath().toLowerCase().startsWith(https))) {
                throw new BadRequestException("外链必须以http://或者https://开头");
            }
        }
        return sellerMenuMapper.toDto(sellerMenuRepository.save(resources));
    }

    @Override
    public void update(SellerMenu resources) {
        if (resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        SellerMenu sellerMenu = sellerMenuRepository.findById(resources.getId()).orElseGet(SellerMenu::new);
        ValidationUtil.isNull(sellerMenu.getId(), "Permission", "id", resources.getId());

        if (resources.getIFrame()) {
            String http = "http://", https = "https://";
            if (!(resources.getPath().toLowerCase().startsWith(http)
                    || resources.getPath().toLowerCase().startsWith(https))) {
                throw new BadRequestException("外链必须以http://或者https://开头");
            }
        }
        SellerMenu sellerMenu1 = sellerMenuRepository.findByName(resources.getName());

        if (sellerMenu1 != null && !sellerMenu1.getId().equals(sellerMenu.getId())) {
            throw new EntityExistException(SellerMenu.class, "name", resources.getName());
        }

        if (StringUtils.isNotBlank(resources.getComponentName())) {
            sellerMenu1 = sellerMenuRepository.findByComponentName(resources.getComponentName());
            if (sellerMenu1 != null && !sellerMenu1.getId().equals(sellerMenu.getId())) {
                throw new EntityExistException(SellerMenu.class, "componentName", resources.getComponentName());
            }
        }
        sellerMenu.setName(resources.getName());
        sellerMenu.setComponent(resources.getComponent());
        sellerMenu.setPath(resources.getPath());
        sellerMenu.setIcon(resources.getIcon());
        sellerMenu.setIFrame(resources.getIFrame());
        sellerMenu.setPid(resources.getPid());
        sellerMenu.setSort(resources.getSort());
        sellerMenu.setCache(resources.getCache());
        sellerMenu.setHidden(resources.getHidden());
        sellerMenu.setComponentName(resources.getComponentName());
        sellerMenu.setPermission(resources.getPermission());
        sellerMenu.setType(resources.getType());
        sellerMenuRepository.save(sellerMenu);
    }

    @Override
    public Set<SellerMenu> getDeleteMenus(List<SellerMenu> sellerMenuList, Set<SellerMenu> sellerMenuSet) {
        // 递归找出待删除的菜单
        for (SellerMenu sellerMenu1 : sellerMenuList) {
            sellerMenuSet.add(sellerMenu1);
            List<SellerMenu> sellerMenus = sellerMenuRepository.findByPid(sellerMenu1.getId());
            if (sellerMenus != null && sellerMenus.size() != 0) {
                getDeleteMenus(sellerMenus, sellerMenuSet);
            }
        }
        return sellerMenuSet;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<SellerMenu> sellerMenuSet) {
        for (SellerMenu sellerMenu : sellerMenuSet) {
            sellerRoleService.untiedMenu(sellerMenu.getId());
            sellerMenuRepository.deleteById(sellerMenu.getId());
        }
    }

    @Override
    public Object getMenuTree(List<SellerMenu> sellerMenus) {
        List<Map<String, Object>> list = new LinkedList<>();
        sellerMenus.forEach(menu -> {
            if (menu != null) {
                List<SellerMenu> sellerMenuList = sellerMenuRepository.findByPid(menu.getId());
                Map<String, Object> map = new HashMap<>(16);
                map.put("id", menu.getId());
                map.put("label", menu.getName());
                if (sellerMenuList != null && sellerMenuList.size() != 0) {
                    map.put("children", getMenuTree(sellerMenuList));
                }
                list.add(map);
            }
        });
        return list;
    }

    @Override
    public List<SellerMenu> findByPid(long pid) {
        return sellerMenuRepository.findByPid(pid);
    }

    @Override
    public Map<String, Object> buildTree(List<SellerMenuDTO> sellerMenuDtos) {
        List<SellerMenuDTO> trees = new ArrayList<>();
        Set<Long> ids = new HashSet<>();
        for (SellerMenuDTO sellerMenuDTO : sellerMenuDtos) {
            if (sellerMenuDTO.getPid() == 0) {
                trees.add(sellerMenuDTO);
            }
            for (SellerMenuDTO it : sellerMenuDtos) {
                if (it.getPid().equals(sellerMenuDTO.getId())) {
                    if (sellerMenuDTO.getChildren() == null) {
                        sellerMenuDTO.setChildren(new ArrayList<>());
                    }
                    sellerMenuDTO.getChildren().add(it);
                    ids.add(it.getId());
                }
            }
        }
        Map<String, Object> map = new HashMap<>(2);
        if (trees.size() == 0) {
            trees = sellerMenuDtos.stream().filter(s -> !ids.contains(s.getId())).collect(Collectors.toList());
        }
        map.put("content", trees);
        map.put("totalElements", sellerMenuDtos.size());
        return map;
    }

    @Override
    public List<StoreMenuVo> buildMenus(List<SellerMenuDTO> sellerMenuDtos) {
        List<StoreMenuVo> list = new LinkedList<>();
        sellerMenuDtos.forEach(menuDTO -> {
            if (menuDTO != null) {
                List<SellerMenuDTO> sellerMenuDtoList = menuDTO.getChildren();
                StoreMenuVo storeMenuVo = new StoreMenuVo();
                storeMenuVo.setName(ObjectUtil.isNotEmpty(menuDTO.getComponentName()) ? menuDTO.getComponentName()
                        : menuDTO.getName());
                // 一级目录需要加斜杠，不然会报警告
                storeMenuVo.setPath(menuDTO.getPid() == 0 ? "/" + menuDTO.getPath() : menuDTO.getPath());
                storeMenuVo.setHidden(menuDTO.getHidden());
                // 如果不是外链
                if (!menuDTO.getIFrame()) {
                    if (menuDTO.getPid() == 0) {
                        storeMenuVo.setComponent(
                                StrUtil.isEmpty(menuDTO.getComponent()) ? "Layout" : menuDTO.getComponent());
                    } else if (!StrUtil.isEmpty(menuDTO.getComponent())) {
                        storeMenuVo.setComponent(menuDTO.getComponent());
                    }
                }
                storeMenuVo.setMeta(new StoreMenuMetaVo(menuDTO.getName(), menuDTO.getIcon(), !menuDTO.getCache()));
                if (sellerMenuDtoList != null && sellerMenuDtoList.size() != 0) {
                    storeMenuVo.setAlwaysShow(true);
                    storeMenuVo.setRedirect("noredirect");
                    storeMenuVo.setChildren(buildMenus(sellerMenuDtoList));
                    // 处理是一级菜单并且没有子菜单的情况
                } else if (menuDTO.getPid() == 0) {
                    StoreMenuVo storeMenuVo1 = new StoreMenuVo();
                    storeMenuVo1.setMeta(storeMenuVo.getMeta());
                    // 非外链
                    if (!menuDTO.getIFrame()) {
                        storeMenuVo1.setPath("index");
                        storeMenuVo1.setName(storeMenuVo.getName());
                        storeMenuVo1.setComponent(storeMenuVo.getComponent());
                    } else {
                        storeMenuVo1.setPath(menuDTO.getPath());
                    }
                    storeMenuVo.setName(null);
                    storeMenuVo.setMeta(null);
                    storeMenuVo.setComponent("Layout");
                    List<StoreMenuVo> list1 = new ArrayList<>();
                    list1.add(storeMenuVo1);
                    storeMenuVo.setChildren(list1);
                }
                list.add(storeMenuVo);
            }
        });
        return list;
    }

    @Override
    public SellerMenu findOne(Long id) {
        SellerMenu sellerMenu = sellerMenuRepository.findById(id).orElseGet(SellerMenu::new);
        ValidationUtil.isNull(sellerMenu.getId(), "SellerMenu", "id", id);
        return sellerMenu;
    }

    @Override
    public void download(List<SellerMenuDTO> sellerMenuDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SellerMenuDTO sellerMenuDTO : sellerMenuDtos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("菜单名称", sellerMenuDTO.getName());
            map.put("菜单类型", sellerMenuDTO.getType() == 0 ? "目录" : sellerMenuDTO.getType() == 1 ? "菜单" : "按钮");
            map.put("权限标识", sellerMenuDTO.getPermission());
            map.put("外链菜单", sellerMenuDTO.getIFrame() ? "是" : "否");
            map.put("菜单可见", sellerMenuDTO.getHidden() ? "否" : "是");
            map.put("是否缓存", sellerMenuDTO.getCache() ? "是" : "否");
            map.put("创建日期", sellerMenuDTO.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
