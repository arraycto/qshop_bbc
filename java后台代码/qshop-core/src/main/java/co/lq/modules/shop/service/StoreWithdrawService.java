package co.lq.modules.shop.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;

import co.lq.modules.shop.domain.StoreWithdraw;
import co.lq.modules.shop.service.dto.StoreWithdrawDTO;
import co.lq.modules.shop.service.dto.StoreWithdrawQueryCriteria;

/**
 * @author billy
 * @date 2020-04-13
 */
public interface StoreWithdrawService {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String,Object>
     */
    Map<String, Object> queryAll(StoreWithdrawQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<StoreWithdrawDTO>
     */
    List<StoreWithdrawDTO> queryAll(StoreWithdrawQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return StoreWithdrawDTO
     */
    StoreWithdrawDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return StoreWithdrawDTO
     */
    StoreWithdrawDTO create(StoreWithdraw resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(StoreWithdraw resources);

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
    void download(List<StoreWithdrawDTO> all, HttpServletResponse response) throws IOException;
}
