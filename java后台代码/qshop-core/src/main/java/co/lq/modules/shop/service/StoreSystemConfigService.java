package co.lq.modules.shop.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;

import co.lq.modules.shop.domain.StoreSystemConfig;
import co.lq.modules.shop.service.dto.StoreSystemConfigDTO;
import co.lq.modules.shop.service.dto.StoreSystemConfigQueryCriteria;

/**
 * @author billy
 * @date 2020-03-11
 */
public interface StoreSystemConfigService {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String,Object>
     */
    Map<String, Object> queryAll(StoreSystemConfigQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<StoreSystemConfigDto>
     */
    List<StoreSystemConfigDTO> queryAll(StoreSystemConfigQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return StoreSystemConfigDto
     */
    StoreSystemConfigDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return StoreSystemConfigDto
     */
    StoreSystemConfigDTO create(StoreSystemConfig resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(StoreSystemConfig resources);

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
    void download(List<StoreSystemConfigDTO> all, HttpServletResponse response) throws IOException;

    StoreSystemConfig findByKey(String str);
}
