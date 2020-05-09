package co.lq.modules.mnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.mnt.domain.Deploy;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
public interface DeployRepository extends JpaRepository<Deploy, Long>, JpaSpecificationExecutor<Deploy> {
}
