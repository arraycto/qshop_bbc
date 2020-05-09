package co.lq.modules.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.shop.domain.UserRecharge;

/**
 * @author billy
 * @date 2020-03-02
 */
public interface UserRechargeRepository
        extends JpaRepository<UserRecharge, Long>, JpaSpecificationExecutor<UserRecharge> {
    /**
     * 根据 OrderId 查询
     *
     * @param order_id /
     * @return /
     */
    UserRecharge findByOrderId(String order_id);
}
