package co.lq.modules.shop.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import co.lq.modules.shop.domain.User;

/**
 * @author billy
 * @date 2019-10-06
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor {

    @Modifying
    @Query(value = "update user set status = ?1 where uid = ?2", nativeQuery = true)
    void updateOnstatus(int status, long id);

    @Modifying
    @Query(value = "update user set now_money = now_money + ?1 where uid = ?2", nativeQuery = true)
    void updateMoney(double money, long id);

    @Modifying
    @Query(value = "update user set brokerage_price = brokerage_price+?1 where uid = ?2", nativeQuery = true)
    void incBrokeragePrice(double price, long id);

    @Query(value = "select FROM_UNIXTIME(add_time, '%Y-%m-%d') as staticsDate, COUNT(uid) as memberCount from user where add_time>= ?1 and add_time<=?2 group by add_time", nativeQuery = true)
    List<Map<String, Object>> getDayMemberIncrement(int timeS, int timeE);

}
