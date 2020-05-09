package co.lq.modules.mnt.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;

import co.lq.modules.mnt.domain.DeployHistory;
import co.lq.modules.mnt.service.dto.DeployHistoryDto;
import co.lq.modules.mnt.service.dto.DeployHistoryQueryCriteria;

/**
 * @author zhanghouying
 */
public interface DeployHistoryService {

    /**
     * 分页查询
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(DeployHistoryQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部
     *
     * @param criteria 条件
     * @return /
     */
    List<DeployHistoryDto> queryAll(DeployHistoryQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    DeployHistoryDto findById(String id);

    /**
     * 创建
     *
     * @param resources /
     * @return /
     */
    DeployHistoryDto create(DeployHistory resources);

    /**
     * 删除
     *
     * @param ids /
     */
    void delete(Set<String> ids);

    /**
     * 导出数据
     *
     * @param queryAll /
     * @param response /
     * @throws IOException /
     */
    void download(List<DeployHistoryDto> queryAll, HttpServletResponse response) throws IOException;
}
