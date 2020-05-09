package co.lq.modules.activity.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.util.ObjectUtil;
import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.exception.ErrorRequestException;
import co.lq.modules.activity.entity.StoreCombination;
import co.lq.modules.activity.mapper.StoreCombinationMapper;
import co.lq.modules.activity.service.ApiStoreCombinationService;
import co.lq.modules.activity.service.ApiStorePinkService;
import co.lq.modules.activity.web.dto.PinkDTO;
import co.lq.modules.activity.web.dto.StoreCombinationDTO;
import co.lq.modules.activity.web.param.StoreCombinationQueryParam;
import co.lq.modules.activity.web.vo.StoreCombinationQueryVo;
import co.lq.modules.shop.service.ApiStoreProductReplyService;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 拼团产品表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-11-19
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiStoreCombinationServiceImpl extends BaseServiceImpl<StoreCombinationMapper, StoreCombination>
        implements ApiStoreCombinationService {

    @Autowired
    private StoreCombinationMapper      storeCombinationMapper;

    @Autowired
    private ApiStoreProductReplyService replyService;
    @Autowired
    private ApiStorePinkService         apiStorePinkService;

    /**
     * 减库存增加销量
     *
     * @param num
     * @param combinationId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decStockIncSales(int num, long combinationId) {
        storeCombinationMapper.decStockIncSales(num, combinationId);
    }

    /**
     * 增加库存 减少销量
     *
     * @param num
     * @param combinationId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incStockDecSales(int num, long combinationId) {
        storeCombinationMapper.incStockDecSales(num, combinationId);
    }

    @Override
    public StoreCombination getCombination(long id) {
        QueryWrapper<StoreCombination> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id).eq("is_del", 0).eq("is_show", 1);
        return storeCombinationMapper.selectOne(wrapper);
    }

    /**
     * 判断库存是否足够
     *
     * @param combinationId
     * @param cartNum
     * @return
     */
    @Override
    public boolean judgeCombinationStock(long combinationId, int cartNum) {
        StoreCombinationQueryVo queryVo = getStoreCombinationById(combinationId);
        if (queryVo.getStock() > cartNum) {
            return true;
        }
        return false;
    }

    @Override
    public StoreCombinationQueryVo getCombinationT(long id) {
        return storeCombinationMapper.getCombDetail(id);
    }

    @Override
    public StoreCombinationDTO getDetail(long id, long uid) {
        StoreCombinationQueryVo storeCombinationQueryVo = storeCombinationMapper.getCombDetail(id);
        if (ObjectUtil.isNull(storeCombinationQueryVo)) {
            throw new ErrorRequestException("拼团不存在或已下架");
        }

        StoreCombinationDTO storeCombinationDTO = new StoreCombinationDTO();

        storeCombinationDTO.setStoreInfo(storeCombinationQueryVo);

        storeCombinationDTO.setReply(replyService.getReply(storeCombinationQueryVo.getProductId()));
        int replyCount = replyService.productReplyCount(storeCombinationQueryVo.getProductId());
        storeCombinationDTO.setReplyCount(replyCount);
        storeCombinationDTO.setReplyChance(replyService.doReply(storeCombinationQueryVo.getProductId(), replyCount));
        Map<String, Object> map = apiStorePinkService.getPinkAll(id, true);
        storeCombinationDTO.setPindAll((List<Long>) map.get("pindAll"));
        storeCombinationDTO.setPink((List<PinkDTO>) map.get("list"));
        storeCombinationDTO.setPinkOkList(apiStorePinkService.getPinkOkList(uid));
        storeCombinationDTO.setPinkOkSum(apiStorePinkService.getPinkOkSumTotalNum());

        return storeCombinationDTO;
    }

    /**
     * 拼团列表
     *
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<StoreCombinationQueryVo> getList(int page, int limit) {
        Page<StoreCombination> pageModel = new Page<>(page, limit);
        return storeCombinationMapper.getCombList(pageModel);
    }

    @Override
    public StoreCombinationQueryVo getStoreCombinationById(Serializable id) {
        return storeCombinationMapper.getStoreCombinationById(id);
    }

    @Override
    public Paging<StoreCombinationQueryVo> getStoreCombinationPageList(StoreCombinationQueryParam storeCombinationQueryParam)
            throws Exception {
        Page page = setPageParam(storeCombinationQueryParam, OrderItem.desc("create_time"));
        IPage<StoreCombinationQueryVo> iPage = storeCombinationMapper.getStoreCombinationPageList(page,
                storeCombinationQueryParam);
        return new Paging(iPage);
    }

}
