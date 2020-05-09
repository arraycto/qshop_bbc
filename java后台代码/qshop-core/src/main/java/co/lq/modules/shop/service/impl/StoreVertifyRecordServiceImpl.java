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

import co.lq.modules.shop.domain.StoreVertifyRecord;
import co.lq.modules.shop.repository.StoreVertifyRecordRepository;
import co.lq.modules.shop.service.StoreVertifyRecordService;
import co.lq.modules.shop.service.dto.StoreVertifyRecordDTO;
import co.lq.modules.shop.service.dto.StoreVertifyRecordQueryCriteria;
import co.lq.modules.shop.service.mapper.StoreVertifyRecordMapper;
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
 * @date 2020-03-28
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StoreVertifyRecordServiceImpl implements StoreVertifyRecordService {

    private final StoreVertifyRecordRepository storeVertifyRecordRepository;

    private final StoreVertifyRecordMapper     storeVertifyRecordMapper;

    public StoreVertifyRecordServiceImpl(StoreVertifyRecordRepository storeVertifyRecordRepository,
                                         StoreVertifyRecordMapper storeVertifyRecordMapper) {
        this.storeVertifyRecordRepository = storeVertifyRecordRepository;
        this.storeVertifyRecordMapper = storeVertifyRecordMapper;
    }

    @Override
    public Map<String, Object> queryAll(StoreVertifyRecordQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<StoreVertifyRecord> page = storeVertifyRecordRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(storeVertifyRecordMapper::toDto));
    }

    @Override
    public List<StoreVertifyRecordDTO> queryAll(StoreVertifyRecordQueryCriteria criteria) {
        criteria.setDeleted(0);
        return storeVertifyRecordMapper.toDto(storeVertifyRecordRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public StoreVertifyRecordDTO findById(Long id) {
        StoreVertifyRecord storeVertifyRecord = storeVertifyRecordRepository.findById(id)
                .orElseGet(StoreVertifyRecord::new);
        ValidationUtil.isNull(storeVertifyRecord.getId(), "StoreVertifyRecord", "id", id);
        return storeVertifyRecordMapper.toDto(storeVertifyRecord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreVertifyRecordDTO create(StoreVertifyRecord resources) {
        resources.setDeleted(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setAddTime(timestamp);
        resources.setModifyTime(timestamp);
        return storeVertifyRecordMapper.toDto(storeVertifyRecordRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreVertifyRecord resources) {
        StoreVertifyRecord storeVertifyRecord = storeVertifyRecordRepository.findById(resources.getId())
                .orElseGet(StoreVertifyRecord::new);
        ValidationUtil.isNull(storeVertifyRecord.getId(), "StoreVertifyRecord", "id", resources.getId());
        storeVertifyRecord.copy(resources);
        storeVertifyRecordRepository.save(storeVertifyRecord);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            storeVertifyRecordRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<StoreVertifyRecordDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StoreVertifyRecordDTO storeVertifyRecord : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("审核人", storeVertifyRecord.getVertifier());
            map.put("审核状态", storeVertifyRecord.getStatus());
            map.put("反馈详情", storeVertifyRecord.getDetail());
            map.put("类目id", storeVertifyRecord.getStoreId());
            map.put("添加时间", storeVertifyRecord.getAddTime());
            map.put("更新时间", storeVertifyRecord.getModifyTime());
            map.put("逻辑删除", storeVertifyRecord.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onVerify(Long id, Integer status) {
        storeVertifyRecordRepository.updateOnVerify(status, id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onClosed(Long id, Integer status) {
        storeVertifyRecordRepository.updateOnClosed(status, id);
    }
}
