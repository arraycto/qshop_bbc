package co.lq.modules.shop.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.entity.StoreCollect;
import co.lq.modules.shop.web.param.StoreCollectQueryParam;
import co.lq.modules.shop.web.vo.StoreCollectQueryVo;

/**
 * <p>
 * 店铺收藏表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2020-04-05
 */
@Repository
public interface StoreCollectMapper extends BaseMapper<StoreCollect> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreCollectQueryVo getStoreCollectById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param storeCollectQueryParam
     * @return
     */
    IPage<StoreCollectQueryVo> getStoreCollectPageList(@Param("page") Page page,
                                                       @Param("param") StoreCollectQueryParam storeCollectQueryParam);

    /**
     * 根据uid, storeId, type查询对象
     *
     * @param uid
     * @param storeId
     * @param type
     * @return
     */
    StoreCollectQueryVo getStoreCollectByUidAndStoreIdAndType(@Param("uid") Long uid, @Param("storeId") Long storeId,
                                                              @Param("type") String type);

}
