package co.lq.modules.shop.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import co.lq.modules.shop.domain.UserTask;
import co.lq.modules.shop.service.dto.UserTaskDTO;
import co.lq.modules.shop.service.dto.UserTaskQueryCriteria;

/**
 * @author billy
 * @date 2019-12-04
 */
public interface UserTaskService {

    /**
     * 查询数据分页
     *
     * @param criteria
     * @param pageable
     * @return
     */
    //@Cacheable
    Map<String, Object> queryAll(UserTaskQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria
     * @return
     */
    //@Cacheable
    List<UserTaskDTO> queryAll(UserTaskQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    UserTaskDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    UserTaskDTO create(UserTask resources);

    /**
     * 编辑
     *
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(UserTask resources);

    /**
     * 删除
     *
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);

    List<Map<String, Object>> getTaskType();
}
