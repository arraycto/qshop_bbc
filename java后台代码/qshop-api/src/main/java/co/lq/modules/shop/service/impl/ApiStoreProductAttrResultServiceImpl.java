package co.lq.modules.shop.service.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.StoreProductAttrResult;
import co.lq.modules.shop.mapper.StoreProductAttrResultMapper;
import co.lq.modules.shop.service.ApiStoreProductAttrResultService;
import co.lq.modules.shop.web.param.StoreProductAttrResultQueryParam;
import co.lq.modules.shop.web.vo.StoreProductAttrResultQueryVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 商品属性详情表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-10-23
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ApiStoreProductAttrResultServiceImpl
        extends BaseServiceImpl<StoreProductAttrResultMapper, StoreProductAttrResult>
        implements ApiStoreProductAttrResultService {

    private final StoreProductAttrResultMapper storeProductAttrResultMapper;

    @Override
    public StoreProductAttrResultQueryVo getStoreProductAttrResultById(Serializable id) throws Exception {
        return storeProductAttrResultMapper.getStoreProductAttrResultById(id);
    }

    @Override
    public Paging<StoreProductAttrResultQueryVo> getStoreProductAttrResultPageList(StoreProductAttrResultQueryParam storeProductAttrResultQueryParam)
            throws Exception {
        Page page = setPageParam(storeProductAttrResultQueryParam, OrderItem.desc("create_time"));
        IPage<StoreProductAttrResultQueryVo> iPage = storeProductAttrResultMapper
                .getStoreProductAttrResultPageList(page, storeProductAttrResultQueryParam);
        return new Paging(iPage);
    }

}
