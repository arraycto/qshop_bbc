package co.lq.modules.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.shop.domain.StoreProductReply;

/**
 * @author billy
 * @date 2019-11-03
 */
public interface StoreProductReplyRepository extends JpaRepository<StoreProductReply, Long>, JpaSpecificationExecutor {
}
