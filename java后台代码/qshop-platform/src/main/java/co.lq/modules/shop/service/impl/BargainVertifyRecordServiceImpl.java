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

import co.lq.modules.shop.domain.BargainVertifyRecord;
import co.lq.modules.shop.repository.BargainVertifyRecordRepository;
import co.lq.modules.shop.service.BargainVertifyRecordService;
import co.lq.modules.shop.service.dto.BargainVertifyRecordDTO;
import co.lq.modules.shop.service.dto.BargainVertifyRecordQueryCriteria;
import co.lq.modules.shop.service.mapper.BargainVertifyRecordMapper;
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
public class BargainVertifyRecordServiceImpl implements BargainVertifyRecordService {

    private final BargainVertifyRecordRepository bargainVertifyRecordRepository;

    private final BargainVertifyRecordMapper     bargainVertifyRecordMapper;

    public BargainVertifyRecordServiceImpl(BargainVertifyRecordRepository bargainVertifyRecordRepository,
                                           BargainVertifyRecordMapper bargainVertifyRecordMapper) {
        this.bargainVertifyRecordRepository = bargainVertifyRecordRepository;
        this.bargainVertifyRecordMapper = bargainVertifyRecordMapper;
    }

    @Override
    public Map<String, Object> queryAll(BargainVertifyRecordQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<BargainVertifyRecord> page = bargainVertifyRecordRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(bargainVertifyRecordMapper::toDto));
    }

    @Override
    public List<BargainVertifyRecordDTO> queryAll(BargainVertifyRecordQueryCriteria criteria) {
        criteria.setDeleted(0);
        return bargainVertifyRecordMapper.toDto(bargainVertifyRecordRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public BargainVertifyRecordDTO findById(Long id) {
        BargainVertifyRecord bargainVertifyRecord = bargainVertifyRecordRepository.findById(id)
                .orElseGet(BargainVertifyRecord::new);
        ValidationUtil.isNull(bargainVertifyRecord.getId(), "BargainVertifyRecord", "id", id);
        return bargainVertifyRecordMapper.toDto(bargainVertifyRecord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BargainVertifyRecordDTO create(BargainVertifyRecord resources) {
        resources.setDeleted(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setAddTime(timestamp);
        resources.setModifyTime(timestamp);
        return bargainVertifyRecordMapper.toDto(bargainVertifyRecordRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BargainVertifyRecord resources) {
        BargainVertifyRecord bargainVertifyRecord = bargainVertifyRecordRepository.findById(resources.getId())
                .orElseGet(BargainVertifyRecord::new);
        ValidationUtil.isNull(bargainVertifyRecord.getId(), "BargainVertifyRecord", "id", resources.getId());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setModifyTime(timestamp);
        bargainVertifyRecord.copy(resources);
        bargainVertifyRecordRepository.save(bargainVertifyRecord);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            BargainVertifyRecord bargainVertifyRecord = bargainVertifyRecordRepository.findById(id)
                    .orElseGet(BargainVertifyRecord::new);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            bargainVertifyRecord.setDeleted(1);
            bargainVertifyRecord.setModifyTime(timestamp);
            bargainVertifyRecordRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<BargainVertifyRecordDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (BargainVertifyRecordDTO bargainVertifyRecord : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(" bargainId", bargainVertifyRecord.getBargainId());
            map.put("审核人", bargainVertifyRecord.getVertifyMan());
            map.put(" status", bargainVertifyRecord.getStatus());
            map.put("反馈详情", bargainVertifyRecord.getDetail());
            map.put("所属店铺", bargainVertifyRecord.getStoreId());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
