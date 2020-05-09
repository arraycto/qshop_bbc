package co.lq.modules.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.shop.domain.CatalogAttr;

/**
 * @author billy
 * @date 2020-03-10
 */
public interface CatalogAttrRepository extends JpaRepository<CatalogAttr, Long>, JpaSpecificationExecutor<CatalogAttr> {

    List<CatalogAttr> findByCatalogIdAndIsSpecOrderBySortAsc(Long catalogId, Integer isSpec);
}
