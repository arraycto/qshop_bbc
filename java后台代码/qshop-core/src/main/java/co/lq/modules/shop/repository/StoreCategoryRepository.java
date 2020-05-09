package co.lq.modules.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import co.lq.modules.shop.domain.StoreCategory;

/**
 * @author billy
 * @date 2020-03-10
*/
public interface StoreCategoryRepository
        extends JpaRepository<StoreCategory, Long>, JpaSpecificationExecutor<StoreCategory> {
    @Query(value = "select cate_name from store_category where id = ?1", nativeQuery = true)
    String findNameById(Long id);
    
    StoreCategory findByPid(Integer pid);
}