package co.lq.modules.shop.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.SystemStore;
import co.lq.modules.shop.mapper.SystemStoreMapper;
import co.lq.modules.shop.mapping.SystemStoreMap;
import co.lq.modules.shop.service.ApiSystemStoreService;
import co.lq.modules.shop.web.param.SystemStoreQueryParam;
import co.lq.modules.shop.web.vo.SystemStoreQueryVo;
import co.lq.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 门店自提 服务实现类
 * </p>
 *
 * @author billy
 * @since 2020-03-04
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiSystemStoreServiceImpl extends BaseServiceImpl<SystemStoreMapper, SystemStore>
        implements ApiSystemStoreService {

    @Autowired
    private SystemStoreMapper systemStoreMapper;

    @Autowired
    private SystemStoreMap    storeMap;

    @Override
    public SystemStoreQueryVo getStoreInfo(Long storeId) {
        SystemStore systemStore = new SystemStore();
        systemStore.setIsDel(0);
        systemStore.setStoreId(storeId);
        systemStore = systemStoreMapper.selectOne(Wrappers.query(systemStore));
        if (systemStore == null) {
            return null;
        }
        String mention = RedisUtil.get("store_self_mention");
        if (mention == null || Integer.valueOf(mention) == 2) {
            return null;
        }
        return storeMap.toDto(systemStore);
    }

    @Override
    public SystemStoreQueryVo getSystemStoreById(Serializable id) throws Exception {
        return systemStoreMapper.getSystemStoreById(id);
    }

    @Override
    public Paging<SystemStoreQueryVo> getSystemStorePageList(SystemStoreQueryParam systemStoreQueryParam)
            throws Exception {
        Page page = setPageParam(systemStoreQueryParam, OrderItem.desc("create_time"));
        IPage<SystemStoreQueryVo> iPage = systemStoreMapper.getSystemStorePageList(page, systemStoreQueryParam);
        return new Paging(iPage);
    }

}
