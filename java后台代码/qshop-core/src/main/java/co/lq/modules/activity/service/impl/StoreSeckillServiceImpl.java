package co.lq.modules.activity.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.activity.domain.StoreSeckill;
import co.lq.modules.activity.repository.StoreSeckillRepository;
import co.lq.modules.activity.service.StoreSeckillService;
import co.lq.modules.activity.service.dto.StoreSeckillDTO;
import co.lq.modules.activity.service.dto.StoreSeckillQueryCriteria;
import co.lq.modules.activity.service.mapper.StoreSeckillMapper;
import co.lq.modules.shop.domain.StoreProduct;
import co.lq.modules.shop.repository.StoreProductRepository;
import co.lq.modules.shop.service.param.StoreProductParam;
import co.lq.utils.FileUtil;
import co.lq.utils.OrderUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-12-14
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StoreSeckillServiceImpl implements StoreSeckillService {

    private final StoreSeckillRepository storeSeckillRepository;
    private final StoreProductRepository storeProductRepository;

    private final StoreSeckillMapper     storeSeckillMapper;

    public StoreSeckillServiceImpl(StoreSeckillRepository storeSeckillRepository,
                                   StoreProductRepository storeProductRepository,
                                   StoreSeckillMapper storeSeckillMapper) {
        this.storeSeckillRepository = storeSeckillRepository;
        this.storeProductRepository = storeProductRepository;
        this.storeSeckillMapper = storeSeckillMapper;
    }

    @Override
    public Map<String, Object> queryAll(StoreSeckillQueryCriteria criteria, Pageable pageable) {
        Page<StoreSeckill> page = storeSeckillRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        List<StoreSeckillDTO> storeSeckillDTOS = storeSeckillMapper.toDto(page.getContent());
        int nowTime = OrderUtil.getSecondTimestampTwo();
        for (StoreSeckillDTO storeSeckillDTO : storeSeckillDTOS) {
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
            }
        }
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", storeSeckillDTOS);
        map.put("totalElements", page.getTotalElements());

        return map;
    }

    @Override
    public List<StoreSeckillDTO> queryAll(StoreSeckillQueryCriteria criteria) {
        return storeSeckillMapper.toDto(storeSeckillRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public StoreSeckillDTO findById(Long id) {
        Optional<StoreSeckill> storeSeckill = storeSeckillRepository.findById(id);
        ValidationUtil.isNull(storeSeckill, "StoreSeckill", "id", id);
        return storeSeckillMapper.toDto(storeSeckill.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreSeckillDTO create(StoreSeckill resources) {
        resources.setIsDel(0);
        resources.setGiveIntegral(BigDecimal.ZERO);
        resources.setStatus(1);
        resources.setVerifyStatus(0);
        resources.setAddTime(String.valueOf(OrderUtil.getSecondTimestampTwo()));
        return storeSeckillMapper.toDto(storeSeckillRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreSeckill resources) {
        Optional<StoreSeckill> optionalStoreSeckill = storeSeckillRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalStoreSeckill, "StoreSeckill", "id", resources.getId());
        StoreSeckill storeSeckill = optionalStoreSeckill.get();
        storeSeckill.copy(resources);
        storeSeckillRepository.save(storeSeckill);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        storeSeckillRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onVerify(Long id, Integer status) {
        if (status == 1) {
            status = 0;
        } else {
            status = 1;
        }
        storeSeckillRepository.updateOnVerify(status, id);
    }

    @Override
    public void download(List<StoreSeckillDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StoreSeckillDTO storeSettle : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("所属店铺", storeSettle.getStoreId());
            map.put("秒杀名称", storeSettle.getTitle());
            map.put("成本", storeSettle.getCost());
            map.put("描述", storeSettle.getDescription());
            map.put("结束时间", storeSettle.getEndTimeDate());
            map.put("开始时间", storeSettle.getAddTime());
            map.put("积分", storeSettle.getGiveIntegral());
            map.put("头图", storeSettle.getImage());
            map.put("gallery", storeSettle.getImages());
            map.put("简介", storeSettle.getInfo());
            map.put("是否包邮", storeSettle.getIsPostage());
            map.put("是否展示", storeSettle.getIsShow());
            map.put("数量", storeSettle.getNum());
            //todo
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
