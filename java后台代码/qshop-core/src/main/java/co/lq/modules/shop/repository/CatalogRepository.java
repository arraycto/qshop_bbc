package co.lq.modules.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import co.lq.modules.shop.domain.Catalog;

/**
 * @author billy
 * @date 2020-03-10
 */
public interface CatalogRepository extends JpaRepository<Catalog, Long>, JpaSpecificationExecutor<Catalog> {
    @Query(value = "select `name` from `catalog` where id = ?1", nativeQuery = true)
    String findNameById(Long id);
}
