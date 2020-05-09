package co.lq.modules.shop.repository;

import co.lq.modules.shop.domain.SeckillVertifyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author billy
* @date 2020-03-11
*/
public interface SeckillVertifyRecordRepository extends JpaRepository<SeckillVertifyRecord, Long>, JpaSpecificationExecutor<SeckillVertifyRecord> {
}