package co.lq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.domain.QiniuContent;

/**
 * @author billy
 * @date 2018-12-31
 */
public interface QiniuContentRepository
        extends JpaRepository<QiniuContent, Long>, JpaSpecificationExecutor<QiniuContent> {

    /**
     * 根据key查询
     *
     * @param key 文件名
     * @return QiniuContent
     */
    QiniuContent findByKey(String key);
}
