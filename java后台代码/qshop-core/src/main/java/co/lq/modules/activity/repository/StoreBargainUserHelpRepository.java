package co.lq.modules.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.activity.domain.StoreBargainUserHelp;

/**
 * 砍价记录
 *
 * @author songbin
 * @since 2020年3月22日 下午4:38:38
 */
public interface StoreBargainUserHelpRepository
        extends JpaRepository<StoreBargainUserHelp, Long>, JpaSpecificationExecutor {
}
