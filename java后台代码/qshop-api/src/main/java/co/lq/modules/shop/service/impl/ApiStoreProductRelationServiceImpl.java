package co.lq.modules.shop.service.impl;

import java.io.Serializable;
import java.util.List;

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
import co.lq.modules.shop.entity.StoreProductRelation;
import co.lq.modules.shop.mapper.StoreProductRelationMapper;
import co.lq.modules.shop.service.ApiStoreProductRelationService;
import co.lq.modules.shop.web.param.StoreProductRelationQueryParam;
import co.lq.modules.shop.web.vo.StoreProductRelationQueryVo;
import co.lq.utils.OrderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 商品点赞和收藏表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-10-23
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ApiStoreProductRelationServiceImpl extends
        BaseServiceImpl<StoreProductRelationMapper, StoreProductRelation> implements ApiStoreProductRelationService {

    private final StoreProductRelationMapper storeProductRelationMapper;

    @Override
    public StoreProductRelationQueryVo getStoreProductRelationById(Serializable id) throws Exception {
        return storeProductRelationMapper.getStoreProductRelationById(id);
    }

    @Override
    public Paging<StoreProductRelationQueryVo> getStoreProductRelationPageList(StoreProductRelationQueryParam storeProductRelationQueryParam)
            throws Exception {
        Page page = setPageParam(storeProductRelationQueryParam, OrderItem.desc("create_time"));
        IPage<StoreProductRelationQueryVo> iPage = storeProductRelationMapper.getStoreProductRelationPageList(page,
                storeProductRelationQueryParam);
        return new Paging(iPage);
    }

    @Override
    public List<StoreProductRelationQueryVo> userCollectProduct(int page, int limit, long uid) {
        Page<StoreProductRelation> pageModel = new Page<>(page, limit);
        List<StoreProductRelationQueryVo> list = baseMapper.selectList(pageModel, uid);
        return list;
    }

    @Override
    public void addRroductRelation(StoreProductRelationQueryParam param, long uid, String relationType) {
        StoreProductRelation storeProductRelation = new StoreProductRelation();
        if (isProductRelation(param.getId(), param.getCategory(), uid, relationType)) {
            throw new ErrorRequestException("已收藏");
        }

        storeProductRelation.setCategory(param.getCategory());
        storeProductRelation.setProductId(param.getId());
        storeProductRelation.setType(relationType);
        storeProductRelation.setUid(uid);
        storeProductRelation.setAddTime(OrderUtil.getSecondTimestampTwo());

        storeProductRelationMapper.insert(storeProductRelation);
    }

    @Override
    public void delRroductRelation(StoreProductRelationQueryParam param, long uid, String relationType) {
        QueryWrapper<StoreProductRelation> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid).eq("product_id", param.getId()).eq("type", relationType).eq("category",
                param.getCategory());
        StoreProductRelation productRelation = storeProductRelationMapper.selectOne(wrapper);
        if (ObjectUtil.isNull(productRelation)) {
            throw new ErrorRequestException("已取消");
        }

        storeProductRelationMapper.deleteById(productRelation.getId());
    }

    @Override
    public Boolean isProductRelation(long productId, String category, long uid, String relationType) {
        QueryWrapper<StoreProductRelation> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid).eq("product_id", productId).eq("type", relationType).eq("category", category);
        StoreProductRelation productRelation = storeProductRelationMapper.selectOne(wrapper);
        if (ObjectUtil.isNotNull(productRelation)) {
            return true;
        }

        return false;
    }
}
