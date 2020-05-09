package co.lq.modules.shop.service;

import co.lq.modules.shop.domain.OrderReturnReason;
import co.lq.modules.shop.service.dto.OrderReturnReasonDTO;
import co.lq.modules.shop.service.dto.OrderReturnReasonQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author billy
* @date 2020-03-27
*/
public interface OrderReturnReasonService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(OrderReturnReasonQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<OrderReturnReasonDTO>
    */
    List<OrderReturnReasonDTO> queryAll(OrderReturnReasonQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return OrderReturnReasonDTO
     */
    OrderReturnReasonDTO findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return OrderReturnReasonDTO
    */
    OrderReturnReasonDTO create(OrderReturnReason resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(OrderReturnReason resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<OrderReturnReasonDTO> all, HttpServletResponse response) throws IOException;
}