package co.lq.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.lq.domain.AlipayConfig;

/**
 * @author billy
 * @date 2018-12-31
 */
public interface AlipayRepository extends JpaRepository<AlipayConfig, Long> {
}
