package co.lq.modules.shop.service.impl;

import co.lq.modules.shop.domain.StoreWithdrawRecord;
import co.lq.utils.ValidationUtil;
import co.lq.utils.FileUtil;
import co.lq.modules.shop.repository.StoreWithdrawRecordRepository;
import co.lq.modules.shop.service.StoreWithdrawRecordService;
import co.lq.modules.shop.service.dto.StoreWithdrawRecordDTO;
import co.lq.modules.shop.service.dto.StoreWithdrawRecordQueryCriteria;
import co.lq.modules.shop.service.mapper.StoreWithdrawRecordMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @author billy
* @date 2020-04-13
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StoreWithdrawRecordServiceImpl implements StoreWithdrawRecordService {

    private final StoreWithdrawRecordRepository storeWithdrawRecordRepository;

    private final StoreWithdrawRecordMapper storeWithdrawRecordMapper;

    public StoreWithdrawRecordServiceImpl(StoreWithdrawRecordRepository storeWithdrawRecordRepository, StoreWithdrawRecordMapper storeWithdrawRecordMapper) {
        this.storeWithdrawRecordRepository = storeWithdrawRecordRepository;
        this.storeWithdrawRecordMapper = storeWithdrawRecordMapper;
    }

    @Override
    public Map<String,Object> queryAll(StoreWithdrawRecordQueryCriteria criteria, Pageable pageable){
        criteria.setDeleted(0);
        Page<StoreWithdrawRecord> page = storeWithdrawRecordRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(storeWithdrawRecordMapper::toDto));
    }

    @Override
    public List<StoreWithdrawRecordDTO> queryAll(StoreWithdrawRecordQueryCriteria criteria){
        criteria.setDeleted(0);
        return storeWithdrawRecordMapper.toDto(storeWithdrawRecordRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public StoreWithdrawRecordDTO findById(Long id) {
        StoreWithdrawRecord storeWithdrawRecord = storeWithdrawRecordRepository.findById(id).orElseGet(StoreWithdrawRecord::new);
        ValidationUtil.isNull(storeWithdrawRecord.getId(),"StoreWithdrawRecord","id",id);
        return storeWithdrawRecordMapper.toDto(storeWithdrawRecord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreWithdrawRecordDTO create(StoreWithdrawRecord resources) {
        resources.setDeleted(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setAddTime(timestamp);
        resources.setModifyTime(timestamp);
        return storeWithdrawRecordMapper.toDto(storeWithdrawRecordRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreWithdrawRecord resources) {
        StoreWithdrawRecord storeWithdrawRecord = storeWithdrawRecordRepository.findById(resources.getId()).orElseGet(StoreWithdrawRecord::new);
        ValidationUtil.isNull( storeWithdrawRecord.getId(),"StoreWithdrawRecord","id",resources.getId());
        storeWithdrawRecord.copy(resources);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        storeWithdrawRecord.setModifyTime(timestamp);
        storeWithdrawRecordRepository.save(storeWithdrawRecord);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            StoreWithdrawRecord storeWithdrawRecord = storeWithdrawRecordRepository.findById(id).orElseGet(StoreWithdrawRecord::new);
            ValidationUtil.isNull( storeWithdrawRecord.getId(),"StoreWithdrawRecord","id",id);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            storeWithdrawRecord.setModifyTime(timestamp);
            storeWithdrawRecord.setDeleted(1);
            storeWithdrawRecordRepository.save(storeWithdrawRecord);
        }
    }

    @Override
    public void download(List<StoreWithdrawRecordDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StoreWithdrawRecordDTO storeWithdrawRecord : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("提现申请ID", storeWithdrawRecord.getStoreWithdrawId());
            map.put("审核人", storeWithdrawRecord.getVertifier());
            map.put(" status",  storeWithdrawRecord.getStatus());
            map.put("反馈详情", storeWithdrawRecord.getDetail());
            map.put("所属店铺", storeWithdrawRecord.getStoreId());
            map.put("添加时间", storeWithdrawRecord.getAddTime());
            map.put("更新时间", storeWithdrawRecord.getModifyTime());
            map.put("逻辑删除", storeWithdrawRecord.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}