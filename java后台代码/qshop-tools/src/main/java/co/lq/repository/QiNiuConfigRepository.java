package co.lq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import co.lq.domain.QiniuConfig;

/**
 * @author billy
 * @date 2018-12-31
 */
public interface QiNiuConfigRepository extends JpaRepository<QiniuConfig, Long> {

    /**
     * 编辑类型
     *
     * @param type
     */
    @Modifying
    @Query(value = "update QiniuConfig set type = ?1")
    void update(String type);
}
