package co.lq.modules.activity.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;

import co.lq.modules.activity.domain.StoreSeckill;
import co.lq.modules.activity.service.dto.StoreSeckillDTO;
import co.lq.modules.activity.service.dto.StoreSeckillQueryCriteria;

/**
 * @author billy
 * @date 2019-12-14
 */
public interface StoreSeckillService {

    /**
     * 查询数据分页
     *
     * @param criteria
     * @param pageable
     * @return
     */
    //@Cacheable
    Map<String, Object> queryAll(StoreSeckillQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria
     * @return
     */
    //@Cacheable
    List<StoreSeckillDTO> queryAll(StoreSeckillQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    StoreSeckillDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    StoreSeckillDTO create(StoreSeckill resources);

    /**
     * 编辑
     *
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(StoreSeckill resources);

    /**
     * 删除
     *
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);

    void onVerify(Long id, Integer status);

    /**
     * 导出数据
     *
     * @param all 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<StoreSeckillDTO> all, HttpServletResponse response) throws IOException;
}
