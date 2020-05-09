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

import co.lq.modules.shop.domain.SeckillVertifyRecord;
import co.lq.modules.shop.repository.SeckillVertifyRecordRepository;
import co.lq.modules.shop.service.SeckillVertifyRecordService;
import co.lq.modules.shop.service.dto.SeckillVertifyRecordDTO;
import co.lq.modules.shop.service.dto.SeckillVertifyRecordQueryCriteria;
import co.lq.modules.shop.service.mapper.SeckillVertifyRecordMapper;
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
public class SeckillVertifyRecordServiceImpl implements SeckillVertifyRecordService {

    private final SeckillVertifyRecordRepository seckillVertifyRecordRepository;

    private final SeckillVertifyRecordMapper     seckillVertifyRecordMapper;

    public SeckillVertifyRecordServiceImpl(SeckillVertifyRecordRepository seckillVertifyRecordRepository,
                                           SeckillVertifyRecordMapper seckillVertifyRecordMapper) {
        this.seckillVertifyRecordRepository = seckillVertifyRecordRepository;
        this.seckillVertifyRecordMapper = seckillVertifyRecordMapper;
    }

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(SeckillVertifyRecordQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<SeckillVertifyRecord> page = seckillVertifyRecordRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(seckillVertifyRecordMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<SeckillVertifyRecordDTO> queryAll(SeckillVertifyRecordQueryCriteria criteria) {
        criteria.setDeleted(0);
        return seckillVertifyRecordMapper.toDto(seckillVertifyRecordRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public SeckillVertifyRecordDTO findById(Long id) {
        SeckillVertifyRecord seckillVertifyRecord = seckillVertifyRecordRepository.findById(id)
                .orElseGet(SeckillVertifyRecord::new);
        ValidationUtil.isNull(seckillVertifyRecord.getId(), "SeckillVertifyRecord", "id", id);
        return seckillVertifyRecordMapper.toDto(seckillVertifyRecord);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public SeckillVertifyRecordDTO create(SeckillVertifyRecord resources) {
        resources.setDeleted(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setAddTime(timestamp);
        resources.setModifyTime(timestamp);
        return seckillVertifyRecordMapper.toDto(seckillVertifyRecordRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(SeckillVertifyRecord resources) {
        SeckillVertifyRecord seckillVertifyRecord = seckillVertifyRecordRepository.findById(resources.getId())
                .orElseGet(SeckillVertifyRecord::new);
        ValidationUtil.isNull(seckillVertifyRecord.getId(), "SeckillVertifyRecord", "id", resources.getId());
        seckillVertifyRecord.copy(resources);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        seckillVertifyRecord.setModifyTime(timestamp);
        seckillVertifyRecordRepository.save(seckillVertifyRecord);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            SeckillVertifyRecord seckillVertifyRecord = seckillVertifyRecordRepository.findById(id)
                    .orElseGet(SeckillVertifyRecord::new);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            seckillVertifyRecord.setModifyTime(timestamp);
            seckillVertifyRecordRepository.save(seckillVertifyRecord);
        }
    }

    @Override
    public void download(List<SeckillVertifyRecordDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SeckillVertifyRecordDTO seckillVertifyRecord : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(" seckillId", seckillVertifyRecord.getSeckillId());
            map.put("审核人", seckillVertifyRecord.getVertifyMan());
            map.put(" status", seckillVertifyRecord.getStatus());
            map.put("反馈详情", seckillVertifyRecord.getDetail());
            map.put("所属店铺", seckillVertifyRecord.getStoreId());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
