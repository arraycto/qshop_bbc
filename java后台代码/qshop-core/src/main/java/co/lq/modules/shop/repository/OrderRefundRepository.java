package co.lq.modules.shop.repository;

import co.lq.modules.shop.domain.OrderRefund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author billy
* @date 2020-03-29
*/
public interface OrderRefundRepository extends JpaRepository<OrderRefund, Long>, JpaSpecificationExecutor<OrderRefund> {
}