package co.lq.modules.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.activity.domain.StoreCouponIssueUser;

/**
 * @author billy
 * @date 2019-11-09
 */
public interface StoreCouponIssueUserRepository
        extends JpaRepository<StoreCouponIssueUser, Long>, JpaSpecificationExecutor {
}
