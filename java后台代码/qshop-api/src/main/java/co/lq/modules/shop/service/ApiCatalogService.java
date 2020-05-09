package co.lq.modules.shop.service;

import java.io.Serializable;
import java.util.List;

import co.lq.common.service.BaseService;
import co.lq.modules.shop.entity.Catalog;
import co.lq.modules.shop.web.vo.CatalogQueryVo;
import co.lq.utils.CatalogDTO;

/**
 * 平台店铺类目
 *
 * @author songbin
 * @since 2020年3月11日 下午5:44:56
 */
public interface ApiCatalogService extends BaseService<Catalog> {
    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    CatalogQueryVo getCatalogById(Serializable id) throws Exception;

    List<CatalogDTO> getList();

    List<String> getAllChilds(int catid);

    List<CatalogDTO> getFirstList();
}
