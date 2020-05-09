package co.lq.modules.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.system.domain.PlatformJob;

/**
 * @author billy
 * @date 2019-03-29
 */
public interface PlatformJobRepository extends JpaRepository<PlatformJob, Long>, JpaSpecificationExecutor<PlatformJob> {
}
