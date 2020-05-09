package co.lq.modules.shop.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import co.lq.modules.shop.domain.SystemConfig;
import co.lq.modules.shop.service.dto.SystemConfigDTO;
import co.lq.modules.shop.service.dto.SystemConfigQueryCriteria;

/**
 * @author billy
 * @date 2019-10-10
 */
//@CacheConfig(cacheNames = "systemConfig")
public interface SystemConfigService {

    /**
     * 查询数据分页
     *
     * @param criteria
     * @param pageable
     * @return
     */
    //@Cacheable
    Map<String, Object> queryAll(SystemConfigQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria
     * @return
     */
    //@Cacheable
    List<SystemConfigDTO> queryAll(SystemConfigQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    SystemConfigDTO findById(Integer id);

    /**
     * 创建
     *
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    SystemConfigDTO create(SystemConfig resources);

    /**
     * 编辑
     *
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(SystemConfig resources);

    /**
     * 删除
     *
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Integer id);

    SystemConfig findByKey(String str);

    String getData(String name);
}
