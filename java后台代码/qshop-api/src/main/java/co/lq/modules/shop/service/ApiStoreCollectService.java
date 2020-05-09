package co.lq.modules.shop.service;

import java.io.Serializable;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.StoreCollect;
import co.lq.modules.shop.web.param.StoreCollectQueryParam;
import co.lq.modules.shop.web.vo.StoreCollectQueryVo;

/**
 * <p>
 * 店铺收藏表 服务类
 * </p>
 *
 * @author billy
 * @since 2020-04-05
 */
public interface ApiStoreCollectService extends BaseService<StoreCollect> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreCollectQueryVo getStoreCollectById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param storeCollectQueryParam
     * @return
     */
    Paging<StoreCollectQueryVo> getStoreCollectPageList(StoreCollectQueryParam storeCollectQueryParam) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param uid
     * @param storeId
     * @param type
     * @return
     */
    StoreCollectQueryVo getStoreCollectByUidAndStoreIdAndType(Long uid, Long storeId, String type);

}
