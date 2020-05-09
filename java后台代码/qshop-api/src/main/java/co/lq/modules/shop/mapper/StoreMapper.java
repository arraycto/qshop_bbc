package co.lq.modules.shop.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.entity.Store;
import co.lq.modules.shop.web.param.StoreQueryParam;
import co.lq.modules.shop.web.vo.StoreQueryVo;

/**
 * 店铺
 *
 * @author songbin
 * @since 2020年3月12日 下午8:45:04
 */
@Repository
public interface StoreMapper extends BaseMapper<Store> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreQueryVo getStoreById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param storeQueryParam
     * @return
     */
    IPage<StoreQueryVo> getStorePageList(@Param("page") Page page, @Param("param") StoreQueryParam storeQueryParam);

    /**
     * 根据ID获取查询对象
     *
     * @param storeQueryParam
     * @return
     */
    StoreQueryVo getShopInfoByUid(@Param("param") StoreQueryParam storeQueryParam);

    /**
     * 关注店铺
     *
     * @param id
     * @return
     */
    int updateCollectById(Long id);

    /**
     * 取关店铺
     *
     * @param id
     * @return
     */
    int minusCollectById(Long id);

    /**
     * 获取店铺列表
     *
     * @param storeQueryParam
     * @param start
     * @param limit
     * @return
     */
    List<StoreQueryVo> getShopList(@Param("param") StoreQueryParam storeQueryParam, @Param("start") int start,
                                   @Param("limit") int limit);

}
