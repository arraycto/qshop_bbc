package co.lq.modules.shop.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.entity.StoreProductRelation;
import co.lq.modules.shop.web.param.StoreProductRelationQueryParam;
import co.lq.modules.shop.web.vo.StoreProductRelationQueryVo;

/**
 * <p>
 * 商品点赞和收藏表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-10-23
 */
@Repository
public interface StoreProductRelationMapper extends BaseMapper<StoreProductRelation> {

    @Select("select B.id pid,A.category,B.product_name as productName,B.price,"
            + "B.ot_price as otPrice,B.sales,B.image,B.is_del as isDel,B.is_show as isShow"
            + " from store_product_relation A left join store_product B " + "on A.product_id = B.id where A.uid=#{uid}")
    List<StoreProductRelationQueryVo> selectList(Page page, @Param("uid") long uid);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreProductRelationQueryVo getStoreProductRelationById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param storeProductRelationQueryParam
     * @return
     */
    IPage<StoreProductRelationQueryVo> getStoreProductRelationPageList(@Param("page") Page page,
                                                                       @Param("param") StoreProductRelationQueryParam storeProductRelationQueryParam);

}
