package co.lq.modules.shop.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import co.lq.modules.shop.domain.StoreOrderStatus;
import co.lq.modules.shop.service.dto.StoreOrderStatusDTO;
import co.lq.modules.shop.service.dto.StoreOrderStatusQueryCriteria;

/**
 * @author billy
 * @date 2019-11-02
 */
//@CacheConfig(cacheNames = "storeOrderStatus")
public interface StoreOrderStatusService {

    /**
     * 查询数据分页
     *
     * @param criteria
     * @param pageable
     * @return
     */
    //@Cacheable
    Map<String, Object> queryAll(StoreOrderStatusQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria
     * @return
     */
    //@Cacheable
    List<StoreOrderStatusDTO> queryAll(StoreOrderStatusQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    StoreOrderStatusDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    StoreOrderStatusDTO create(StoreOrderStatus resources);

    /**
     * 编辑
     *
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(StoreOrderStatus resources);

    /**
     * 删除
     *
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}
