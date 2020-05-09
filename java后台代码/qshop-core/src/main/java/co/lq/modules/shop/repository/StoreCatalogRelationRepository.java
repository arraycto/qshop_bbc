package co.lq.modules.shop.repository;

import co.lq.modules.shop.domain.StoreCatalogRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author billy
* @date 2020-04-23
*/
public interface StoreCatalogRelationRepository extends JpaRepository<StoreCatalogRelation, Long>, JpaSpecificationExecutor<StoreCatalogRelation> {
}