package co.lq.modules.shop.repository;

import co.lq.modules.shop.domain.HomeAdvertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author billy
* @date 2020-03-13
*/
public interface HomeAdvertiseRepository extends JpaRepository<HomeAdvertise, Long>, JpaSpecificationExecutor<HomeAdvertise> {
}