package co.lq.modules.shop.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import co.lq.modules.shop.domain.StoreOrder;
import co.lq.modules.shop.service.dto.OrderTimeDataDTO;
import co.lq.modules.shop.service.dto.StoreOrderDTO;
import co.lq.modules.shop.service.dto.StoreOrderQueryCriteria;
import co.lq.modules.shop.service.dto.StoreOrderTimeDataDTO;

/**
 * @author billy
 * @date 2019-10-14
 */
//@CacheConfig(cacheNames = "storeOrder")
public interface StoreOrderService {
    OrderTimeDataDTO getOrderTimeData();

    OrderTimeDataDTO getStoreOrderTimeData(Long shopId);

    Map<String, Object> chartCount(Date time, Long storeId);

    Map<String, Object> chartStoreCount(Long shopId);

    String orderType(long id, long pinkId, long combinationId, long seckillId, long bargainId, int shippingType);

    void refund(StoreOrder resources);

    /**
     * 查询数据分页
     *
     * @param criteria
     * @param pageable
     * @return
     */
    //@Cacheable
    Map<String, Object> queryAll(StoreOrderQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria
     * @return
     */
    //@Cacheable
    List<StoreOrderDTO> queryAll(StoreOrderQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    StoreOrderDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    StoreOrderDTO create(StoreOrder resources);

    /**
     * 编辑
     *
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(StoreOrder resources);

    /**
     * 删除
     *
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * 获取店铺销售额
     *
     * @param timeS
     * @param timeE
     * @return
     */
    List<StoreOrderTimeDataDTO> getShopSalesAmount(int timeS, int timeE);

    /**
     * 获取指定店铺的销售情况
     *
     * @param timeS
     * @param timeE
     * @return
     */
    List<Map<String, Object>> getShopCartListBetweenTimes(int timeS, int timeE);

    int countSettlemnetOrder(Integer hidden, Integer isDel, Integer paid, Integer settlementStatus, int timeS,
                             int timeE);

    List<StoreOrder> getSettlementOrderList(Integer hidden, Integer isDel, Integer paid, Integer settlementStatus,
                                            int timeS, int timeE, int limitStart, int limit);
}
