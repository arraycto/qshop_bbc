package co.lq.modules.shop.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.entity.StoreCouponIssue;
import co.lq.modules.shop.web.param.StoreCouponIssueQueryParam;
import co.lq.modules.shop.web.vo.StoreCouponIssueQueryVo;

/**
 * <p>
 * 优惠券前台领取表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-10-27
 */
@Repository
public interface StoreCouponIssueMapper extends BaseMapper<StoreCouponIssue> {

    @Select("select A.cid,A.end_time as endTime,A.start_time as startTime,"
            + "A.is_permanent as isPermanent,A.remain_count as remainCount,"
            + "A.total_count as totalCount,A.id,B.coupon_price as couponPrice," + "B.use_min_price as useMinPrice"
            + " from store_coupon_issue A left join store_coupon B " + "on A.cid = B.id " + "where A.status =1 "
            + "AND (  (  A.start_time < unix_timestamp(now())  AND A.end_time > unix_timestamp(now()) ) "
            + "OR (  A.start_time = 0  AND A.end_time = 0 ) )" + " AND A.is_del = 0  AND "
            + "( A.remain_count > 0 OR A.is_permanent = 1 ) ORDER BY B.sort DESC")
    List<StoreCouponIssueQueryVo> selectList(Page page);

    @Select("select A.cid,A.end_time as endTime,A.start_time as startTime,"
            + "A.is_permanent as isPermanent,A.remain_count as remainCount," + "A.total_count as totalCount,A.id"
            + " from store_coupon_issue A" + " where A.status =1 and A.id=#{id}"
            + " AND (  (  A.start_time < unix_timestamp(now())  AND A.end_time > unix_timestamp(now()) ) "
            + "OR (  A.start_time = 0  AND A.end_time = 0 ) )" + " AND A.is_del = 0  AND "
            + "( A.remain_count > 0 OR A.is_permanent = 1 )")
    StoreCouponIssueQueryVo selectOne(long id);

    @Update("update store_coupon_issue set remain_count=remain_count-1" + " where id=#{id}")
    int decCount(@Param("id") long id);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreCouponIssueQueryVo getStoreCouponIssueById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param storeCouponIssueQueryParam
     * @return
     */
    IPage<StoreCouponIssueQueryVo> getStoreCouponIssuePageList(@Param("page") Page page,
                                                               @Param("param") StoreCouponIssueQueryParam storeCouponIssueQueryParam);

}
