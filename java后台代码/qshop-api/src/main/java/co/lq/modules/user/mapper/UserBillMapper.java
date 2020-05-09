package co.lq.modules.user.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.user.entity.UserBill;
import co.lq.modules.user.web.dto.BillDTO;
import co.lq.modules.user.web.dto.BillOrderRecordDTO;
import co.lq.modules.user.web.dto.SignDTO;
import co.lq.modules.user.web.dto.UserBillDTO;
import co.lq.modules.user.web.param.UserBillQueryParam;
import co.lq.modules.user.web.vo.UserBillQueryVo;

/**
 * <p>
 * 用户账单表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-10-27
 */
@Repository
public interface UserBillMapper extends BaseMapper<UserBill> {

    @Select("select IFNULL(sum(number),0) from user_bill "
            + "where status=1 and type='sign' and pm=1 and category='integral' " + "and uid=#{uid}")
    double sumIntegral(@Param("uid") long uid);

    @Select("SELECT FROM_UNIXTIME(a.add_time,'%Y-%m-%d') as addTime,a.title,a.number "
            + "FROM user_bill a INNER JOIN `user` u ON u.uid=a.uid WHERE a.category = 'integral'"
            + " AND a.type = 'sign' AND a.status = 1 AND a.uid = #{uid} " + "ORDER BY a.add_time DESC")
    List<SignDTO> getSignList(@Param("uid") long uid, Page page);

    @Select("SELECT o.order_id as orderId,FROM_UNIXTIME(b.add_time, '%Y-%m-%d %H:%i') as time,"
            + "b.number,u.avatar,u.nickname FROM user_bill b " + "INNER JOIN store_order o ON o.id=b.link_id "
            + "RIGHT JOIN `user` u ON u.uid=o.uid"
            + " WHERE b.uid = #{uid} AND ( FROM_UNIXTIME(b.add_time, '%Y-%m')= #{time} ) AND "
            + "b.category = 'now_money' AND b.type = 'brokerage' ORDER BY time DESC")
    List<BillOrderRecordDTO> getBillOrderRList(@Param("time") String time, @Param("uid") long uid);

    @Select("SELECT FROM_UNIXTIME(add_time,'%Y-%m') as time " + " FROM user_bill ${ew.customSqlSegment}")
    List<String> getBillOrderList(@Param(Constants.WRAPPER) Wrapper<UserBill> userWrapper, Page page);

    @Select("SELECT FROM_UNIXTIME(add_time,'%Y-%m') as time,group_concat(id SEPARATOR ',') ids "
            + " FROM user_bill ${ew.customSqlSegment}")
    List<BillDTO> getBillList(@Param(Constants.WRAPPER) Wrapper<UserBill> userWrapper, Page page);

    @Select("SELECT FROM_UNIXTIME(add_time,'%Y-%m-%d %H:%i') as add_time,title,number,pm "
            + " FROM user_bill ${ew.customSqlSegment}")
    List<UserBillDTO> getUserBillList(@Param(Constants.WRAPPER) Wrapper<UserBill> userWrapper);

    @Select("select IFNULL(sum(number),0) from user_bill "
            + "where status=1 and type='brokerage' and pm=1 and category='now_money' " + "and uid=#{uid}")
    double sumPrice(@Param("uid") long uid);

    @Select("select IFNULL(sum(number),0) from user_bill "
            + "where status=1 and type='brokerage' and pm=1 and category='now_money' "
            + "and uid=#{uid} and TO_DAYS(NOW()) - TO_DAYS(add_time) <= 1")
    double sumYesterdayPrice(@Param("uid") long uid);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    UserBillQueryVo getUserBillById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param userBillQueryParam
     * @return
     */
    IPage<UserBillQueryVo> getUserBillPageList(@Param("page") Page page,
                                               @Param("param") UserBillQueryParam userBillQueryParam);

}
