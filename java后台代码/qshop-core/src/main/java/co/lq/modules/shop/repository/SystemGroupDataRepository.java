package co.lq.modules.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.shop.domain.SystemGroupData;

/**
 * @author billy
 * @date 2019-10-18
 */
public interface SystemGroupDataRepository extends JpaRepository<SystemGroupData, Long>, JpaSpecificationExecutor {
}
