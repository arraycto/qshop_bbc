package co.lq.modules.system.repository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.system.domain.PlatformMenu;

/**
 * @author billy
 * @date 2018-12-17
 */
@SuppressWarnings("all")
public interface PlatformMenuRepository
        extends JpaRepository<PlatformMenu, Long>, JpaSpecificationExecutor<PlatformMenu> {

    /**
     * 根据菜单名称查询
     *
     * @param name 菜单名称
     * @return /
     */
    PlatformMenu findByName(String name);

    /**
     * 根据组件名称查询
     *
     * @param name 组件名称
     * @return /
     */
    PlatformMenu findByComponentName(String name);

    /**
     * 根据菜单的 PID 查询
     *
     * @param pid /
     * @return /
     */
    List<PlatformMenu> findByPid(long pid);

    /**
     * 根据角色ID与菜单类型查询菜单
     *
     * @param roleIds roleIDs
     * @param type 类型
     * @return /
     */
    LinkedHashSet<PlatformMenu> findByPlatformRoles_IdInAndTypeNotOrderBySortAsc(Set<Long> roleIds, int type);

    /**
     * 根据角色ID与菜单类型查询菜单
     *
     * @param roleIds roleIDs
     * @param type 类型
     * @return /
     */
    LinkedHashSet<PlatformMenu> findByPlatformRoles_IdInAndTypeNotAndDeletedOrderBySortAsc(Set<Long> roleIds, int type,
                                                                                           int deleted);
}
