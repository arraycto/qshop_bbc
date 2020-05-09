package co.lq.modules.system.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import co.lq.modules.system.domain.PlatformDept;

/**
 * @author billy
 * @date 2019-03-25
 */
@SuppressWarnings("all")
public interface PlatformDeptRepository
        extends JpaRepository<PlatformDept, Long>, JpaSpecificationExecutor<PlatformDept> {

    /**
     * 根据 PID 查询
     *
     * @param id pid
     * @return /
     */
    List<PlatformDept> findByPid(Long id);

    /**
     * 根据ID查询名称
     *
     * @param id ID
     * @return /
     */
    @Query(value = "select name from dept where id = ?1", nativeQuery = true)
    String findNameById(Long id);

    /**
     * 根据角色ID 查询
     *
     * @param id 角色ID
     * @return /
     */
    Set<PlatformDept> findByPlatformRoles_Id(Long id);
}
