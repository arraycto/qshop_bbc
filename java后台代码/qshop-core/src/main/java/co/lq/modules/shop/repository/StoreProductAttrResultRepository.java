package co.lq.modules.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.shop.domain.StoreProductAttrResult;

/**
 * @author billy
 * @date 2019-10-13
 */
public interface StoreProductAttrResultRepository
        extends JpaRepository<StoreProductAttrResult, Long>, JpaSpecificationExecutor {

    /**
     * findByProductId
     *
     * @param product_id
     * @return
     */
    StoreProductAttrResult findByProductId(Long product_id);

    void deleteByProductId(Long product_id);
}
