package co.lq.modules.activity.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.activity.entity.StoreBargain;
import co.lq.modules.activity.web.param.StoreBargainQueryParam;
import co.lq.modules.activity.web.vo.StoreBargainQueryVo;
import co.lq.modules.shop.web.vo.StoreProductQueryVo;

/**
 * <p>
 * 砍价表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-12-21
 */
@Repository
public interface StoreBargainMapper extends BaseMapper<StoreBargain> {

    @Update("update store_bargain set stock=stock+#{num}, sales=sales-#{num}" + " where id=#{bargainId}")
    int incStockDecSales(@Param("num") int num, @Param("bargainId") long bargainId);

    @Update("update store_bargain set stock=stock-#{num}, sales=sales+#{num}" + " where id=#{bargainId}")
    int decStockIncSales(@Param("num") int num, @Param("bargainId") long bargainId);

    @Select("SELECT c.id,c.image,c.min_price as price,c.price as otPrice,"
            + "c.title as productName,c.status as isShow,c.cost,"
            + "c.is_postage as isPostage,c.postage,c.sales,c.stock,c.is_del as isDel" + " FROM store_bargain c "
            + " WHERE c.id = #{id} ")
    StoreProductQueryVo bargainInfo(long id);

    @Select("select IFNULL(sum(look),0)" + "from store_bargain")
    int lookCount();

    @Select("select IFNULL(sum(share),0) as shareCount " + "from store_bargain")
    int shareCount();

    @Update("update store_bargain set share=share+1" + " where id=#{id}")
    int addBargainShare(@Param("id") long id);

    @Update("update store_bargain set look=look+1" + " where id=#{id}")
    int addBargainLook(@Param("id") long id);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreBargainQueryVo getStoreBargainById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param storeBargainQueryParam
     * @return
     */
    IPage<StoreBargainQueryVo> getStoreBargainPageList(@Param("page") Page page,
                                                       @Param("param") StoreBargainQueryParam storeBargainQueryParam);

}
