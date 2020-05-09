package co.lq.modules.shop.repository;

import co.lq.modules.shop.domain.StoreOrderReturnReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author billy
* @date 2020-03-29
*/
public interface StoreOrderReturnReasonRepository extends JpaRepository<StoreOrderReturnReason, Long>, JpaSpecificationExecutor<StoreOrderReturnReason> {
}