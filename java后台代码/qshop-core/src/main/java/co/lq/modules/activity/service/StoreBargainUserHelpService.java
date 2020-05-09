package co.lq.modules.activity.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import co.lq.modules.activity.domain.StoreBargainUserHelp;
import co.lq.modules.activity.service.dto.StoreBargainUserHelpDTO;
import co.lq.modules.activity.service.dto.StoreBargainUserHelpQueryCriteria;
import co.lq.modules.activity.service.dto.StoreBargainUserQueryCriteria;

/**
 * 砍价记录
 *
 * @author songbin
 * @since 2020年3月22日 下午4:41:55
 */
public interface StoreBargainUserHelpService {
    /**
     * 查询数据分页
     *
     * @param criteria
     * @param pageable
     * @return
     */
    //@Cacheable
    Map<String, Object> queryAll(StoreBargainUserHelpQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria
     * @return
     */
    //@Cacheable
    List<StoreBargainUserHelpDTO> queryAll(StoreBargainUserQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    StoreBargainUserHelpDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    StoreBargainUserHelpDTO create(StoreBargainUserHelp resources);

    /**
     * 编辑
     *
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(StoreBargainUserHelp resources);

    /**
     * 删除
     *
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}
