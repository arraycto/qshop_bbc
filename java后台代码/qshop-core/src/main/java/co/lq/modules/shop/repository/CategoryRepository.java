package co.lq.modules.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import co.lq.modules.shop.domain.Category;

/**
 * @author billy
 * @date 2019-10-03
 */
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor {
    @Query(value = "select cate_name from category where id = ?1", nativeQuery = true)
    String findNameById(Long id);

    Category findByPid(Long id);
}
