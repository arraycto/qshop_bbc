package co.lq.modules.shop.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import co.lq.modules.shop.domain.Express;
import co.lq.modules.shop.service.dto.ExpressDTO;
import co.lq.modules.shop.service.dto.ExpressQueryCriteria;

/**
 * @author billy
 * @date 2019-12-12
 */
//@CacheConfig(cacheNames = "express")
public interface ExpressService {

    /**
     * 查询数据分页
     *
     * @param criteria
     * @param pageable
     * @return
     */
    //@Cacheable
    Map<String, Object> queryAll(ExpressQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria
     * @return
     */
    //@Cacheable
    List<ExpressDTO> queryAll(ExpressQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    ExpressDTO findById(Integer id);

    /**
     * 创建
     *
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    ExpressDTO create(Express resources);

    /**
     * 编辑
     *
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(Express resources);

    /**
     * 删除
     *
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Integer id);
}
