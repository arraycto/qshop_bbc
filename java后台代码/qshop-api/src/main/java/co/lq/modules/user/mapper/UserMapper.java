package co.lq.modules.user.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.user.entity.User;
import co.lq.modules.user.web.dto.PromUserDTO;
import co.lq.modules.user.web.param.UserQueryParam;
import co.lq.modules.user.web.vo.UserQueryVo;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-10-16
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("<script>SELECT u.uid,u.nickname,u.avatar,from_unixtime(u.add_time,'%Y/%m/%d') as time,"
            + "u.spread_count as childCount,COUNT(o.id) as orderCount,"
            + "IFNULL(SUM(o.pay_price),0) as numberCount FROM `user` u " + "LEFT JOIN store_order o ON u.uid=o.uid "
            + "WHERE u.uid in <foreach item='id' index='index' collection='uids' "
            + " open='(' separator=',' close=')'>" + "   #{id}" + " </foreach> <if test='keyword != null'>"
            + " AND ( u.nickname LIKE CONCAT(CONCAT('%',#{keyword}),'%') OR u.phone LIKE CONCAT(CONCAT('%',#{keyword}),'%'))</if>"
            + " GROUP BY u.uid ORDER BY #{orderBy} " + "</script>")
    List<PromUserDTO> getUserSpreadCountList(Page page, @Param("uids") List uids, @Param("keyword") String keyword,
                                             @Param("orderBy") String orderBy);

    @Update("update `user` set now_money=now_money-#{payPrice}" + " where uid=#{uid}")
    int decPrice(@Param("payPrice") double payPrice, @Param("uid") long uid);

    @Update("update `user` set brokerage_price=brokerage_price+#{brokeragePrice}" + " where uid=#{uid}")
    int incBrokeragePrice(@Param("brokeragePrice") double brokeragePrice, @Param("uid") long uid);

    @Update("update `user` set pay_count=pay_count+1" + " where uid=#{uid}")
    int incPayCount(@Param("uid") long uid);

    @Update("update `user` set now_money=now_money+#{price}" + " where uid=#{uid}")
    int incMoney(@Param("uid") long uid, double price);

    @Update("update `user` set integral=integral-#{integral}" + " where uid=#{uid}")
    int decIntegral(@Param("integral") double integral, @Param("uid") long uid);

    @Update("update `user` set integral=integral+#{integral}" + " where uid=#{uid}")
    int incIntegral(@Param("integral") double integral, @Param("uid") long uid);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    UserQueryVo getUserById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param userQueryParam
     * @return
     */
    IPage<UserQueryVo> getUserPageList(@Param("page") Page page, @Param("param") UserQueryParam userQueryParam);

}
