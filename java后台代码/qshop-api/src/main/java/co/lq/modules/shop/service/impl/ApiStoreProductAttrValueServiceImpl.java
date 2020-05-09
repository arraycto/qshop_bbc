package co.lq.modules.shop.service.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.StoreProductAttrValue;
import co.lq.modules.shop.mapper.StoreProductAttrValueMapper;
import co.lq.modules.shop.service.ApiStoreProductAttrValueService;
import co.lq.modules.shop.web.param.StoreProductAttrValueQueryParam;
import co.lq.modules.shop.web.vo.StoreProductAttrValueQueryVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 商品属性值表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-10-23
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ApiStoreProductAttrValueServiceImpl extends
        BaseServiceImpl<StoreProductAttrValueMapper, StoreProductAttrValue> implements ApiStoreProductAttrValueService {

    private final StoreProductAttrValueMapper storeProductAttrValueMapper;

    @Override
    public StoreProductAttrValueQueryVo getStoreProductAttrValueById(Serializable id) throws Exception {
        return storeProductAttrValueMapper.getStoreProductAttrValueById(id);
    }

    @Override
    public Paging<StoreProductAttrValueQueryVo> getStoreProductAttrValuePageList(StoreProductAttrValueQueryParam storeProductAttrValueQueryParam)
            throws Exception {
        Page page = setPageParam(storeProductAttrValueQueryParam, OrderItem.desc("create_time"));
        IPage<StoreProductAttrValueQueryVo> iPage = storeProductAttrValueMapper.getStoreProductAttrValuePageList(page,
                storeProductAttrValueQueryParam);
        return new Paging(iPage);
    }

}
