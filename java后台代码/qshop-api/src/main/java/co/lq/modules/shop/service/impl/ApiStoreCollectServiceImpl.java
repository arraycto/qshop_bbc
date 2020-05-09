package co.lq.modules.shop.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.Store;
import co.lq.modules.shop.entity.StoreCollect;
import co.lq.modules.shop.mapper.StoreCollectMapper;
import co.lq.modules.shop.mapper.StoreMapper;
import co.lq.modules.shop.service.ApiStoreCollectService;
import co.lq.modules.shop.web.param.StoreCollectQueryParam;
import co.lq.modules.shop.web.vo.StoreCollectQueryVo;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 店铺收藏表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2020-04-05
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiStoreCollectServiceImpl extends BaseServiceImpl<StoreCollectMapper, StoreCollect>
        implements ApiStoreCollectService {

    @Autowired
    private StoreCollectMapper storeCollectMapper;

    @Autowired
    private StoreMapper        storeMapper;

    @Override
    public StoreCollectQueryVo getStoreCollectById(Serializable id) throws Exception {
        return storeCollectMapper.getStoreCollectById(id);
    }

    @Override
    public Paging<StoreCollectQueryVo> getStoreCollectPageList(StoreCollectQueryParam storeCollectQueryParam)
            throws Exception {
        storeCollectQueryParam.setDeleted(0);
        Page page = setPageParam(storeCollectQueryParam, OrderItem.desc("add_time"));
        IPage<StoreCollectQueryVo> iPage = storeCollectMapper.getStoreCollectPageList(page, storeCollectQueryParam);
        iPage.getRecords().forEach(storeCollectQueryVo -> {
            Store store = storeMapper.selectById(storeCollectQueryVo.getStoreId());
            storeCollectQueryVo.setLogo(store.getLogo());
            storeCollectQueryVo.setShopName(store.getName());
            storeCollectQueryVo.setContactMobile(store.getContactMobile());
        });
        return new Paging(iPage);
    }

    @Override
    public StoreCollectQueryVo getStoreCollectByUidAndStoreIdAndType(Long uid, Long storeId, String type) {
        return storeCollectMapper.getStoreCollectByUidAndStoreIdAndType(uid, storeId, type);
    }

}
