package co.lq.modules.shop.repository;

import co.lq.modules.shop.domain.CmsPrefrenceArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author billy
* @date 2020-04-11
*/
public interface CmsPrefrenceAreaRepository extends JpaRepository<CmsPrefrenceArea, Long>, JpaSpecificationExecutor<CmsPrefrenceArea> {
}