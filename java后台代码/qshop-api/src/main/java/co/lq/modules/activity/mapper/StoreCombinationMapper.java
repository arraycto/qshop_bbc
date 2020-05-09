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

import co.lq.modules.activity.entity.StoreCombination;
import co.lq.modules.activity.web.param.StoreCombinationQueryParam;
import co.lq.modules.activity.web.vo.StoreCombinationQueryVo;
import co.lq.modules.shop.web.vo.StoreProductQueryVo;

/**
 * <p>
 * 拼团产品表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-11-19
 */
@Repository
public interface StoreCombinationMapper extends BaseMapper<StoreCombination> {

    @Update("update store_combination set stock=stock-#{num}, sales=sales+#{num}" + " where id=#{combinationId}")
    int decStockIncSales(@Param("num") int num, @Param("combinationId") long combinationId);

    @Update("update store_combination set stock=stock+#{num}, sales=sales-#{num}" + " where id=#{combinationId}")
    int incStockDecSales(@Param("num") int num, @Param("combinationId") long combinationId);

    @Select("SELECT c.id,c.effective_time as effectiveTime,c.image,c.people,c.price,"
            + "c.title,c.unit_name as unitName,s.price as productPrice FROM store_combination c "
            + "INNER JOIN store_product s ON s.id=c.product_id "
            + " WHERE c.is_show = 1 AND c.is_del = 0 AND c.stock > 0 AND c.start_time < unix_timestamp(now()) "
            + " AND c.stop_time > unix_timestamp(now()) ORDER BY c.sort desc,c.id desc")
    List<StoreCombinationQueryVo> getCombList(Page page);

    @Select("SELECT c.id,c.effective_time as effectiveTime,c.image,c.people,c.price,c.browse,"
            + "c.description,c.image,c.images,c.info,c.is_postage as isPostage,c.postage,"
            + "c.product_id as productId,c.sales,c.start_time as startTime" + ",c.stock,c.stop_time stopTime,"
            + "c.title,c.unit_name as unitName,s.price as productPrice FROM store_combination c "
            + "INNER JOIN store_product s ON s.id=c.product_id "
            + " WHERE c.is_show = 1 AND c.is_del = 0 AND c.stock > 0 AND c.id = #{id} ")
    StoreCombinationQueryVo getCombDetail(long id);

    @Select("SELECT c.id,c.image,c.price,c.title as productName,c.is_show as isShow,c.cost,"
            + "c.is_postage as isPostage,c.postage,c.sales,c.stock,c.is_del as isDel" + " FROM store_combination c "
            + " WHERE c.id = #{id} ")
    StoreProductQueryVo combinatiionInfo(long id);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreCombinationQueryVo getStoreCombinationById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param storeCombinationQueryParam
     * @return
     */
    IPage<StoreCombinationQueryVo> getStoreCombinationPageList(@Param("page") Page page,
                                                               @Param("param") StoreCombinationQueryParam storeCombinationQueryParam);

}
