package co.lq.mp.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import co.lq.mp.domain.WechatTemplate;
import co.lq.mp.service.dto.WechatTemplateDTO;
import co.lq.mp.service.dto.WechatTemplateQueryCriteria;

/**
 * @author billy
 * @date 2019-12-10
 */
public interface WechatTemplateService {

    WechatTemplate findByTempkey(String key);

    /**
     * 查询数据分页
     *
     * @param criteria
     * @param pageable
     * @return
     */
    //@Cacheable
    Map<String, Object> queryAll(WechatTemplateQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria
     * @return
     */
    //@Cacheable
    List<WechatTemplateDTO> queryAll(WechatTemplateQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    WechatTemplateDTO findById(Integer id);

    /**
     * 创建
     *
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    WechatTemplateDTO create(WechatTemplate resources);

    /**
     * 编辑
     *
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(WechatTemplate resources);

    /**
     * 删除
     *
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Integer id);
}
