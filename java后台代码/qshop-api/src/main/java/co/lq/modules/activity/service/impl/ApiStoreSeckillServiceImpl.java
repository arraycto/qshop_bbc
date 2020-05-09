package co.lq.modules.activity.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.exception.ErrorRequestException;
import co.lq.modules.activity.entity.StoreSeckill;
import co.lq.modules.activity.mapper.StoreSeckillMapper;
import co.lq.modules.activity.mapping.StoreSeckillMap;
import co.lq.modules.activity.service.ApiStoreSeckillService;
import co.lq.modules.activity.web.dto.StoreSeckillDTO;
import co.lq.modules.activity.web.param.StoreSeckillQueryParam;
import co.lq.modules.activity.web.vo.StoreSeckillQueryVo;
import co.lq.modules.shop.service.ApiStoreProductReplyService;
import co.lq.utils.OrderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 商品秒杀产品表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-12-14
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ApiStoreSeckillServiceImpl extends BaseServiceImpl<StoreSeckillMapper, StoreSeckill>
        implements ApiStoreSeckillService {

    private final StoreSeckillMapper          storeSeckillMapper;
    private final StoreSeckillMap             storeSeckillMap;

    private final ApiStoreProductReplyService replyService;

    /**
     * 退回库存减少销量
     *
     * @param num
     * @param seckillId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incStockDecSales(int num, long seckillId) {
        storeSeckillMapper.incStockDecSales(num, seckillId);
    }

    /**
     * 减库存增加销量
     *
     * @param num
     * @param seckillId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decStockIncSales(int num, long seckillId) {
        storeSeckillMapper.decStockIncSales(num, seckillId);
    }

    @Override
    public StoreSeckill getSeckill(long id) {
        QueryWrapper<StoreSeckill> wrapper = new QueryWrapper<>();
        int nowTime = OrderUtil.getSecondTimestampTwo();
        wrapper.eq("id", id).eq("is_del", 0).eq("status", 1).le("start_time", nowTime).ge("stop_time", nowTime);
        return storeSeckillMapper.selectOne(wrapper);
    }

    /**
     * 产品详情
     *
     * @param id
     * @return
     */
    @Override
    public StoreSeckillDTO getDetail(long id) throws Exception {
        StoreSeckillQueryVo storeSeckillQueryVo = getStoreSeckillById(id);

        if (ObjectUtil.isNull(storeSeckillQueryVo)) {
            throw new ErrorRequestException("秒杀产品不存在或已下架");
        }

        StoreSeckillDTO storeSeckillDTO = StoreSeckillDTO.builder()
                .storeInfo(storeSeckillQueryVo)
                .reply(replyService.getReply(storeSeckillQueryVo.getProductId()))
                .replyCount(replyService.productReplyCount(storeSeckillQueryVo.getProductId()))
                .build();

        return storeSeckillDTO;
    }

    /**
     * 秒杀产品列表
     *
     * @param page
     * @param limit
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<StoreSeckillQueryVo> getList(int page, int limit, int startTime, int endTime) {
        Page<StoreSeckill> pageModel = new Page<>(page, limit);
        QueryWrapper<StoreSeckill> wrapper = new QueryWrapper<>();
        wrapper.eq("is_del", 0)
                .eq("is_show", 1)
                .ge("stock", 0)
                .le("start_time", startTime)
                .ge("stop_time", endTime)
                .orderByDesc("sort");
        List<StoreSeckillQueryVo> storeSeckillQueryVos = storeSeckillMap
                .toDto(storeSeckillMapper.selectPage(pageModel, wrapper).getRecords());
        storeSeckillQueryVos.forEach(item -> {
            Integer sum = item.getSales() + item.getStock();
            item.setPercent(NumberUtil.round(NumberUtil.mul(NumberUtil.div(item.getSales(), sum), 100), 0).intValue());
        });
        return storeSeckillQueryVos;
    }

    @Override
    public StoreSeckillQueryVo getStoreSeckillById(Serializable id) throws Exception {

        return storeSeckillMapper.getStoreSeckillById(id);
    }

    @Override
    public Paging<StoreSeckillQueryVo> getStoreSeckillPageList(StoreSeckillQueryParam storeSeckillQueryParam)
            throws Exception {
        Page page = setPageParam(storeSeckillQueryParam, OrderItem.desc("create_time"));
        IPage<StoreSeckillQueryVo> iPage = storeSeckillMapper.getStoreSeckillPageList(page, storeSeckillQueryParam);
        return new Paging(iPage);
    }

}
