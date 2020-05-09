package co.lq.modules.shop.repository;

import co.lq.modules.shop.domain.MemberSharedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author billy
* @date 2020-03-11
*/
public interface MemberSharedProductRepository extends JpaRepository<MemberSharedProduct, Long>, JpaSpecificationExecutor<MemberSharedProduct> {
}