package co.lq.modules.shop.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import co.lq.modules.shop.domain.StoreOrder;
import co.lq.modules.shop.domain.User;
import co.lq.modules.shop.service.dto.UserDTO;
import co.lq.modules.shop.service.dto.UserMoneyDTO;
import co.lq.modules.shop.service.dto.UserQueryCriteria;

/**
 * @author billy
 * @date 2019-10-06
 */
//@CacheConfig(cacheNames = "user")
public interface UserService {

    void updateMoney(UserMoneyDTO param);

    /**
     * 查询数据分页
     *
     * @param criteria
     * @param pageable
     * @return
     */
    //@Cacheable
    Map<String, Object> queryAll(UserQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria
     * @return
     */
    //@Cacheable
    List<UserDTO> queryAll(UserQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param uid
     * @return
     */
    //@Cacheable(key = "#p0")
    UserDTO findById(Long uid);

    /**
     * 创建
     *
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    UserDTO create(User resources);

    /**
     * 编辑
     *
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(User resources);

    /**
     * 删除
     *
     * @param uid
     */
    //@CacheEvict(allEntries = true)
    void delete(Long uid);

    void onStatus(Long uid, Integer status);

    void incBrokeragePrice(double price, Long uid);

    boolean backOrderBrokerage(StoreOrder order);

    boolean backOrderBrokerageTwo(StoreOrder order);
}
