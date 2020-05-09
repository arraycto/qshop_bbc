package co.lq.modules.shop.repository;

import co.lq.modules.shop.domain.SmsContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author billy
* @date 2020-03-27
*/
public interface SmsContentRepository extends JpaRepository<SmsContent, Long>, JpaSpecificationExecutor<SmsContent> {
}