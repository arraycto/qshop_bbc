package co.lq.modules.shop.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.shop.domain.Shop;
import co.lq.modules.shop.domain.StoreSettle;
import co.lq.modules.shop.repository.StoreRepository;
import co.lq.modules.shop.repository.StoreSettleRepository;
import co.lq.modules.shop.service.ShopService;
import co.lq.modules.shop.service.dto.ShopDTO;
import co.lq.modules.shop.service.dto.ShopQueryCriteria;
import co.lq.modules.shop.service.mapper.StoreMapper;
import co.lq.utils.FileUtil;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

/**
 * @author billy
 * @date 2020-03-10
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ShopServiceImpl implements ShopService {

    private final StoreRepository       storeRepository;

    private final StoreMapper           storeMapper;
    private final StoreSettleRepository storeSettleRepository;

    public ShopServiceImpl(StoreRepository storeRepository, StoreMapper storeMapper,
                           StoreSettleRepository storeSettleRepository) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
        this.storeSettleRepository = storeSettleRepository;
    }

    @Override
    public Map<String, Object> queryAll(ShopQueryCriteria criteria, Pageable pageable) {
        Page<Shop> page = storeRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(shop -> {
            ShopDTO shopDTO = storeMapper.toDto(shop);
            StoreSettle storeSettle = storeSettleRepository.findByStoreIdAndDeleted(shop.getId(), 0);
            if (storeSettle != null) {
                if (storeSettle.getStatus() == 1) {
                    shopDTO.setShopStatus("通过");
                } else if (storeSettle.getStatus() == 2) {
                    shopDTO.setShopStatus("审核中");
                } else {
                    shopDTO.setShopStatus("拒绝");
                }

                if (storeSettle.getClosed() == 0) {
                    shopDTO.setClosedStatus("关闭");
                } else {
                    shopDTO.setClosedStatus("正常");
                }
            }
            return shopDTO;
        }));
    }

    @Override
    public List<ShopDTO> queryAll(ShopQueryCriteria criteria) {
        return storeMapper.toDto(storeRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public Shop findById(Long id) {
        Shop shop = storeRepository.findById(id).orElseGet(Shop::new);
        ValidationUtil.isNull(shop.getId(), "Shop", "id", id);
        return shop;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ShopDTO create(Shop resources) {
        Shop shop = new Shop();
        if (resources.getId() != null) {
            shop = storeRepository.findById(resources.getId()).orElseGet(Shop::new);
            ValidationUtil.isNull(shop.getId(), "Shop", "id", resources.getId());
            shop.copy(resources);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            shop.setModifyTime(timestamp);
        } else {
            if (resources.getIsBoutique() == null) {
                resources.setIsBoutique(0);
            }
            resources.setDeleted(0);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            resources.setAddTime(timestamp);
            resources.setModifyTime(timestamp);
            shop.copy(resources);
        }

        return storeMapper.toDto(storeRepository.save(shop));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Shop resources) {
        Shop shop = storeRepository.findById(resources.getId()).orElseGet(Shop::new);
        ValidationUtil.isNull(shop.getId(), "Shop", "id", resources.getId());
        shop.copy(resources);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        shop.setModifyTime(timestamp);
        storeRepository.save(shop);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            Shop shop = storeRepository.findById(id).orElseGet(Shop::new);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            shop.setModifyTime(timestamp);
            storeRepository.save(shop);
        }
    }

    @Override
    public void download(List<ShopDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ShopDTO qshopStore : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("注册类型", qshopStore.getRegisterType());
            map.put("到期时间", qshopStore.getExpireTime());
            map.put("尝试时间", qshopStore.getTryTime());
            map.put("联系电话", qshopStore.getContactMobile());
            map.put("是否选中", qshopStore.getIsChecked());
            map.put("服务电话", qshopStore.getServicePhone());
            map.put("地址名", qshopStore.getAddressLat());
            map.put("联系人名", qshopStore.getContactName());
            map.put("删除时间", qshopStore.getDeleteTime());
            map.put(" isStar", qshopStore.getIsStar());
            map.put("尝试", qshopStore.getIsTry());
            map.put("图标", qshopStore.getLogo());
            map.put("地址细节", qshopStore.getAddressDetail());
            map.put(" name", qshopStore.getName());
            map.put(" uid", qshopStore.getUid());
            map.put("联系QQ", qshopStore.getContactQq());
            map.put(" addressLng", qshopStore.getAddressLng());
            map.put(" lastLoginTime", qshopStore.getLastLoginTime());
            map.put("支持电话", qshopStore.getSupportPhone());
            map.put(" contactQrcode", qshopStore.getContactQrcode());
            map.put("描述", qshopStore.getDescription());
            map.put(" collect", qshopStore.getCollect());
            map.put(" hit", qshopStore.getHit());
            map.put(" goodsCount", qshopStore.getGoodsCount());
            map.put(" memberCount", qshopStore.getMemberCount());
            map.put(" orderCount", qshopStore.getOrderCount());
            map.put(" payAmount", qshopStore.getPayAmount());
            map.put(" articleCount", qshopStore.getArticleCount());
            map.put("精品店铺标识,0:否，1:是", qshopStore.getIsBoutique());
            map.put("添加时间", qshopStore.getAddTime());
            map.put("更新时间", qshopStore.getModifyTime());
            map.put("逻辑删除", qshopStore.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
