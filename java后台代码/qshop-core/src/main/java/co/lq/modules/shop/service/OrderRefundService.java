package co.lq.modules.shop.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;

import co.lq.modules.shop.domain.OrderRefund;
import co.lq.modules.shop.service.dto.OrderRefundDTO;
import co.lq.modules.shop.service.dto.OrderRefundQueryCriteria;

/**
 * @author billy
 * @date 2020-03-29
 */
public interface OrderRefundService {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String,Object>
     */
    Map<String, Object> queryAll(OrderRefundQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<OrderRefundDTO>
     */
    List<OrderRefundDTO> queryAll(OrderRefundQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return OrderRefundDTO
     */
    OrderRefundDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return OrderRefundDTO
     */
    OrderRefundDTO create(OrderRefund resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(OrderRefund resources);

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
    void download(List<OrderRefundDTO> all, HttpServletResponse response) throws IOException;

    /**
     * 退款
     *
     * @param id
     */
    Boolean refund(Long id);
}
