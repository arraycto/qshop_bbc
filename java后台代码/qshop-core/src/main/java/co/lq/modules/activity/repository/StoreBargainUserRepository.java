package co.lq.modules.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.activity.domain.StoreBargainUser;

/**
 * 砍价
 *
 * @author songbin
 * @since 2020年3月22日 下午3:54:11
 */
public interface StoreBargainUserRepository extends JpaRepository<StoreBargainUser, Long>, JpaSpecificationExecutor {
}
