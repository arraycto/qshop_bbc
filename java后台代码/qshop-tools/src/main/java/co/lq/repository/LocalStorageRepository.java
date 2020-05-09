package co.lq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.domain.LocalStorage;

/**
 * @author billy
 * @date 2019-09-05
 */
public interface LocalStorageRepository
        extends JpaRepository<LocalStorage, Long>, JpaSpecificationExecutor<LocalStorage> {
}
