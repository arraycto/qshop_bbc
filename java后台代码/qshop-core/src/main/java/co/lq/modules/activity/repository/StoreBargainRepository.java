package co.lq.modules.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import co.lq.modules.activity.domain.StoreBargain;

/**
 * @author billy
 * @date 2019-12-22
 */
public interface StoreBargainRepository extends JpaRepository<StoreBargain, Long>, JpaSpecificationExecutor {
    @Modifying
    @Query(value = "update store_bargain set verify_status = ?1 where id = ?2", nativeQuery = true)
    void updateOnVerify(int status, long id);
}
