package co.lq.modules.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.activity.domain.StoreVisit;

/**
 * @author billy
 * @date 2019-11-18
 */
public interface StoreVisitRepository extends JpaRepository<StoreVisit, Long>, JpaSpecificationExecutor {
    int countByProductIdAndProductType(long productId, String productType);
}
