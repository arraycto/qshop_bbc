package co.lq.mp.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import co.lq.mp.domain.Cache;
import co.lq.mp.service.dto.CacheDTO;
import co.lq.mp.service.dto.CacheQueryCriteria;

/**
 * @author billy
 * @date 2019-10-06
 */
public interface CacheService {

    /**
     * 查询数据分页
     *
     * @param criteria
     * @param pageable
     * @return
     */
    //@Cacheable
    Map<String, Object> queryAll(CacheQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria
     * @return
     */
    //@Cacheable
    List<CacheDTO> queryAll(CacheQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param key
     * @return
     */
    //@Cacheable(key = "#p0")
    CacheDTO findById(String key);

    /**
     * 创建
     *
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    CacheDTO create(Cache resources);

    /**
     * 编辑
     *
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(Cache resources);

    /**
     * 删除
     *
     * @param key
     */
    //@CacheEvict(allEntries = true)
    void delete(String key);

    boolean isExist(String key);
}
