package co.lq.modules.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.shop.domain.StoreProductAttr;

/**
 * @author billy
 * @date 2019-10-13
 */
public interface StoreProductAttrRepository extends JpaRepository<StoreProductAttr, Long>, JpaSpecificationExecutor {
    //@Modifying
    //@Query(value = "delete from store_product_attr where product_id =?1",nativeQuery = true)
    void deleteByProductId(Long id);

    List<StoreProductAttr> findAllByProductIdAndDeleted(Long productId, Integer deleted);

    StoreProductAttr findByProductIdAndCatalogAttrId(Long productId, Long catalogAttrId);
}
