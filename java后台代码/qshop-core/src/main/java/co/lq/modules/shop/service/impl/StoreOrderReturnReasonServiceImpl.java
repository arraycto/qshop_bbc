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

import co.lq.modules.shop.domain.StoreOrderReturnReason;
import co.lq.modules.shop.repository.StoreOrderReturnReasonRepository;
import co.lq.modules.shop.service.StoreOrderReturnReasonService;
import co.lq.modules.shop.service.dto.StoreOrderReturnReasonDTO;
import co.lq.modules.shop.service.dto.StoreOrderReturnReasonQueryCriteria;
import co.lq.modules.shop.service.mapper.StoreOrderReturnReasonMapper;
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
 * @date 2020-03-29
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StoreOrderReturnReasonServiceImpl implements StoreOrderReturnReasonService {

    private final StoreOrderReturnReasonRepository storeOrderReturnReasonRepository;

    private final StoreOrderReturnReasonMapper     storeOrderReturnReasonMapper;

    public StoreOrderReturnReasonServiceImpl(StoreOrderReturnReasonRepository storeOrderReturnReasonRepository,
                                             StoreOrderReturnReasonMapper storeOrderReturnReasonMapper) {
        this.storeOrderReturnReasonRepository = storeOrderReturnReasonRepository;
        this.storeOrderReturnReasonMapper = storeOrderReturnReasonMapper;
    }

    @Override
    public Map<String, Object> queryAll(StoreOrderReturnReasonQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<StoreOrderReturnReason> page = storeOrderReturnReasonRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(storeOrderReturnReasonMapper::toDto));
    }

    @Override
    public List<StoreOrderReturnReasonDTO> queryAll(StoreOrderReturnReasonQueryCriteria criteria) {
        criteria.setDeleted(0);
        return storeOrderReturnReasonMapper.toDto(storeOrderReturnReasonRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public StoreOrderReturnReasonDTO findById(Long id) {
        StoreOrderReturnReason storeOrderReturnReason = storeOrderReturnReasonRepository.findById(id)
                .orElseGet(StoreOrderReturnReason::new);
        ValidationUtil.isNull(storeOrderReturnReason.getId(), "StoreOrderReturnReason", "id", id);
        return storeOrderReturnReasonMapper.toDto(storeOrderReturnReason);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreOrderReturnReasonDTO create(StoreOrderReturnReason resources) {
        resources.setDeleted(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setAddTime(timestamp);
        resources.setModifyTime(timestamp);
        return storeOrderReturnReasonMapper.toDto(storeOrderReturnReasonRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreOrderReturnReason resources) {
        StoreOrderReturnReason storeOrderReturnReason = storeOrderReturnReasonRepository.findById(resources.getId())
                .orElseGet(StoreOrderReturnReason::new);
        ValidationUtil.isNull(storeOrderReturnReason.getId(), "StoreOrderReturnReason", "id", resources.getId());
        storeOrderReturnReason.copy(resources);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        storeOrderReturnReason.setModifyTime(timestamp);
        storeOrderReturnReasonRepository.save(storeOrderReturnReason);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            StoreOrderReturnReason storeOrderReturnReason = storeOrderReturnReasonRepository.findById(id)
                    .orElseGet(StoreOrderReturnReason::new);
            ValidationUtil.isNull(storeOrderReturnReason.getId(), "StoreOrderReturnReason", "id", id);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            storeOrderReturnReason.setModifyTime(timestamp);
            storeOrderReturnReason.setDeleted(1);
            storeOrderReturnReasonRepository.save(storeOrderReturnReason);
        }
    }

    @Override
    public void download(List<StoreOrderReturnReasonDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StoreOrderReturnReasonDTO storeOrderReturnReason : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("退货类型", storeOrderReturnReason.getName());
            map.put(" sort", storeOrderReturnReason.getSort());
            map.put("状态：0->不启用；1->启用", storeOrderReturnReason.getStatus());
            map.put("所属店铺", storeOrderReturnReason.getStoreId());
            map.put("添加时间", storeOrderReturnReason.getAddTime());
            map.put("更新时间", storeOrderReturnReason.getModifyTime());
            map.put("逻辑删除", storeOrderReturnReason.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
