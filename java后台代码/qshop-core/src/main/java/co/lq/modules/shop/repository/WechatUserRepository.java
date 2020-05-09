package co.lq.modules.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.shop.domain.WechatUser;

/**
 * @author billy
 * @date 2019-12-13
 */
public interface WechatUserRepository extends JpaRepository<WechatUser, Long>, JpaSpecificationExecutor {
}
