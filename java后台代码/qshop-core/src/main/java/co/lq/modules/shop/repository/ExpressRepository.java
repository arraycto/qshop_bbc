package co.lq.modules.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.modules.shop.domain.Express;

/**
 * @author billy
 * @date 2019-12-12
 */
public interface ExpressRepository extends JpaRepository<Express, Integer>, JpaSpecificationExecutor {

    /**
     * findByCode
     *
     * @param code
     * @return
     */
    Express findByCode(String code);
}
