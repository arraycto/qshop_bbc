package co.lq.modules.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.shop.domain.StoreCategoryAttr;

/**
 * @author billy
 * @date 2020-03-10
 */
public interface StoreCategoryAttrRepository
        extends JpaRepository<StoreCategoryAttr, Long>, JpaSpecificationExecutor<StoreCategoryAttr> {
}
