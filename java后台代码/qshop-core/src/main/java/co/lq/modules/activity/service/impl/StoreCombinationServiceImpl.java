package co.lq.modules.activity.service.impl;

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

import co.lq.modules.activity.domain.StoreCombination;
import co.lq.modules.activity.repository.StoreCombinationRepository;
import co.lq.modules.activity.repository.StorePinkRepository;
import co.lq.modules.activity.repository.StoreVisitRepository;
import co.lq.modules.activity.service.StoreCombinationService;
import co.lq.modules.activity.service.dto.StoreCombinationDTO;
import co.lq.modules.activity.service.dto.StoreCombinationQueryCriteria;
import co.lq.modules.activity.service.mapper.StoreCombinationMapper;
import co.lq.modules.shop.domain.StoreProduct;
import co.lq.modules.shop.repository.StoreProductRepository;
import co.lq.modules.shop.service.param.StoreProductParam;
import co.lq.utils.OrderUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-11-18
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StoreCombinationServiceImpl implements StoreCombinationService {

    private final StoreCombinationRepository storeCombinationRepository;
    private final StoreProductRepository     storeProductRepository;
    private final StorePinkRepository        storePinkRepository;
    private final StoreVisitRepository       storeVisitRepository;

    private final StoreCombinationMapper     storeCombinationMapper;

    public StoreCombinationServiceImpl(StoreCombinationRepository storeCombinationRepository,
                                       StoreProductRepository storeProductRepository,
                                       StorePinkRepository storePinkRepository,
                                       StoreVisitRepository storeVisitRepository,
                                       StoreCombinationMapper storeCombinationMapper) {
        this.storeCombinationRepository = storeCombinationRepository;
        this.storeProductRepository = storeProductRepository;
        this.storePinkRepository = storePinkRepository;
        this.storeVisitRepository = storeVisitRepository;
        this.storeCombinationMapper = storeCombinationMapper;
    }

    @Override
    public Map<String, Object> queryAll(StoreCombinationQueryCriteria criteria, Pageable pageable) {
        criteria.setIsDel(0);
        Page<StoreCombination> page = storeCombinationRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        List<StoreCombinationDTO> combinationDTOS = storeCombinationMapper.toDto(page.getContent());
        int nowTime = OrderUtil.getSecondTimestampTwo();
        for (StoreCombinationDTO combinationDTO : combinationDTOS) {
            //参与人数
            combinationDTO.setCountPeopleAll(storePinkRepository.countByCid(combinationDTO.getId()));

            //成团人数
            combinationDTO.setCountPeoplePink(storePinkRepository.countByCidAndKId(combinationDTO.getId(), 0));
            //获取查看拼团产品人数
            combinationDTO.setCountPeopleBrowse(
                    storeVisitRepository.countByProductIdAndProductType(combinationDTO.getId(), "combination"));

            if (combinationDTO.getStatus() > 0) {
                if (combinationDTO.getStartTime() > nowTime) {
                    combinationDTO.setStatusStr("活动未开始");
                } else if (combinationDTO.getStopTime() < nowTime) {
                    combinationDTO.setStatusStr("活动已结束");
                } else if (combinationDTO.getStopTime() > nowTime && combinationDTO.getStartTime() < nowTime) {
                    combinationDTO.setStatusStr("正在进行中");
                }
            } else {
                combinationDTO.setStatusStr("关闭");
            }
            Optional<StoreProduct> optionalStoreProduct = storeProductRepository
                    .findById(combinationDTO.getProductId());
            StoreProduct storeProduct = optionalStoreProduct.get();
            if (storeProduct != null) {
                List<StoreProductParam> storeProductParamList = new ArrayList<>();
                StoreProductParam storeProductParam = new StoreProductParam();
                storeProductParam.setId(storeProduct.getId());
                storeProductParam.setName(storeProduct.getProductName());
                storeProductParam.setPrice(storeProduct.getPrice());
                storeProductParam.setStock(storeProduct.getStock());
                storeProductParamList.add(storeProductParam);
                combinationDTO.setProductList(storeProductParamList);
            }

        }
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", combinationDTOS);
        map.put("totalElements", page.getTotalElements());

        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onSale(Long id, Integer status) {
        if (status == 1) {
            status = 0;
        } else {
            status = 1;
        }
        storeCombinationRepository.updateOnsale(status, id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onVerify(Long id, Integer status) {
        if (status == 1) {
            status = 0;
        } else {
            status = 1;
        }
        storeCombinationRepository.updateOnVerify(status, id);
    }

    @Override
    public List<StoreCombinationDTO> queryAll(StoreCombinationQueryCriteria criteria) {
        return storeCombinationMapper.toDto(storeCombinationRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public StoreCombinationDTO findById(Long id) {
        Optional<StoreCombination> storeCombination = storeCombinationRepository.findById(id);
        ValidationUtil.isNull(storeCombination, "StoreCombination", "id", id);
        return storeCombinationMapper.toDto(storeCombination.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreCombinationDTO create(StoreCombination resources) {
        resources.setIsDel(0);
        resources.setStatus(1);
        resources.setVerifyStatus(0);
        resources.setAddTime(String.valueOf(OrderUtil.getSecondTimestampTwo()));
        return storeCombinationMapper.toDto(storeCombinationRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreCombination resources) {
        Optional<StoreCombination> optionalStoreCombination = storeCombinationRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalStoreCombination, "StoreCombination", "id", resources.getId());
        StoreCombination storeCombination = optionalStoreCombination.get();
        storeCombination.copy(resources);
        storeCombinationRepository.save(storeCombination);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        storeCombinationRepository.deleteById(id);
    }
}
