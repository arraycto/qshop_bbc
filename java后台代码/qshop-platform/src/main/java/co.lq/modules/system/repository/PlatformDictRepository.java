package co.lq.modules.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.system.domain.Dict;

/**
 * @author billy
 * @date 2019-04-10
 */
public interface PlatformDictRepository extends JpaRepository<Dict, Long>, JpaSpecificationExecutor<Dict> {
}
