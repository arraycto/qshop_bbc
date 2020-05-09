package co.lq.modules.system.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;

import co.lq.modules.system.domain.PlatformRole;
import co.lq.modules.system.service.dto.PlatformRoleDTO;
import co.lq.modules.system.service.dto.PlatformUserDTO;
import co.lq.modules.system.service.dto.RoleQueryCriteria;
import co.lq.modules.system.service.dto.RoleSmallDTO;

/**
 * @author billy
 * @date 2018-12-03
 */
public interface PlatformRoleService {

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    PlatformRoleDTO findById(long id);

    /**
     * 创建
     *
     * @param resources /
     * @return /
     */
    PlatformRoleDTO create(PlatformRole resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(PlatformRole resources);

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
    List<RoleSmallDTO> findByUsersId(Long id);

    /**
     * 根据角色查询角色级别
     *
     * @param platformRoles /
     * @return /
     */
    Integer findByRoles(Set<PlatformRole> platformRoles);

    /**
     * 修改绑定的菜单
     *
     * @param resources /
     * @param platformRoleDTO /
     */
    void updateMenu(PlatformRole resources, PlatformRoleDTO platformRoleDTO);

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
    Object queryAll(RoleQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部
     *
     * @param criteria 条件
     * @return /
     */
    List<PlatformRoleDTO> queryAll(RoleQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<PlatformRoleDTO> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 获取用户权限信息
     *
     * @param user 用户信息
     * @return 权限信息
     */
    Collection<GrantedAuthority> mapToGrantedAuthorities(PlatformUserDTO user);
}
