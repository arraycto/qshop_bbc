package co.lq.modules.shop.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.entity.StoreProductAttrValue;
import co.lq.modules.shop.web.param.StoreProductAttrValueQueryParam;
import co.lq.modules.shop.web.vo.StoreProductAttrValueQueryVo;

/**
 * <p>
 * 商品属性值表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-10-23
 */
@Repository
public interface StoreProductAttrValueMapper extends BaseMapper<StoreProductAttrValue> {

    @Select("select sum(stock) from store_product_attr_value " + "where product_id = #{productId}")
    Integer sumStock(Long productId);

    @Update("update store_product_attr_value set stock=stock-#{num}, sales=sales+#{num}"
            + " where product_id=#{productId} and `unique`=#{unique}")
    int decStockIncSales(@Param("num") int num, @Param("productId") long productId, @Param("unique") String unique);

    @Update("update store_product_attr_value set stock=stock+#{num}, sales=sales-#{num}"
            + " where product_id=#{productId} and `unique`=#{unique}")
    int incStockDecSales(@Param("num") int num, @Param("productId") long productId, @Param("unique") String unique);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreProductAttrValueQueryVo getStoreProductAttrValueById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param storeProductAttrValueQueryParam
     * @return
     */
    IPage<StoreProductAttrValueQueryVo> getStoreProductAttrValuePageList(@Param("page") Page page,
                                                                         @Param("param") StoreProductAttrValueQueryParam storeProductAttrValueQueryParam);

}
