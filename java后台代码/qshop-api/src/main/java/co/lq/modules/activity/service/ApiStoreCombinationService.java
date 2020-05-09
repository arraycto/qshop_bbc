package co.lq.modules.activity.service;

import java.io.Serializable;
import java.util.List;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.activity.entity.StoreCombination;
import co.lq.modules.activity.web.dto.StoreCombinationDTO;
import co.lq.modules.activity.web.param.StoreCombinationQueryParam;
import co.lq.modules.activity.web.vo.StoreCombinationQueryVo;

/**
 * <p>
 * 拼团产品表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-11-19
 */
public interface ApiStoreCombinationService extends BaseService<StoreCombination> {

    StoreCombinationQueryVo getCombinationT(long id);

    void decStockIncSales(int num, long combinationId);

    void incStockDecSales(int num, long combinationId);

    StoreCombination getCombination(long id);

    boolean judgeCombinationStock(long combinationId, int cartNum);

    List<StoreCombinationQueryVo> getList(int page, int limit);

    StoreCombinationDTO getDetail(long id, long uid);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreCombinationQueryVo getStoreCombinationById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param storeCombinationQueryParam
     * @return
     */
    Paging<StoreCombinationQueryVo> getStoreCombinationPageList(StoreCombinationQueryParam storeCombinationQueryParam)
            throws Exception;

}
