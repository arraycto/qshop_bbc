package co.lq.modules.shop.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import co.lq.modules.shop.domain.SystemGroupData;
import co.lq.modules.shop.service.dto.SystemGroupDataDTO;
import co.lq.modules.shop.service.dto.SystemGroupDataQueryCriteria;

/**
 * @author billy
 * @date 2019-10-18
 */
//@CacheConfig(cacheNames = "systemGroupData")
public interface SystemGroupDataService {

    /**
     * 查询数据分页
     *
     * @param criteria
     * @param pageable
     * @return
     */
    //@Cacheable
    Map<String, Object> queryAll(SystemGroupDataQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria
     * @return
     */
    //@Cacheable
    List<SystemGroupDataDTO> queryAll(SystemGroupDataQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    SystemGroupDataDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    SystemGroupDataDTO create(SystemGroupData resources);

    /**
     * 编辑
     *
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(SystemGroupData resources);

    /**
     * 删除
     *
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}
