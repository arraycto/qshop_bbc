package co.lq.modules.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.shop.domain.SystemStore;

/**
 * @author billy
 * @date 2020-03-03
 */
public interface SystemStoreRepository extends JpaRepository<SystemStore, Long>, JpaSpecificationExecutor<SystemStore> {
}
