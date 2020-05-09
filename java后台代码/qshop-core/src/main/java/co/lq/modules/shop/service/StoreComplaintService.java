package co.lq.modules.shop.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;

import co.lq.modules.shop.domain.StoreComplaint;
import co.lq.modules.shop.service.dto.StoreComplaintDTO;
import co.lq.modules.shop.service.dto.StoreComplaintQueryCriteria;

/**
 * @author billy
 * @date 2020-03-11
 */
public interface StoreComplaintService {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String,Object>
     */
    Map<String, Object> queryAll(StoreComplaintQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<StoreComplaintDto>
     */
    List<StoreComplaintDTO> queryAll(StoreComplaintQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return StoreComplaintDto
     */
    StoreComplaintDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return StoreComplaintDto
     */
    StoreComplaintDTO create(StoreComplaint resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(StoreComplaint resources);

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
    void download(List<StoreComplaintDTO> all, HttpServletResponse response) throws IOException;
}
