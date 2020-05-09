package co.lq.modules.activity.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import co.lq.modules.activity.domain.StoreCouponIssueUser;
import co.lq.modules.activity.service.dto.StoreCouponIssueUserDTO;
import co.lq.modules.activity.service.dto.StoreCouponIssueUserQueryCriteria;

/**
 * @author billy
 * @date 2019-11-09
 */
public interface StoreCouponIssueUserService {

    /**
     * 查询数据分页
     *
     * @param criteria
     * @param pageable
     * @return
     */
    //@Cacheable
    Map<String, Object> queryAll(StoreCouponIssueUserQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria
     * @return
     */
    //@Cacheable
    List<StoreCouponIssueUserDTO> queryAll(StoreCouponIssueUserQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    StoreCouponIssueUserDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    StoreCouponIssueUserDTO create(StoreCouponIssueUser resources);

    /**
     * 编辑
     *
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(StoreCouponIssueUser resources);

    /**
     * 删除
     *
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}
