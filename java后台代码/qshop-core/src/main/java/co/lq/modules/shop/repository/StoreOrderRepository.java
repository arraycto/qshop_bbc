package co.lq.modules.shop.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import co.lq.modules.shop.domain.StoreOrder;
import co.lq.modules.shop.service.dto.ChartDataDTO;

/**
 * @author billy
 * @date 2019-10-14
 */
public interface StoreOrderRepository extends JpaRepository<StoreOrder, Long>, JpaSpecificationExecutor {

    //今天 //上周 //本月
    int countByPayTimeGreaterThanEqual(int time);

    //今天 //上周 //本月
    int countByShopIdAndPayTimeGreaterThanEqual(Long shopId, int time);

    //昨天
    int countByPayTimeLessThanAndPayTimeGreaterThanEqual(int timeO, int timeT);

    //昨天
    int countByShopIdAndPayTimeLessThanAndPayTimeGreaterThanEqual(Long shopId, int timeO, int timeT);

    @Query(value = "select IFNULL(sum(pay_price),0)  from store_order "
            + "where refund_status=0 and is_del=0 and paid=1 and  pay_time >= ?1", nativeQuery = true)
    double sumPrice(Integer time);

    @Query(value = "select IFNULL(sum(pay_price),0)  from store_order "
            + "where refund_status=0 and is_del=0 and paid=1 and shop_id = ?1 and pay_time >= ?2", nativeQuery = true)
    double sumStorePrice(Long shopId, Integer time);

    @Query(value = "select IFNULL(sum(pay_price),0)  from store_order "
            + "where refund_status=0 and is_del=0 and paid=1 and  pay_time >= ?1 and pay_time < ?2", nativeQuery = true)
    double sumTPrice(Integer timeO, Integer timeT);

    @Query(value = "select IFNULL(sum(pay_price),0)  from store_order "
            + "where refund_status=0 and is_del=0 and paid=1 and shop_id = ?1 and pay_time >= ?2 and pay_time < ?3", nativeQuery = true)
    double sumStoreTPrice(Long shopId, Integer timeO, Integer timeT);

    @Query(value = "SELECT IFNULL(sum(pay_price),0) as num," + "FROM_UNIXTIME(add_time, '%m-%d') as time "
            + " FROM store_order where refund_status=0 and is_del=0 and paid=1 and pay_time >= ?1"
            + " GROUP BY FROM_UNIXTIME(add_time,'%Y-%m-%d') " + " ORDER BY add_time ASC", nativeQuery = true)
    List<ChartDataDTO> chartList(Integer time);

    @Query(value = "SELECT IFNULL(sum(pay_price),0) as num," + "FROM_UNIXTIME(add_time, '%m-%d') as time "
            + " FROM store_order where refund_status=0 and is_del=0 and paid=1 and shop_id = ?1 and pay_time >= ?2"
            + " GROUP BY FROM_UNIXTIME(add_time,'%Y-%m-%d') " + " ORDER BY add_time ASC", nativeQuery = true)
    List<ChartDataDTO> storeChartList(Long shopId, Integer time);

    @Query(value = "SELECT count(id) as num," + "FROM_UNIXTIME(add_time, '%m-%d') as time "
            + " FROM store_order where refund_status=0 and is_del=0 and paid=1 and pay_time >= ?1"
            + " GROUP BY FROM_UNIXTIME(add_time,'%Y-%m-%d') " + " ORDER BY add_time ASC", nativeQuery = true)
    List<ChartDataDTO> chartListT(Integer time);

    @Query(value = "SELECT count(id) as num," + "FROM_UNIXTIME(add_time, '%m-%d') as time "
            + " FROM store_order where refund_status=0 and is_del=0 and paid=1 and shop_id = ?1 and pay_time >= ?2"
            + " GROUP BY FROM_UNIXTIME(add_time,'%Y-%m-%d') " + " ORDER BY add_time ASC", nativeQuery = true)
    List<ChartDataDTO> storeChartListT(Long shopId, Integer time);

    /**
     * findByUnique
     *
     * @param unique
     * @return
     */
    StoreOrder findByUnique(String unique);

    //今天 //上周 //本月
    @Query(value = "select c.* from store_order as o " + "left join store_order_cart_info as c on c.oid=o.id "
            + "where o.pid>0 and o.refund_status=0 and o.is_del=0 and o.paid=1 and  o.pay_time >= ?1", nativeQuery = true)
    List<Map<String, Object>> getOrderCartListByPayTimeGreaterThanEqual(int time);

    //今天 //上周 //本月
    @Query(value = "select c.* from store_order as o " + "left join store_order_cart_info as c on c.oid=o.id "
            + "where o.pid>0 and o.shop_id=?1 and o.refund_status=0 and o.is_del=0 and o.paid=1 and  o.pay_time >= ?2", nativeQuery = true)
    List<Map<String, Object>> getOrderCartListByPayTimeGreaterThanEqual(Long shopId, int time);

    //昨日
    @Query(value = "select c.* from store_order as o " + "left join store_order_cart_info as c on c.oid=o.id "
            + "where o.pid>0 and o.refund_status=0 and o.is_del=0 and o.paid=1 and  o.pay_time >= ?1 and o.pay_time <= ?2", nativeQuery = true)
    List<Map<String, Object>> getOrderCartListLastDay(int timeS, int timeE);

    //昨日
    @Query(value = "select o.total_price, o.store_promotion_price, c.* from store_order as o "
            + "left join store_order_cart_info as c on c.oid=o.id "
            + "where o.pid>0 and o.shop_id=?1 and o.refund_status=0 and o.is_del=0 and o.paid=1 and  o.pay_time >= ?2 and pay_time <=?3", nativeQuery = true)
    List<Map<String, Object>> getShopOrderCartListLastDay(Long shopId, int timeS, int timeE);

    @Query(value = "select o.shop_id, sum(o.pay_price) as totalAmount, s.name as shopName from store_order as o left join store as s on o.shop_id=s.id where o.hidden=0 and o.is_del=0 and o.paid=1 and o.refund_status=0 and o.status=0 and o.pay_time>=?1 GROUP BY o.shop_id", nativeQuery = true)
    List<Map<String, Object>> getShopOrderSalesByPayTimeGreaterThanEqual(int time);

    @Query(value = "select o.shop_id, sum(o.pay_price) as totalAmount, s.name as shopName from store_order as o left join store as s on o.shop_id=s.id where o.hidden=0 and o.is_del=0 and o.paid=1 and o.refund_status=0 and o.status=0 and o.pay_time>=?1 and o.pay_time<=?2 GROUP BY o.shop_id", nativeQuery = true)
    List<Map<String, Object>> getShopOrderSalesByPayTimeLastDay(int timeS, int timeE);

    @Query(value = "select o.shop_id, count(o.id) as orderCnt, sum(o.refund_price) as totalRefundPrice, sum(o.total_price-o.refund_price-o.store_promotion_price+o.promotion_price-o.charge_price-o.commission-o.deduction_price) as totalSettlement, sum(o.total_price) as totalPrice, sum(o.freight_price) as totalFreightPrice, s.name as shopName from store_order as o left join store as s on o.shop_id=s.id where o.hidden=0 and o.is_del=0 and o.paid=1 and o.settlement_status=0 and o.pay_time>=?1 and o.pay_time<=?2 GROUP BY o.shop_id", nativeQuery = true)
    List<Map<String, Object>> getShopOrderSettlementByPayTimeLastDay(int timeS, int timeE);

    @Query(value = "select * from store_order where hidden=?1 and is_del=?2 and paid=?3 and settlement_status=?4 and pay_time>=?5 and pay_time<?6 limit ?7, ?8", nativeQuery = true)
    List<Map<String, Object>> getSettlementOrderList(Integer hidden, Integer isDel, Integer paid,
                                                     Integer settlementStatus, int timeS, int timeE, int limitStart,
                                                     int limit);

    @Query(value = "select count(`id`) as cnt from store_order where hidden=?1 and is_del=?2 and paid=?3 and settlement_status=?4 and pay_time>=?5 and pay_time<?6", nativeQuery = true)
    List<Map<String, Object>> countSettlementOrder(Integer hidden, Integer isDel, Integer paid,
                                                   Integer settlementStatus, int timeS, int timeE);
}
