package co.lq.mp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import co.lq.mp.domain.Article;

/**
 * @author billy
 * @date 2019-10-07
 */
public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor {
}
