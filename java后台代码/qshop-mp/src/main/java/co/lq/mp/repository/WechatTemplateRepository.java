package co.lq.mp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.mp.domain.WechatTemplate;

/**
 * @author billy
 * @date 2019-12-10
 */
public interface WechatTemplateRepository extends JpaRepository<WechatTemplate, Integer>, JpaSpecificationExecutor {
    WechatTemplate findByTempkey(String key);
}
