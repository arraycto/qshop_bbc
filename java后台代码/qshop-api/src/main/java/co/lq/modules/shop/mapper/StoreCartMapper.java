package co.lq.modules.shop.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.entity.StoreCart;
import co.lq.modules.shop.web.param.StoreCartQueryParam;
import co.lq.modules.shop.web.vo.StoreCartQueryVo;

/**
 * <p>
 * 购物车表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-10-25
 */
@Repository
public interface StoreCartMapper extends BaseMapper<StoreCart> {

    @Select("select IFNULL(sum(cart_num),0) from store_cart "
            + "where is_pay=0 and is_del=0 and is_new=0 and uid=#{uid} and type=#{type}")
    int cartSum(@Param("uid") long uid, @Param("type") String type);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreCartQueryVo getStoreCartById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param storeCartQueryParam
     * @return
     */
    IPage<StoreCartQueryVo> getStoreCartPageList(@Param("page") Page page,
                                                 @Param("param") StoreCartQueryParam storeCartQueryParam);

}
