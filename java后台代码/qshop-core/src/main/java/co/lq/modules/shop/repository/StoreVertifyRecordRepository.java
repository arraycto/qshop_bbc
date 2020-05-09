package co.lq.modules.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import co.lq.modules.shop.domain.StoreVertifyRecord;

/**
 * @author billy
 * @date 2020-03-28
 */
public interface StoreVertifyRecordRepository
        extends JpaRepository<StoreVertifyRecord, Long>, JpaSpecificationExecutor<StoreVertifyRecord> {
    @Modifying
    @Query(value = "update store_settle set status = ?1 where id = ?2", nativeQuery = true)
    void updateOnVerify(int status, long id);

    @Modifying
    @Query(value = "update store_settle set closed = ?1 where id = ?2", nativeQuery = true)
    void updateOnClosed(int status, long id);
}
