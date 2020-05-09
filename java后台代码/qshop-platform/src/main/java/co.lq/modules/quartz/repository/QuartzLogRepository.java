package co.lq.modules.quartz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.quartz.domain.QuartzLog;

/**
 * @author billy
 * @date 2019-01-07
 */
public interface QuartzLogRepository extends JpaRepository<QuartzLog, Long>, JpaSpecificationExecutor<QuartzLog> {

}
