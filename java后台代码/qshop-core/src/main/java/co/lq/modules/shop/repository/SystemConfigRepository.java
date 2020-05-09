package co.lq.modules.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.shop.domain.SystemConfig;

/**
 * @author billy
 * @date 2019-10-10
 */
public interface SystemConfigRepository extends JpaRepository<SystemConfig, Integer>, JpaSpecificationExecutor {
    SystemConfig findByMenuName(String str);
}
