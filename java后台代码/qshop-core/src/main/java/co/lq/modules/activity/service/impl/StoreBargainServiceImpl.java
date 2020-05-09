package co.lq.modules.activity.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.activity.domain.StoreBargain;
import co.lq.modules.activity.repository.StoreBargainRepository;
import co.lq.modules.activity.service.StoreBargainService;
import co.lq.modules.activity.service.dto.StoreBargainDTO;
import co.lq.modules.activity.service.dto.StoreBargainQueryCriteria;
import co.lq.modules.activity.service.mapper.StoreBargainMapper;
import co.lq.modules.shop.domain.StoreProduct;
import co.lq.modules.shop.repository.StoreProductRepository;
import co.lq.modules.shop.service.param.StoreProductParam;
import co.lq.utils.OrderUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-12-22
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StoreBargainServiceImpl implements StoreBargainService {

    private final StoreBargainRepository storeBargainRepository;
    private final StoreProductRepository storeProductRepository;

    private final StoreBargainMapper     storeBargainMapper;

    public StoreBargainServiceImpl(StoreBargainRepository storeBargainRepository,
                                   StoreProductRepository storeProductRepository,
                                   StoreBargainMapper storeBargainMapper) {
        this.storeBargainRepository = storeBargainRepository;
        this.storeProductRepository = storeProductRepository;
        this.storeBargainMapper = storeBargainMapper;
    }

    @Override
    public Map<String, Object> queryAll(StoreBargainQueryCriteria criteria, Pageable pageable) {
        Page<StoreBargain> page = storeBargainRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        List<StoreBargainDTO> storeSeckillDTOS = storeBargainMapper.toDto(page.getContent());
        int nowTime = OrderUtil.getSecondTimestampTwo();
        for (StoreBargainDTO storeSeckillDTO : storeSeckillDTOS) {
            if (storeSeckillDTO.getStatus() > 0) {
                if (storeSeckillDTO.getStartTime() > nowTime) {
                    storeSeckillDTO.setStatusStr("活动未开始");
                } else if (storeSeckillDTO.getStopTime() < nowTime) {
                    storeSeckillDTO.setStatusStr("活动已结束");
                } else if (storeSeckillDTO.getStopTime() > nowTime && storeSeckillDTO.getStartTime() < nowTime) {
                    storeSeckillDTO.setStatusStr("正在进行中");
                }
            } else {
                storeSeckillDTO.setStatusStr("关闭");
            }
            Optional<StoreProduct> optionalStoreProduct = storeProductRepository
                    .findById(storeSeckillDTO.getProductId());
            StoreProduct storeProduct = optionalStoreProduct.get();
            if (storeProduct != null) {
                List<StoreProductParam> storeProductParamList = new ArrayList<>();
                StoreProductParam storeProductParam = new StoreProductParam();
                storeProductParam.setId(storeProduct.getId());
                storeProductParam.setName(storeProduct.getProductName());
                storeProductParam.setPrice(storeProduct.getPrice());
                storeProductParam.setStock(storeProduct.getStock());
                storeProductParamList.add(storeProductParam);
                storeSeckillDTO.setProductList(storeProductParamList);
                storeSeckillDTO.setProductName(storeProduct.getProductName());
            }
        }
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", storeSeckillDTOS);
        map.put("totalElements", page.getTotalElements());

        return map;
    }

    @Override
    public List<StoreBargainDTO> queryAll(StoreBargainQueryCriteria criteria) {
        return storeBargainMapper.toDto(storeBargainRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public StoreBargainDTO findById(Long id) {
        Optional<StoreBargain> storeBargain = storeBargainRepository.findById(id);
        ValidationUtil.isNull(storeBargain, "StoreBargain", "id", id);
        return storeBargainMapper.toDto(storeBargain.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreBargainDTO create(StoreBargain resources) {
        resources.setIsDel(0);
        resources.setGiveIntegral(BigDecimal.ZERO);
        resources.setStatus(1);
        resources.setVerifyStatus(0);
        resources.setAddTime(OrderUtil.getSecondTimestampTwo());
        return storeBargainMapper.toDto(storeBargainRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreBargain resources) {
        Optional<StoreBargain> optionalStoreBargain = storeBargainRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalStoreBargain, "StoreBargain", "id", resources.getId());
        StoreBargain storeBargain = optionalStoreBargain.get();
        storeBargain.copy(resources);
        storeBargainRepository.save(storeBargain);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        Optional<StoreBargain> optionalStoreBargain = storeBargainRepository.findById(id);
        ValidationUtil.isNull(optionalStoreBargain, "StoreBargain", "id", id);
        StoreBargain storeBargain = optionalStoreBargain.get();
        storeBargain.setIsDel(1);
        storeBargainRepository.save(storeBargain);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onVerify(Long id, Integer status) {
        if (status == 1) {
            status = 0;
        } else {
            status = 1;
        }
        storeBargainRepository.updateOnVerify(status, id);
    }
}
