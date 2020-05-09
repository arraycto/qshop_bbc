package co.lq.modules.activity.service;

import java.io.Serializable;
import java.util.List;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.activity.entity.StoreSeckill;
import co.lq.modules.activity.web.dto.StoreSeckillDTO;
import co.lq.modules.activity.web.param.StoreSeckillQueryParam;
import co.lq.modules.activity.web.vo.StoreSeckillQueryVo;

/**
 * <p>
 * 商品秒杀产品表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-12-14
 */
public interface ApiStoreSeckillService extends BaseService<StoreSeckill> {

    void incStockDecSales(int num, long seckillId);

    void decStockIncSales(int num, long seckillId);

    StoreSeckill getSeckill(long id);

    StoreSeckillDTO getDetail(long id) throws Exception;

    /**
     * 分页获取产品详情
     *
     * @param page
     * @param limit
     * @return
     */
    List<StoreSeckillQueryVo> getList(int page, int limit, int startTime, int endTime);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreSeckillQueryVo getStoreSeckillById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param storeSeckillQueryParam
     * @return
     */
    Paging<StoreSeckillQueryVo> getStoreSeckillPageList(StoreSeckillQueryParam storeSeckillQueryParam) throws Exception;

}
