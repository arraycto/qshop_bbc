package co.lq.modules.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.shop.domain.GroupActivity;

/**
* @author billy
* @date 2020-04-02
*/
public interface GroupActivityRepository extends JpaRepository<GroupActivity, Long>, JpaSpecificationExecutor<GroupActivity> {
}