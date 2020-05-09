package co.lq.modules.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.shop.domain.StoreSettle;

/**
 * @author billy
 * @date 2020-03-28
 */
public interface StoreSettleRepository extends JpaRepository<StoreSettle, Long>, JpaSpecificationExecutor<StoreSettle> {

    /**
     * 通过店铺id查询
     *
     * @param StoreId
     * @param deleted
     * @return
     */
    StoreSettle findByStoreIdAndDeleted(Long StoreId, Integer deleted);

    /**
     * 查询数量
     *
     * @param status
     * @param closed
     * @param deleted
     * @return
     */
    int countByStatusAndClosedAndDeleted(Integer status, Integer closed, Integer deleted);
}
