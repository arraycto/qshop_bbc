package co.lq.modules.shop.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;

import co.lq.modules.shop.domain.StoreVertifyRecord;
import co.lq.modules.shop.service.dto.StoreVertifyRecordDTO;
import co.lq.modules.shop.service.dto.StoreVertifyRecordQueryCriteria;

/**
 * @author billy
 * @date 2020-03-28
 */
public interface StoreVertifyRecordService {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String,Object>
     */
    Map<String, Object> queryAll(StoreVertifyRecordQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<StoreVertifyRecordDTO>
     */
    List<StoreVertifyRecordDTO> queryAll(StoreVertifyRecordQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return StoreVertifyRecordDTO
     */
    StoreVertifyRecordDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return StoreVertifyRecordDTO
     */
    StoreVertifyRecordDTO create(StoreVertifyRecord resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(StoreVertifyRecord resources);

    /**
     * 多选删除
     *
     * @param ids /
     */
    void deleteAll(Long[] ids);

    /**
     * 导出数据
     *
     * @param all 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<StoreVertifyRecordDTO> all, HttpServletResponse response) throws IOException;

    void onVerify(Long id, Integer status);

    void onClosed(Long id, Integer status);
}
