package co.lq.modules.mnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.mnt.domain.Database;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
public interface DatabaseRepository extends JpaRepository<Database, String>, JpaSpecificationExecutor<Database> {
}
