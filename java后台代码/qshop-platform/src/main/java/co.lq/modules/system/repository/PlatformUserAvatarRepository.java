package co.lq.modules.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.system.domain.PlatformUserAvatar;

/**
 * @author billy
 * @date 2018-11-22
 */
public interface PlatformUserAvatarRepository
        extends JpaRepository<PlatformUserAvatar, Long>, JpaSpecificationExecutor<PlatformUserAvatar> {

}
