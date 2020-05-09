package co.lq.modules.shop.service;

import java.io.Serializable;
import java.util.List;

import co.lq.modules.shop.entity.StoreSettle;
import co.lq.modules.shop.web.param.StoreSettleQueryParam;
import co.lq.modules.shop.web.vo.StoreSettleQueryVo;

/**
 * 店铺入驻服务
 *
 * @author songbin
 * @since 2020年3月31日 下午2:16:08
 */
public interface ApiStoreSettleService {
    /**
     * 根据ID获取查询对象
     *
     * @param id 主键
     * @return StoreSettleQueryVo
     */
    StoreSettleQueryVo getStoreById(Serializable id);

    /**
     * 获取店铺列表
     *
     * @return List<StoreSettleQueryVo>
     */
    List<StoreSettleQueryVo> getList();

    /**
     * 根据ID获取查询对象
     *
     * @param storeSettleQueryParam 查询条件
     * @return StoreSettleQueryVo
     */
    StoreSettleQueryVo getShopSetleByStoreId(StoreSettleQueryParam storeSettleQueryParam);

    /**
     * 保存店铺信息
     *
     * @param storeSettle 店铺数据
     */
    Boolean saveShopSettle(StoreSettle storeSettle);

}
