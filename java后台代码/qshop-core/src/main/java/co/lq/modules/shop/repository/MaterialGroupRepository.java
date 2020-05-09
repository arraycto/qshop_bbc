package co.lq.modules.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.shop.domain.MaterialGroup;

/**
 * @author billy
 * @date 2020-01-09
 */
public interface MaterialGroupRepository
        extends JpaRepository<MaterialGroup, String>, JpaSpecificationExecutor<MaterialGroup> {
}
