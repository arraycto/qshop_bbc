package co.lq.mp.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import co.lq.mp.domain.WechatReply;
import co.lq.mp.service.dto.WechatReplyDTO;
import co.lq.mp.service.dto.WechatReplyQueryCriteria;

/**
 * @author billy
 * @date 2019-10-10
 */
public interface WechatReplyService {

    /**
     * 查询数据分页
     *
     * @param criteria
     * @param pageable
     * @return
     */
    //@Cacheable
    Map<String, Object> queryAll(WechatReplyQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria
     * @return
     */
    //@Cacheable
    List<WechatReplyDTO> queryAll(WechatReplyQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    WechatReplyDTO findById(Integer id);

    /**
     * 创建
     *
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    WechatReplyDTO create(WechatReply resources);

    /**
     * 编辑
     *
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(WechatReply resources);

    /**
     * 删除
     *
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Integer id);

    WechatReply isExist(String key);

}
