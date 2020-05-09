package co.lq.modules.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.activity.domain.StorePink;

/**
 * @author billy
 * @date 2019-11-18
 */
public interface StorePinkRepository extends JpaRepository<StorePink, Long>, JpaSpecificationExecutor {
    int countByCid(long cid);

    int countByCidAndKId(long cid, long kid);

    int countByKId(long kid);

    StorePink findByOrderIdKey(long id);

}
