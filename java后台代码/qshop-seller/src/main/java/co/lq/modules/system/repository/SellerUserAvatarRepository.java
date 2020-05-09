package co.lq.modules.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.system.domain.SellerUserAvatar;

/**
 * @author billy
 * @date 2018-11-22
 */
public interface SellerUserAvatarRepository
        extends JpaRepository<SellerUserAvatar, Long>, JpaSpecificationExecutor<SellerUserAvatar> {

}
