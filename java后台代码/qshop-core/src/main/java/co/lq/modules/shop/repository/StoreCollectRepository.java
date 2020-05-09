package co.lq.modules.shop.repository;

import co.lq.modules.shop.domain.StoreCollect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author billy
* @date 2020-04-05
*/
public interface StoreCollectRepository extends JpaRepository<StoreCollect, Long>, JpaSpecificationExecutor<StoreCollect> {
}