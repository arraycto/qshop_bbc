package co.lq.modules.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.shop.domain.UserTask;

/**
 * @author billy
 * @date 2019-12-04
 */
public interface UserTaskRepository extends JpaRepository<UserTask, Long>, JpaSpecificationExecutor {
}
