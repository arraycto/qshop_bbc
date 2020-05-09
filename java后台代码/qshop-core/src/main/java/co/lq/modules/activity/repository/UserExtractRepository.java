package co.lq.modules.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.activity.domain.UserExtract;

/**
 * @author billy
 * @date 2019-11-14
 */
public interface UserExtractRepository extends JpaRepository<UserExtract, Long>, JpaSpecificationExecutor {
}
