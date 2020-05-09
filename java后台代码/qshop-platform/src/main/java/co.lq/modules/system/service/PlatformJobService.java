package co.lq.modules.system.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;

import co.lq.modules.system.domain.PlatformJob;
import co.lq.modules.system.service.dto.PlatformJobDTO;
import co.lq.modules.system.service.dto.PlatformJobQueryCriteria;

/**
 * @author billy
 * @date 2019-03-29
 */
public interface PlatformJobService {

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    PlatformJobDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return /
     */
    PlatformJobDTO create(PlatformJob resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(PlatformJob resources);

    /**
     * 删除
     *
     * @param ids /
     */
    void delete(Set<Long> ids);

    /**
     * 分页查询
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Map<String, Object> queryAll(PlatformJobQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部数据
     *
     * @param criteria /
     * @return /
     */
    List<PlatformJobDTO> queryAll(PlatformJobQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<PlatformJobDTO> queryAll, HttpServletResponse response) throws IOException;
}
