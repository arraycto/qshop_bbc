package co.lq.mp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.mp.domain.WechatReply;

/**
 * @author billy
 * @date 2019-10-10
 */
public interface WechatReplyRepository extends JpaRepository<WechatReply, Integer>, JpaSpecificationExecutor {

    /**
     * findByKey
     *
     * @param key
     * @return
     */
    WechatReply findByKey(String key);
}
