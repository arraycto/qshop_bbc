package co.lq.modules.system.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;

import co.lq.modules.system.domain.SellerRole;
import co.lq.modules.system.service.dto.SellerDTO;
import co.lq.modules.system.service.dto.SellerRoleDTO;
import co.lq.modules.system.service.dto.SellerRoleQueryCriteria;
import co.lq.modules.system.service.dto.SellerRoleSmallDTO;

/**
 * @author billy
 * @date 2018-12-03
 */
public interface SellerRoleService {

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    SellerRoleDTO findById(long id);

    /**
     * 创建
     *
     * @param resources /
     * @return /
     */
    SellerRoleDTO create(SellerRole resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(SellerRole resources);

    /**
     * 删除
     *
     * @param ids /
     */
    void delete(Set<Long> ids);

    /**
     * 根据用户ID查询
     *
     * @param id 用户ID
     * @return /
     */
    List<SellerRoleSmallDTO> findByUsersId(Long id);

    /**
     * 根据角色查询角色级别
     *
     * @param sellerRoles /
     * @return /
     */
    Integer findByRoles(Set<SellerRole> sellerRoles);

    /**
     * 修改绑定的菜单
     *
     * @param resources /
     * @param sellerRoleDTO /
     */
    void updateMenu(SellerRole resources, SellerRoleDTO sellerRoleDTO);

    /**
     * 解绑菜单
     *
     * @param id /
     */
    void untiedMenu(Long id);

    /**
     * 不带条件分页查询
     *
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(Pageable pageable);

    /**
     * 待条件分页查询
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(SellerRoleQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部
     *
     * @param criteria 条件
     * @return /
     */
    List<SellerRoleDTO> queryAll(SellerRoleQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<SellerRoleDTO> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 获取用户权限信息
     *
     * @param user 用户信息
     * @return 权限信息
     */
    Collection<GrantedAuthority> mapToGrantedAuthorities(SellerDTO user);
}
