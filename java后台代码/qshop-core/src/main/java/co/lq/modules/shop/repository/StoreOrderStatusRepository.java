package co.lq.modules.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.shop.domain.StoreOrderStatus;

/**
 * @author billy
 * @date 2019-11-02
 */
public interface StoreOrderStatusRepository extends JpaRepository<StoreOrderStatus, Long>, JpaSpecificationExecutor {
}
