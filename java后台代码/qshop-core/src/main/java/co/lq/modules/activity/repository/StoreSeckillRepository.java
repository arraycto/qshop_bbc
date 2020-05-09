package co.lq.modules.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import co.lq.modules.activity.domain.StoreSeckill;

/**
 * @author billy
 * @date 2019-12-14
 */
public interface StoreSeckillRepository extends JpaRepository<StoreSeckill, Long>, JpaSpecificationExecutor {
    @Modifying
    @Query(value = "update store_seckill set verify_status = ?1 where id = ?2", nativeQuery = true)
    void updateOnVerify(int status, long id);
}
