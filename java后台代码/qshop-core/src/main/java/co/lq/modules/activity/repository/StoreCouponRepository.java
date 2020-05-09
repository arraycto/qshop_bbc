package co.lq.modules.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.activity.domain.StoreCoupon;

/**
 * @author billy
 * @date 2019-11-09
 */
public interface StoreCouponRepository extends JpaRepository<StoreCoupon, Long>, JpaSpecificationExecutor {
}
