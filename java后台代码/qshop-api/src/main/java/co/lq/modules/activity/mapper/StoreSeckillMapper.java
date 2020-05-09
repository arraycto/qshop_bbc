package co.lq.modules.activity.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.activity.entity.StoreSeckill;
import co.lq.modules.activity.web.param.StoreSeckillQueryParam;
import co.lq.modules.activity.web.vo.StoreSeckillQueryVo;
import co.lq.modules.shop.web.vo.StoreProductQueryVo;

/**
 * <p>
 * 商品秒杀产品表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-12-14
 */
@Repository
public interface StoreSeckillMapper extends BaseMapper<StoreSeckill> {

    @Update("update store_seckill set stock=stock+#{num}, sales=sales-#{num}" + " where id=#{seckillId}")
    int incStockDecSales(@Param("num") int num, @Param("seckillId") long seckillId);

    @Update("update store_seckill set stock=stock-#{num}, sales=sales+#{num}" + " where id=#{seckillId}")
    int decStockIncSales(@Param("num") int num, @Param("seckillId") long seckillId);

    @Select("SELECT c.id,c.image,c.price,c.title as productName,c.is_show as isShow,c.cost,"
            + "c.is_postage as isPostage,c.postage,c.sales,c.stock,c.is_del as isDel" + " FROM store_seckill c "
            + " WHERE c.id = #{id} ")
    StoreProductQueryVo seckillInfo(long id);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreSeckillQueryVo getStoreSeckillById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param storeSeckillQueryParam
     * @return
     */
    IPage<StoreSeckillQueryVo> getStoreSeckillPageList(@Param("page") Page page,
                                                       @Param("param") StoreSeckillQueryParam storeSeckillQueryParam);

    @Select("select t.id, t.image, t.images, t.title, t.info, t.price, t.cost, t.sort, t.stock, t.sales, "
            + "t.postage, t.description,  t.status, t.num from store_seckill t"
            + "INNER JOIN store_product s ON s.id=t.product_id "
            + "WHERE t.is_show = 1 AND t.is_del = 0 AND t.start_time < unix_timestamp(now()) "
            + "AND t.stop_time > unix_timestamp(now()) ORDER BY t.sort desc,t.id desc")
    List<StoreSeckillQueryVo> getCombList(Page<StoreSeckill> pageModel, @Param("time") String time);
}
