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

import co.lq.modules.shop.domain.CombinationVertifyRecord;
import co.lq.modules.shop.repository.CombinationVertifyRecordRepository;
import co.lq.modules.shop.service.CombinationVertifyRecordService;
import co.lq.modules.shop.service.dto.CombinationVertifyRecordDTO;
import co.lq.modules.shop.service.dto.CombinationVertifyRecordQueryCriteria;
import co.lq.modules.shop.service.mapper.CombinationVertifyRecordMapper;
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
 * @date 2020-03-11
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CombinationVertifyRecordServiceImpl implements CombinationVertifyRecordService {

    private final CombinationVertifyRecordRepository combinationVertifyRecordRepository;

    private final CombinationVertifyRecordMapper     combinationVertifyRecordMapper;

    public CombinationVertifyRecordServiceImpl(CombinationVertifyRecordRepository combinationVertifyRecordRepository,
                                               CombinationVertifyRecordMapper combinationVertifyRecordMapper) {
        this.combinationVertifyRecordRepository = combinationVertifyRecordRepository;
        this.combinationVertifyRecordMapper = combinationVertifyRecordMapper;
    }

    @Override
    public Map<String, Object> queryAll(CombinationVertifyRecordQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<CombinationVertifyRecord> page = combinationVertifyRecordRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(combinationVertifyRecordMapper::toDto));
    }

    @Override
    public List<CombinationVertifyRecordDTO> queryAll(CombinationVertifyRecordQueryCriteria criteria) {
        criteria.setDeleted(0);
        return combinationVertifyRecordMapper.toDto(combinationVertifyRecordRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public CombinationVertifyRecordDTO findById(Long id) {
        CombinationVertifyRecord combinationVertifyRecord = combinationVertifyRecordRepository.findById(id)
                .orElseGet(CombinationVertifyRecord::new);
        ValidationUtil.isNull(combinationVertifyRecord.getId(), "CombinationVertifyRecord", "id", id);
        return combinationVertifyRecordMapper.toDto(combinationVertifyRecord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CombinationVertifyRecordDTO create(CombinationVertifyRecord resources) {
        resources.setDeleted(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setAddTime(timestamp);
        resources.setModifyTime(timestamp);
        return combinationVertifyRecordMapper.toDto(combinationVertifyRecordRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CombinationVertifyRecord resources) {
        CombinationVertifyRecord combinationVertifyRecord = combinationVertifyRecordRepository
                .findById(resources.getId())
                .orElseGet(CombinationVertifyRecord::new);
        ValidationUtil.isNull(combinationVertifyRecord.getId(), "CombinationVertifyRecord", "id", resources.getId());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setModifyTime(timestamp);
        combinationVertifyRecord.copy(resources);
        combinationVertifyRecordRepository.save(combinationVertifyRecord);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            CombinationVertifyRecord combinationVertifyRecord = combinationVertifyRecordRepository.findById(id)
                    .orElseGet(CombinationVertifyRecord::new);
            combinationVertifyRecord.setDeleted(1);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            combinationVertifyRecord.setModifyTime(timestamp);
            combinationVertifyRecordRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<CombinationVertifyRecordDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CombinationVertifyRecordDTO combinationVertifyRecord : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(" combinationId", combinationVertifyRecord.getCombinationId());
            map.put("审核人", combinationVertifyRecord.getVertifyMan());
            map.put(" status", combinationVertifyRecord.getStatus());
            map.put("反馈详情", combinationVertifyRecord.getDetail());
            map.put("所属店铺", combinationVertifyRecord.getStoreId());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
