package co.lq.modules.activity.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import co.lq.modules.activity.domain.StoreCombination;
import co.lq.modules.activity.service.dto.StoreCombinationDTO;
import co.lq.modules.activity.service.dto.StoreCombinationQueryCriteria;

/**
 * @author billy
 * @date 2019-11-18
 */
public interface StoreCombinationService {

    void onSale(Long id, Integer status);

    void onVerify(Long id, Integer status);

    /**
     * 查询数据分页
     *
     * @param criteria
     * @param pageable
     * @return
     */
    //@Cacheable
    Map<String, Object> queryAll(StoreCombinationQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria
     * @return
     */
    //@Cacheable
    List<StoreCombinationDTO> queryAll(StoreCombinationQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    StoreCombinationDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    StoreCombinationDTO create(StoreCombination resources);

    /**
     * 编辑
     *
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(StoreCombination resources);

    /**
     * 删除
     *
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}
