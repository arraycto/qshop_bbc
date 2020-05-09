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

import co.lq.modules.shop.domain.StoreComplaint;
import co.lq.modules.shop.repository.StoreComplaintRepository;
import co.lq.modules.shop.service.StoreComplaintService;
import co.lq.modules.shop.service.dto.StoreComplaintDTO;
import co.lq.modules.shop.service.dto.StoreComplaintQueryCriteria;
import co.lq.modules.shop.service.mapper.StoreComplaintMapper;
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
public class StoreComplaintServiceImpl implements StoreComplaintService {

    private final StoreComplaintRepository storeComplaintRepository;

    private final StoreComplaintMapper     storeComplaintMapper;

    public StoreComplaintServiceImpl(StoreComplaintRepository storeComplaintRepository,
                                     StoreComplaintMapper storeComplaintMapper) {
        this.storeComplaintRepository = storeComplaintRepository;
        this.storeComplaintMapper = storeComplaintMapper;
    }

    @Override
    public Map<String, Object> queryAll(StoreComplaintQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<StoreComplaint> page = storeComplaintRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(storeComplaintMapper::toDto));
    }

    @Override
    public List<StoreComplaintDTO> queryAll(StoreComplaintQueryCriteria criteria) {
        criteria.setDeleted(0);
        return storeComplaintMapper.toDto(storeComplaintRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public StoreComplaintDTO findById(Long id) {
        StoreComplaint storeComplaint = storeComplaintRepository.findById(id).orElseGet(StoreComplaint::new);
        ValidationUtil.isNull(storeComplaint.getId(), "StoreComplaint", "id", id);
        return storeComplaintMapper.toDto(storeComplaint);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreComplaintDTO create(StoreComplaint resources) {
        resources.setDeleted(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setAddTime(timestamp);
        resources.setModifyTime(timestamp);
        return storeComplaintMapper.toDto(storeComplaintRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreComplaint resources) {
        StoreComplaint storeComplaint = storeComplaintRepository.findById(resources.getId())
                .orElseGet(StoreComplaint::new);
        ValidationUtil.isNull(storeComplaint.getId(), "StoreComplaint", "id", resources.getId());
        storeComplaint.copy(resources);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        storeComplaint.setModifyTime(timestamp);
        storeComplaintRepository.save(storeComplaint);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            StoreComplaint storeComplaint = storeComplaintRepository.findById(id).orElseGet(StoreComplaint::new);
            ValidationUtil.isNull(storeComplaint.getId(), "StoreComplaint", "id", id);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            storeComplaint.setModifyTime(timestamp);
            storeComplaintRepository.save(storeComplaint);
        }
    }

    @Override
    public void download(List<StoreComplaintDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StoreComplaintDTO storeComplaint : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("会员id", storeComplaint.getUid());
            map.put("投诉原因", storeComplaint.getReason());
            map.put("投诉内容", storeComplaint.getDesc());
            map.put("所属店铺", storeComplaint.getStoreId());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
