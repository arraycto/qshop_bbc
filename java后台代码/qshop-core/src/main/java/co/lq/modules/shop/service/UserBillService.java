package co.lq.modules.shop.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import co.lq.modules.shop.domain.UserBill;
import co.lq.modules.shop.service.dto.UserBillDTO;
import co.lq.modules.shop.service.dto.UserBillQueryCriteria;

/**
 * @author billy
 * @date 2019-11-06
 */
//@CacheConfig(cacheNames = "userBill")
public interface UserBillService {

    /**
     * 查询数据分页
     *
     * @param criteria
     * @param pageable
     * @return
     */
    //@Cacheable
    Map<String, Object> queryAll(UserBillQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria
     * @return
     */
    //@Cacheable
    List<UserBillDTO> queryAll(UserBillQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    UserBillDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    UserBillDTO create(UserBill resources);

    /**
     * 编辑
     *
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(UserBill resources);

    /**
     * 删除
     *
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}
