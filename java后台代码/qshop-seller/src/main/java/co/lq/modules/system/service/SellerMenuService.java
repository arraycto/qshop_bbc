package co.lq.modules.system.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import co.lq.modules.system.domain.SellerMenu;
import co.lq.modules.system.service.dto.SellerMenuDTO;
import co.lq.modules.system.service.dto.SellerMenuQueryCriteria;
import co.lq.modules.system.service.dto.SellerRoleSmallDTO;

/**
 * @author billy
 * @date 2018-12-17
 */
public interface SellerMenuService {

    /**
     * 查询全部数据
     *
     * @param criteria 条件
     * @return /
     */
    List<SellerMenuDTO> queryAll(SellerMenuQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    SellerMenuDTO findById(long id);

    /**
     * 创建
     *
     * @param resources /
     * @return /
     */
    SellerMenuDTO create(SellerMenu resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(SellerMenu resources);

    /**
     * 获取待删除的菜单
     *
     * @param sellerMenuList /
     * @param sellerMenuSet /
     * @return /
     */
    Set<SellerMenu> getDeleteMenus(List<SellerMenu> sellerMenuList, Set<SellerMenu> sellerMenuSet);

    /**
     * 获取菜单树
     *
     * @param sellerMenus /
     * @return /
     */
    Object getMenuTree(List<SellerMenu> sellerMenus);

    /**
     * 根据pid查询
     *
     * @param pid /
     * @return /
     */
    List<SellerMenu> findByPid(long pid);

    /**
     * 构建菜单树
     *
     * @param sellerMenuDtos 原始数据
     * @return /
     */
    Map<String, Object> buildTree(List<SellerMenuDTO> sellerMenuDtos);

    /**
     * 根据角色查询
     *
     * @param roles /
     * @return /
     */
    List<SellerMenuDTO> findByRoles(List<SellerRoleSmallDTO> roles);

    /**
     * 构建菜单树
     *
     * @param sellerMenuDtos /
     * @return /
     */
    Object buildMenus(List<SellerMenuDTO> sellerMenuDtos);

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    SellerMenu findOne(Long id);

    /**
     * 删除
     *
     * @param sellerMenuSet /
     */
    void delete(Set<SellerMenu> sellerMenuSet);

    /**
     * 导出
     *
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<SellerMenuDTO> queryAll, HttpServletResponse response) throws IOException;
}
