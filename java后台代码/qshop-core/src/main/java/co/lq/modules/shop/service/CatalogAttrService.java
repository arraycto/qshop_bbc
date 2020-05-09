package co.lq.modules.shop.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;

import co.lq.modules.shop.domain.CatalogAttr;
import co.lq.modules.shop.service.dto.CatalogAttrDTO;
import co.lq.modules.shop.service.dto.CatalogAttrQueryCriteria;

/**
 * @author billy
 * @date 2020-03-10
 */
public interface CatalogAttrService {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String,Object>
     */
    Map<String, Object> queryAll(CatalogAttrQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<QshopCatalogAttrDto>
     */
    List<CatalogAttrDTO> queryAll(CatalogAttrQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return QshopCatalogAttrDto
     */
    CatalogAttrDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return QshopCatalogAttrDto
     */
    CatalogAttrDTO create(CatalogAttr resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(CatalogAttr resources);

    /**
     * 多选删除
     *
     * @param ids /
     */
    void deleteAll(Long[] ids);

    /**
     * 导出数据
     *
     * @param all 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<CatalogAttrDTO> all, HttpServletResponse response) throws IOException;
}
