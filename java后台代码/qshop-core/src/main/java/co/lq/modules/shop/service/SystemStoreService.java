package co.lq.modules.shop.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;

import co.lq.modules.shop.domain.SystemStore;
import co.lq.modules.shop.service.dto.SystemStoreDto;
import co.lq.modules.shop.service.dto.SystemStoreQueryCriteria;

/**
 * @author billy
 * @date 2020-03-03
 */
public interface SystemStoreService {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String,Object>
     */
    Map<String, Object> queryAll(SystemStoreQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<SystemStoreDto>
     */
    List<SystemStoreDto> queryAll(SystemStoreQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return SystemStoreDto
     */
    SystemStoreDto findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return SystemStoreDto
     */
    SystemStoreDto create(SystemStore resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(SystemStore resources);

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
    void download(List<SystemStoreDto> all, HttpServletResponse response) throws IOException;
}
