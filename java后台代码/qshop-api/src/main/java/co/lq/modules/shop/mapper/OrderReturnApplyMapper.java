package co.lq.modules.shop.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import co.lq.modules.shop.entity.OrderReturnApply;
import co.lq.modules.shop.web.param.OrderReturnApplyQueryParam;
import co.lq.modules.shop.web.vo.OrderReturnApplyQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * <p>
 * 订单退货申请 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2020-04-04
 */
@Repository
public interface OrderReturnApplyMapper extends BaseMapper<OrderReturnApply> {

    /**
     * 根据ID获取查询对象
     * @param id
     * @return
     */
    OrderReturnApplyQueryVo getOrderReturnApplyById(Serializable id);

    /**
     * 获取分页对象
     * @param page
     * @param orderReturnApplyQueryParam
     * @return
     */
    IPage<OrderReturnApplyQueryVo> getOrderReturnApplyPageList(@Param("page") Page page, @Param("param") OrderReturnApplyQueryParam orderReturnApplyQueryParam);

}
