package co.lq.modules.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.shop.domain.CmsSubjectProductRelation;

/**
 * @author billy
 * @date 2020-03-27
 */
public interface CmsSubjectProductRelationRepository
        extends JpaRepository<CmsSubjectProductRelation, Long>, JpaSpecificationExecutor<CmsSubjectProductRelation> {
    List<CmsSubjectProductRelation> findByProductIdAndDeleted(Long productId, Integer deleted);
}
