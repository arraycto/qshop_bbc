package co.lq.modules.system.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;

import co.lq.modules.system.domain.SellerJob;
import co.lq.modules.system.service.dto.SellerJobDTO;
import co.lq.modules.system.service.dto.SellerJobQueryCriteria;

/**
 * @author billy
 * @date 2019-03-29
 */
public interface SellerJobService {

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    SellerJobDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return /
     */
    SellerJobDTO create(SellerJob resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(SellerJob resources);

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
    Map<String, Object> queryAll(SellerJobQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部数据
     *
     * @param criteria /
     * @return /
     */
    List<SellerJobDTO> queryAll(SellerJobQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<SellerJobDTO> queryAll, HttpServletResponse response) throws IOException;
}
