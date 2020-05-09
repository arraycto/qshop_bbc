package co.lq.modules.shop.service;

import java.io.Serializable;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.SystemStore;
import co.lq.modules.shop.web.param.SystemStoreQueryParam;
import co.lq.modules.shop.web.vo.SystemStoreQueryVo;

/**
 * <p>
 * 门店自提 服务类
 * </p>
 *
 * @author billy
 * @since 2020-03-04
 */
public interface ApiSystemStoreService extends BaseService<SystemStore> {

    SystemStoreQueryVo getStoreInfo(Long storeId);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SystemStoreQueryVo getSystemStoreById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param systemStoreQueryParam
     * @return
     */
    Paging<SystemStoreQueryVo> getSystemStorePageList(SystemStoreQueryParam systemStoreQueryParam) throws Exception;

}
