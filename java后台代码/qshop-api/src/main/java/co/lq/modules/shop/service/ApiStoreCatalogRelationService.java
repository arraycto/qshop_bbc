package co.lq.modules.shop.service;

import java.io.Serializable;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.StoreCatalogRelation;
import co.lq.modules.shop.web.param.StoreCatalogRelationQueryParam;
import co.lq.modules.shop.web.vo.StoreCatalogRelationQueryVo;

/**
 * <p>
 * 店铺类目关联表 服务类
 * </p>
 *
 * @author billy
 * @since 2020-04-23
 */
public interface ApiStoreCatalogRelationService extends BaseService<StoreCatalogRelation> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreCatalogRelationQueryVo getStoreCatalogRelationById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param storeCatalogRelationQueryParam
     * @return
     */
    Paging<StoreCatalogRelationQueryVo> getStoreCatalogRelationPageList(StoreCatalogRelationQueryParam storeCatalogRelationQueryParam)
            throws Exception;

}
