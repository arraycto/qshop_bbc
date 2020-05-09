package co.lq.modules.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import co.lq.modules.shop.domain.StoreProductAttrValue;

/**
 * @author billy
 * @date 2019-10-13
 */
public interface StoreProductAttrValueRepository
        extends JpaRepository<StoreProductAttrValue, Long>, JpaSpecificationExecutor {

    //@Modifying
    // @Query(value = "delete from store_product_attr_value where product_id =?1",nativeQuery = true)
    void deleteByProductId(Long id);

    @Query(value = "select sum(stock)  from store_product_attr_value " + "where product_id = ?1", nativeQuery = true)
    Integer sumStock(Long productId);

    List<StoreProductAttrValue> findAllByProductIdAndDeleted(Long productId, Integer deleted);

    StoreProductAttrValue findByProductIdAndSuk(Long productId, String suk);
}
