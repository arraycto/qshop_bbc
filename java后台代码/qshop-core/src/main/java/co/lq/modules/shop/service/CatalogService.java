package co.lq.modules.shop.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;

import co.lq.modules.shop.domain.Catalog;
import co.lq.modules.shop.service.dto.CatalogDTO;
import co.lq.modules.shop.service.dto.CatalogQueryCriteria;

/**
 * @author billy
 * @date 2020-03-10
 */
public interface CatalogService {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String,Object>
     */
    Map<String, Object> queryAll(CatalogQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<CatalogDTO>
     */
    List<CatalogDTO> queryAll(CatalogQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return CatalogDTO
     */
    CatalogDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return CatalogDTO
     */
    CatalogDTO create(Catalog resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(Catalog resources);

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
    void download(List<CatalogDTO> all, HttpServletResponse response) throws IOException;

    Object buildTree(List<CatalogDTO> catalogDTOS);
}
