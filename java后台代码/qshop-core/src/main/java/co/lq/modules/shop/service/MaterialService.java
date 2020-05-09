package co.lq.modules.shop.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import co.lq.modules.shop.domain.Material;
import co.lq.modules.shop.service.dto.MaterialDto;
import co.lq.modules.shop.service.dto.MaterialQueryCriteria;

/**
 * @author billy
 * @date 2020-01-09
 */
public interface MaterialService {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String,Object>
     */
    Map<String, Object> queryAll(MaterialQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<MaterialDto>
     */
    List<MaterialDto> queryAll(MaterialQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return MaterialDto
     */
    MaterialDto findById(String id);

    /**
     * 创建
     *
     * @param resources /
     * @return MaterialDto
     */
    MaterialDto create(Material resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(Material resources);

    /**
     * 多选删除
     *
     * @param ids /
     */
    void deleteAll(String[] ids);

    void deleteById(String id);

}
