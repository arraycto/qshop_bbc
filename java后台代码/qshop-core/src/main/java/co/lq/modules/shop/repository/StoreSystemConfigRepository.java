package co.lq.modules.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.shop.domain.StoreSystemConfig;

/**
 * @author billy
 * @date 2020-03-11
 */
public interface StoreSystemConfigRepository
        extends JpaRepository<StoreSystemConfig, Long>, JpaSpecificationExecutor<StoreSystemConfig> {
    StoreSystemConfig findByMenuName(String str);
}
