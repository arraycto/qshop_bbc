package co.lq.modules.shop.service.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.modules.shop.entity.StoreSettle;
import co.lq.modules.shop.mapper.StoreSettleMapper;
import co.lq.modules.shop.mapping.StoreSettleMap;
import co.lq.modules.shop.service.ApiStoreSettleService;
import co.lq.modules.shop.web.param.StoreSettleQueryParam;
import co.lq.modules.shop.web.vo.StoreSettleQueryVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 店铺入驻服务实现
 *
 * @author songbin
 * @since 2020年3月31日 下午2:17:25
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ApiStoreSettleServiceImpl extends BaseServiceImpl<StoreSettleMapper, StoreSettle>
        implements ApiStoreSettleService {

    public final StoreSettleMapper storeSettleMapper;

    public final StoreSettleMap    storeSettleMap;

    @Override
    public StoreSettleQueryVo getStoreById(Serializable id) {
        return storeSettleMapper.getStoreSettleById(id);
    }

    @Override
    public List<StoreSettleQueryVo> getList() {
        QueryWrapper<StoreSettle> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0).orderByDesc("add_time");
        List<StoreSettleQueryVo> list = storeSettleMap.toDto(baseMapper.selectList(wrapper));
        return list;
    }

    @Override
    public StoreSettleQueryVo getShopSetleByStoreId(StoreSettleQueryParam storeSettleQueryParam) {
        return storeSettleMapper.getShopInfoByStoreId(storeSettleQueryParam);
    }

    @Override
    public Boolean saveShopSettle(StoreSettle storeSettle) {
        storeSettle.setStatus(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (storeSettle.getId() == null || storeSettle.getId().equals(0L)) {
            storeSettle.setAddTime(timestamp);
            storeSettle.setModifyTime(timestamp);
            storeSettleMapper.insert(storeSettle);
        } else {
            storeSettle.setModifyTime(timestamp);
            storeSettleMapper.updateById(storeSettle);
        }

        return true;
    }
}
