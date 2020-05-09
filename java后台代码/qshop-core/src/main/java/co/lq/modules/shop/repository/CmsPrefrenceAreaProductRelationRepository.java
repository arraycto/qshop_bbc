package co.lq.modules.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.shop.domain.CmsPrefrenceAreaProductRelation;

/**
 * @author billy
 * @date 2020-04-11
 */
public interface CmsPrefrenceAreaProductRelationRepository extends JpaRepository<CmsPrefrenceAreaProductRelation, Long>,
        JpaSpecificationExecutor<CmsPrefrenceAreaProductRelation> {
    List<CmsPrefrenceAreaProductRelation> findByProductIdAndDeleted(Long productId, Integer deleted);
}
