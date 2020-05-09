package co.lq.modules.shop.repository;

import co.lq.modules.shop.domain.StoreWithdraw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author billy
* @date 2020-04-13
*/
public interface StoreWithdrawRepository extends JpaRepository<StoreWithdraw, Long>, JpaSpecificationExecutor<StoreWithdraw> {
}