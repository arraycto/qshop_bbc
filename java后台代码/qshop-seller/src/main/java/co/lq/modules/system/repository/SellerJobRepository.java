package co.lq.modules.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.system.domain.SellerJob;

/**
 * @author billy
 * @date 2019-03-29
 */
public interface SellerJobRepository extends JpaRepository<SellerJob, Long>, JpaSpecificationExecutor<SellerJob> {
}
