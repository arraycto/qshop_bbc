package co.lq.modules.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.shop.domain.StoreBrand;

/**
 * @author billy
 * @date 2020-03-27
 */
public interface StoreBrandRepository extends JpaRepository<StoreBrand, Long>, JpaSpecificationExecutor<StoreBrand> {
}
