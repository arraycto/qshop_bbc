package co.lq.modules.system.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import co.lq.modules.system.domain.SellerDept;

/**
 * @author billy
 * @date 2019-03-25
 */
@SuppressWarnings("all")
public interface SellerDeptRepository extends JpaRepository<SellerDept, Long>, JpaSpecificationExecutor<SellerDept> {

    /**
     * 根据 PID 查询
     *
     * @param id pid
     * @return /
     */
    List<SellerDept> findByPid(Long id);

    /**
     * 根据 PID 查询
     *
     * @param id pid
     * @param storeId /
     * @return /
     */
    List<SellerDept> findByPidAndStoreId(Long id, Long storeId);

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
    Set<SellerDept> findBySellerRoles_Id(Long id);
}
