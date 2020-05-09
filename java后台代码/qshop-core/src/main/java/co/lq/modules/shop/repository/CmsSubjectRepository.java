package co.lq.modules.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.shop.domain.CmsSubject;

/**
* @author billy
* @date 2020-03-27
*/
public interface CmsSubjectRepository extends JpaRepository<CmsSubject, Long>, JpaSpecificationExecutor<CmsSubject> {
    List<CmsSubject> findByDeleted(Integer deleted);
}