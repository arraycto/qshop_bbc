package co.lq.modules.shop.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import co.lq.modules.shop.domain.MaterialGroup;
import co.lq.modules.shop.service.dto.MaterialGroupDto;
import co.lq.modules.shop.service.dto.MaterialGroupQueryCriteria;

/**
 * @author billy
 * @date 2020-01-09
 */
public interface MaterialGroupService {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String,Object>
     */
    Map<String, Object> queryAll(MaterialGroupQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<MaterialGroupDto>
     */
    List<MaterialGroupDto> queryAll(MaterialGroupQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return MaterialGroupDto
     */
    MaterialGroupDto findById(String id);

    /**
     * 创建
     *
     * @param resources /
     * @return MaterialGroupDto
     */
    MaterialGroupDto create(MaterialGroup resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(MaterialGroup resources);

    /**
     * 多选删除
     *
     * @param ids /
     */
    void deleteAll(String[] ids);

    void deleteById(String id);

}
