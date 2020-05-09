package co.lq.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.lq.domain.EmailConfig;

/**
 * @author billy
 * @date 2018-12-26
 */
public interface EmailRepository extends JpaRepository<EmailConfig, Long> {
}
