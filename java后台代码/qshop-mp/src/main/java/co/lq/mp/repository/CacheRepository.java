package co.lq.mp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.mp.domain.Cache;

/**
 * @author billy
 * @date 2019-10-06
 */
public interface CacheRepository extends JpaRepository<Cache, String>, JpaSpecificationExecutor {
}
