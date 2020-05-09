package co.lq.modules.shop.repository;

import co.lq.modules.shop.domain.SmsContentMsg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author billy
* @date 2020-03-27
*/
public interface SmsContentMsgRepository extends JpaRepository<SmsContentMsg, Long>, JpaSpecificationExecutor<SmsContentMsg> {
}