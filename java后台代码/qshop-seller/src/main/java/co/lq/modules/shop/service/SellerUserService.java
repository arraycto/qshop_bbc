package co.lq.modules.shop.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.domain.User;
import co.lq.modules.shop.service.dto.UserDTO;
import co.lq.modules.shop.service.dto.UserMoneyDTO;
import co.lq.modules.shop.service.dto.UserQueryCriteria;

/**
 * @author billy
 * @date 2019-10-06
 */
public interface SellerUserService extends BaseService<User> {

    void updateMoney(UserMoneyDTO param);

    /**
     * 查询数据分页
     *
     * @param criteria
     * @param page
     * @return
     */
    //@Cacheable
    Map<String, Object> queryAll(UserQueryCriteria criteria, Page page);

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
     * @param storeId
     * @return
     */
    //@CacheEvict(allEntries = true)
    UserDTO create(User resources, Long storeId);

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
     * @param storeId
     */
    //@CacheEvict(allEntries = true)
    void delete(Long uid, Long storeId);

    void onStatus(Long uid, Integer status);

    void incBrokeragePrice(double price, long uid);
}
