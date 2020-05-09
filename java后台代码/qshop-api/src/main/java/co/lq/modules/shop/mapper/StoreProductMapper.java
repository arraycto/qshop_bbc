package co.lq.modules.shop.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.entity.StoreProduct;
import co.lq.modules.shop.web.param.StoreProductQueryParam;
import co.lq.modules.shop.web.vo.StoreProductQueryVo;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-10-19
 */
@Repository
public interface StoreProductMapper extends BaseMapper<StoreProduct> {

    @Update("update store_product set stock=stock-#{num}, sales=sales+#{num}" + " where id=#{productId}")
    int decStockIncSales(@Param("num") int num, @Param("productId") long productId);

    @Update("update store_product set stock=stock+#{num}, sales=sales-#{num}" + " where id=#{productId}")
    int incStockDecSales(@Param("num") int num, @Param("productId") long productId);

    @Update("update store_product set sales=sales+#{num}" + " where id=#{productId}")
    int incSales(@Param("num") int num, @Param("productId") long productId);

    @Update("update store_product set sales=sales-#{num}" + " where id=#{productId}")
    int decSales(@Param("num") int num, @Param("productId") long productId);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreProductQueryVo getStoreProductById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param storeProductQueryParam
     * @return
     */
    IPage<StoreProductQueryVo> getStoreProductPageList(@Param("page") Page page,
                                                       @Param("param") StoreProductQueryParam storeProductQueryParam);

}
