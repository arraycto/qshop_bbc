package co.lq.modules.mnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.mnt.domain.DeployHistory;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
public interface DeployHistoryRepository
        extends JpaRepository<DeployHistory, String>, JpaSpecificationExecutor<DeployHistory> {
}
