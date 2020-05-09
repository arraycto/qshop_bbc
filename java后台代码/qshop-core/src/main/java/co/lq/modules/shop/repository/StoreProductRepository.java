package co.lq.modules.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import co.lq.modules.shop.domain.StoreProduct;

/**
 * @author billy
 * @date 2019-10-04
 */
public interface StoreProductRepository extends JpaRepository<StoreProduct, Long>, JpaSpecificationExecutor {
    @Modifying
    @Query(value = "update store_product set is_show = ?1 where id = ?2", nativeQuery = true)
    void updateOnsale(int status, Long id);

    @Modifying
    @Query(value = "update store_product set is_del = ?1 where id = ?2", nativeQuery = true)
    void updateDel(int status, Long id);

    @Modifying
    @Query(value = "update store_product set verify_status = ?1 where id = ?2", nativeQuery = true)
    void updateOnVerify(int status, long id);
}
