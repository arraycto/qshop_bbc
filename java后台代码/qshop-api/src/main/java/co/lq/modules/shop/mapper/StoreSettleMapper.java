package co.lq.modules.shop.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.entity.StoreSettle;
import co.lq.modules.shop.web.param.StoreSettleQueryParam;
import co.lq.modules.shop.web.vo.StoreSettleQueryVo;

/**
 * 店铺入驻信息
 *
 * @author songbin
 * @since 2020年3月31日 下午2:06:50
 */
@Repository
public interface StoreSettleMapper extends BaseMapper<StoreSettle> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreSettleQueryVo getStoreSettleById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param storeSettleQueryParam
     * @return
     */
    IPage<StoreSettleQueryVo> getStoreSettlePageList(@Param("page") Page page,
                                                     @Param("param") StoreSettleQueryParam storeSettleQueryParam);

    /**
     * 根据ID获取查询对象
     *
     * @param storeSettleQueryParam
     * @return
     */
    StoreSettleQueryVo getShopInfoByStoreId(@Param("param") StoreSettleQueryParam storeSettleQueryParam);

}
