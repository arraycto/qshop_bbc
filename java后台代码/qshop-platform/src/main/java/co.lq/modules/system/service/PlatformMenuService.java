package co.lq.modules.system.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import co.lq.modules.system.domain.PlatformMenu;
import co.lq.modules.system.service.dto.MenuDTO;
import co.lq.modules.system.service.dto.MenuQueryCriteria;
import co.lq.modules.system.service.dto.RoleSmallDTO;

/**
 * @author billy
 * @date 2018-12-17
 */
public interface PlatformMenuService {

    /**
     * 查询全部数据
     *
     * @param criteria 条件
     * @return /
     */
    List<MenuDTO> queryAll(MenuQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    MenuDTO findById(long id);

    /**
     * 创建
     *
     * @param resources /
     * @return /
     */
    MenuDTO create(PlatformMenu resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(PlatformMenu resources);

    /**
     * 获取待删除的菜单
     *
     * @param platformMenuList /
     * @param platformMenuSet /
     * @return /
     */
    Set<PlatformMenu> getDeleteMenus(List<PlatformMenu> platformMenuList, Set<PlatformMenu> platformMenuSet);

    /**
     * 获取菜单树
     *
     * @param platformMenus /
     * @return /
     */
    Object getMenuTree(List<PlatformMenu> platformMenus);

    /**
     * 根据pid查询
     *
     * @param pid /
     * @return /
     */
    List<PlatformMenu> findByPid(long pid);

    /**
     * 构建菜单树
     *
     * @param menuDtos 原始数据
     * @return /
     */
    Map<String, Object> buildTree(List<MenuDTO> menuDtos);

    /**
     * 根据角色查询
     *
     * @param roles /
     * @return /
     */
    List<MenuDTO> findByRoles(List<RoleSmallDTO> roles);

    /**
     * 构建菜单树
     *
     * @param menuDtos /
     * @return /
     */
    Object buildMenus(List<MenuDTO> menuDtos);

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    PlatformMenu findOne(Long id);

    /**
     * 删除
     *
     * @param platformMenuSet /
     */
    void delete(Set<PlatformMenu> platformMenuSet);

    /**
     * 导出
     *
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<MenuDTO> queryAll, HttpServletResponse response) throws IOException;
}
