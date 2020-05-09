package co.lq.modules.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import co.lq.modules.activity.domain.StoreCombination;

/**
 * @author billy
 * @date 2019-11-18
 */
public interface StoreCombinationRepository extends JpaRepository<StoreCombination, Long>, JpaSpecificationExecutor {
    @Modifying
    @Query(value = "update store_combination set is_show = ?1 where id = ?2", nativeQuery = true)
    void updateOnsale(int status, long id);

    @Modifying
    @Query(value = "update store_combination set verify_status = ?1 where id = ?2", nativeQuery = true)
    void updateOnVerify(int status, long id);

}
