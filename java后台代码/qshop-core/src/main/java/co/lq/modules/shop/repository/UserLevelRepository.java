package co.lq.modules.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.shop.domain.UserLevel;

/**
 * @author billy
 * @date 2019-12-04
 */
public interface UserLevelRepository extends JpaRepository<UserLevel, Long>, JpaSpecificationExecutor {
}
