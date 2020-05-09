package co.lq.modules.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.activity.domain.StoreCouponUser;

/**
 * @author billy
 * @date 2019-11-10
 */
public interface StoreCouponUserRepository extends JpaRepository<StoreCouponUser, Long>, JpaSpecificationExecutor {
}
