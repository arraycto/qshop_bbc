package co.lq.modules.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.shop.domain.StoreOrderCartInfo;

/**
 * @author billy
 * @date 2019-10-14
 */
public interface StoreOrderCartInfoRepository
        extends JpaRepository<StoreOrderCartInfo, Long>, JpaSpecificationExecutor {

    List<StoreOrderCartInfo> findByOid(long oid);

}
