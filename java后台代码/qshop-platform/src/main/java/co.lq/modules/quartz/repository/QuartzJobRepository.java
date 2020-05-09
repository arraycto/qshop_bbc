package co.lq.modules.quartz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.quartz.domain.QuartzJob;

/**
 * @author billy
 * @date 2019-01-07
 */
public interface QuartzJobRepository extends JpaRepository<QuartzJob, Long>, JpaSpecificationExecutor<QuartzJob> {

    /**
     * 查询启用的任务
     *
     * @return List
     */
    List<QuartzJob> findByIsPauseIsFalse();
}
