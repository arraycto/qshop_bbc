package co.lq.modules.shop.repository;

import co.lq.modules.shop.domain.StoreGiftsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author billy
* @date 2020-03-27
*/
public interface StoreGiftsCategoryRepository extends JpaRepository<StoreGiftsCategory, Long>, JpaSpecificationExecutor<StoreGiftsCategory> {
}