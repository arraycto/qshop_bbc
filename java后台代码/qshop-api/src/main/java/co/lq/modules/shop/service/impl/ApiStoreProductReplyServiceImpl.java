package co.lq.modules.shop.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.StoreProductReply;
import co.lq.modules.shop.mapper.StoreProductReplyMapper;
import co.lq.modules.shop.service.ApiStoreProductReplyService;
import co.lq.modules.shop.web.dto.ReplyCountDTO;
import co.lq.modules.shop.web.param.StoreProductReplyQueryParam;
import co.lq.modules.shop.web.vo.StoreCartQueryVo;
import co.lq.modules.shop.web.vo.StoreProductReplyQueryVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-10-23
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ApiStoreProductReplyServiceImpl extends BaseServiceImpl<StoreProductReplyMapper, StoreProductReply>
        implements ApiStoreProductReplyService {

    private final StoreProductReplyMapper storeProductReplyMapper;

    /**
     * 评价数据
     *
     * @param productId
     * @return
     */
    @Override
    public ReplyCountDTO getReplyCount(long productId) {
        ReplyCountDTO replyCountDTO = new ReplyCountDTO();

        QueryWrapper<StoreProductReply> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id", productId).eq("is_del", 0).eq("reply_type", "product");
        int sumCount = storeProductReplyMapper.selectCount(wrapper);
        replyCountDTO.setSumCount(sumCount);

        //好评
        QueryWrapper<StoreProductReply> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("product_id", productId).eq("is_del", 0).eq("reply_type", "product").eq("product_score", 5);
        int goodCount = storeProductReplyMapper.selectCount(wrapperOne);
        replyCountDTO.setGoodCount(goodCount);

        //中评
        QueryWrapper<StoreProductReply> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.eq("product_id", productId).eq("is_del", 0).eq("reply_type", "product").lt("product_score", 5).gt(
                "product_score", 2);
        replyCountDTO.setInCount(storeProductReplyMapper.selectCount(wrapperTwo));

        //差评
        QueryWrapper<StoreProductReply> wrapperThree = new QueryWrapper<>();
        wrapperThree.eq("product_id", productId).eq("is_del", 0).eq("reply_type", "product").lt("product_score", 2);
        replyCountDTO.setPoorCount(storeProductReplyMapper.selectCount(wrapperThree));

        //好评率

        replyCountDTO.setReplySstar("" + NumberUtil.round(NumberUtil.mul(NumberUtil.div(goodCount, sumCount), 5), 2));
        replyCountDTO
                .setReplyChance("" + NumberUtil.round(NumberUtil.mul(NumberUtil.div(goodCount, sumCount), 100), 2));

        return replyCountDTO;
    }

    /**
     * 处理评价
     *
     * @param replyQueryVo
     * @return
     */
    @Override
    public StoreProductReplyQueryVo handleReply(StoreProductReplyQueryVo replyQueryVo) {
        StoreCartQueryVo cartInfo = JSONObject.parseObject(replyQueryVo.getCartInfo(), StoreCartQueryVo.class);
        if (ObjectUtil.isNotNull(cartInfo)) {
            if (ObjectUtil.isNotNull(cartInfo.getProductInfo())) {
                if (ObjectUtil.isNotNull(cartInfo.getProductInfo().getAttrInfo())) {
                    replyQueryVo.setSuk(cartInfo.getProductInfo().getAttrInfo().getSuk());
                }
            }
        }

        BigDecimal star = NumberUtil.add(replyQueryVo.getProductScore(), replyQueryVo.getServiceScore());

        star = NumberUtil.div(star, 2);

        replyQueryVo.setStar(String.valueOf(star.intValue()));

        if (StrUtil.isEmpty(replyQueryVo.getComment())) {
            replyQueryVo.setComment("此用户没有填写评价");
        }

        return replyQueryVo;
    }

    /**
     * 获取单条评价
     *
     * @param productId
     * @return
     */
    @Override
    public StoreProductReplyQueryVo getReply(long productId) {
        StoreProductReplyQueryVo vo = storeProductReplyMapper.getReply(productId);
        if (ObjectUtil.isNotNull(vo)) {
            return handleReply(storeProductReplyMapper.getReply(productId));
        }
        return null;
    }

    /**
     * 获取评价列表
     *
     * @param productId
     * @param type
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<StoreProductReplyQueryVo> getReplyList(long productId, int type, int page, int limit) {
        List<StoreProductReplyQueryVo> newList = new ArrayList<>();
        Page<StoreProductReply> pageModel = new Page<>(page, limit);
        List<StoreProductReplyQueryVo> list = storeProductReplyMapper.selectReplyList(pageModel, productId, type);
        List<StoreProductReplyQueryVo> list1 = list.stream().map(i -> {
            StoreProductReplyQueryVo vo = new StoreProductReplyQueryVo();
            BeanUtils.copyProperties(i, vo);
            if (i.getPictures().contains(",")) {
                vo.setPics(i.getPictures().split(","));
            }
            return vo;
        }).collect(Collectors.toList());
        for (StoreProductReplyQueryVo queryVo : list1) {
            newList.add(handleReply(queryVo));
        }
        return newList;
    }

    @Override
    public int getInfoCount(Long oid, String unique) {
        QueryWrapper<StoreProductReply> wrapper = new QueryWrapper<>();
        wrapper.eq("`unique`", unique).eq("oid", oid);
        return storeProductReplyMapper.selectCount(wrapper);
    }

    @Override
    public int productReplyCount(long productId) {
        QueryWrapper<StoreProductReply> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id", productId).eq("is_del", 0).eq("reply_type", "product");
        return storeProductReplyMapper.selectCount(wrapper);
    }

    @Override
    public int replyCount(String unique) {
        QueryWrapper<StoreProductReply> wrapper = new QueryWrapper<>();
        wrapper.eq("`unique`", unique);
        return storeProductReplyMapper.selectCount(wrapper);
    }

    /**
     * 处理比例
     *
     * @param productId
     * @param count
     * @return
     */
    @Override
    public String doReply(long productId, int count) {
        QueryWrapper<StoreProductReply> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id", productId).eq("is_del", 0).eq("reply_type", "product").eq("product_score", 5);
        int productScoreCount = storeProductReplyMapper.selectCount(wrapper);
        if (count > 0) {
            return "" + NumberUtil.round(NumberUtil.mul(NumberUtil.div(productScoreCount, count), 100), 2);
        }

        return "0";
    }

    @Override
    public StoreProductReplyQueryVo getStoreProductReplyById(Serializable id) throws Exception {
        return storeProductReplyMapper.getStoreProductReplyById(id);
    }

    @Override
    public Paging<StoreProductReplyQueryVo> getStoreProductReplyPageList(StoreProductReplyQueryParam storeProductReplyQueryParam)
            throws Exception {
        Page page = setPageParam(storeProductReplyQueryParam, OrderItem.desc("create_time"));
        IPage<StoreProductReplyQueryVo> iPage = storeProductReplyMapper.getStoreProductReplyPageList(page,
                storeProductReplyQueryParam);
        return new Paging(iPage);
    }

}
