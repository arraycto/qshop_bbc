package co.lq.modules.shop.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;

import co.lq.modules.shop.domain.StoreSettle;
import co.lq.modules.shop.service.dto.StoreSettleDTO;
import co.lq.modules.shop.service.dto.StoreSettleQueryCriteria;

/**
 * @author billy
 * @date 2020-03-28
 */
public interface StoreSettleService {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String,Object>
     */
    Map<String, Object> queryAll(StoreSettleQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<StoreSettleDTO>
     */
    List<StoreSettleDTO> queryAll(StoreSettleQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return StoreSettleDTO
     */
    StoreSettleDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return StoreSettleDTO
     */
    StoreSettleDTO create(StoreSettle resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(StoreSettle resources);

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
    void download(List<StoreSettleDTO> all, HttpServletResponse response) throws IOException;

    int getCount(StoreSettleQueryCriteria criteria);
}
