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

import co.lq.modules.shop.domain.StoreSettle;
import co.lq.modules.shop.repository.StoreSettleRepository;
import co.lq.modules.shop.service.StoreSettleService;
import co.lq.modules.shop.service.dto.StoreSettleDTO;
import co.lq.modules.shop.service.dto.StoreSettleQueryCriteria;
import co.lq.modules.shop.service.mapper.StoreSettleMapper;
import co.lq.utils.FileUtil;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

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
public class StoreSettleServiceImpl implements StoreSettleService {

    private final StoreSettleRepository storeSettleRepository;

    private final StoreSettleMapper     storeSettleMapper;

    public StoreSettleServiceImpl(StoreSettleRepository storeSettleRepository, StoreSettleMapper storeSettleMapper) {
        this.storeSettleRepository = storeSettleRepository;
        this.storeSettleMapper = storeSettleMapper;
    }

    @Override
    public Map<String, Object> queryAll(StoreSettleQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<StoreSettle> page = storeSettleRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(storeSettleMapper::toDto));
    }

    @Override
    public List<StoreSettleDTO> queryAll(StoreSettleQueryCriteria criteria) {
        criteria.setDeleted(0);
        return storeSettleMapper.toDto(storeSettleRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public StoreSettleDTO findById(Long id) {
        StoreSettle storeSettle = storeSettleRepository.findById(id).orElseGet(StoreSettle::new);
        ValidationUtil.isNull(storeSettle.getId(), "StoreSettle", "id", id);
        return storeSettleMapper.toDto(storeSettle);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreSettleDTO create(StoreSettle resources) {
        StoreSettle shop = new StoreSettle();
        if (resources.getId() != null) {
            shop = storeSettleRepository.findById(resources.getId()).orElseGet(StoreSettle::new);
            ValidationUtil.isNull(shop.getId(), "StoreSettle", "id", resources.getId());
            shop.copy(resources);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            shop.setModifyTime(timestamp);
        } else {
            resources.setDeleted(0);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            resources.setAddTime(timestamp);
            resources.setModifyTime(timestamp);
            shop.copy(resources);
        }

        return storeSettleMapper.toDto(storeSettleRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreSettle resources) {
        StoreSettle storeSettle = storeSettleRepository.findById(resources.getId()).orElseGet(StoreSettle::new);
        ValidationUtil.isNull(storeSettle.getId(), "StoreSettle", "id", resources.getId());
        storeSettle.copy(resources);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        storeSettle.setModifyTime(timestamp);
        storeSettleRepository.save(storeSettle);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            StoreSettle storeSettle = storeSettleRepository.findById(id).orElseGet(StoreSettle::new);
            ValidationUtil.isNull(storeSettle.getId(), "StoreSettle", "id", id);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            storeSettle.setModifyTime(timestamp);
            storeSettle.setDeleted(1);
            storeSettleRepository.save(storeSettle);
        }
    }

    @Override
    public void download(List<StoreSettleDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StoreSettleDTO storeSettle : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("所属店铺", storeSettle.getStoreId());
            map.put("公司名称", storeSettle.getCompanyName());
            map.put("营业执照注册号", storeSettle.getLicenseNo());
            map.put("法定代表人姓名", storeSettle.getRepresentative());
            map.put("法定代表人身份证号", storeSettle.getIdNo());
            map.put("法定代表人身份证正面", storeSettle.getRepresentIdentity1());
            map.put("法定代表人身份证反面", storeSettle.getRepresentIdentity2());
            map.put("营业执照开始时间", storeSettle.getLicenseStartTime());
            map.put("营业执照结束时间", storeSettle.getLicenseEndTime());
            map.put("公司所在地", storeSettle.getArea());
            map.put("公司详细地址", storeSettle.getAddressDetail());
            map.put("公司电话", storeSettle.getTel());
            map.put("公司联系人", storeSettle.getContact());
            map.put("公司联系人手机", storeSettle.getPhone());
            map.put("注册资本", storeSettle.getCapital());
            map.put("公司官网", storeSettle.getWebsiteUrl());
            map.put("添加时间", storeSettle.getAddTime());
            map.put("更新时间", storeSettle.getModifyTime());
            map.put("逻辑删除", storeSettle.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public int getCount(StoreSettleQueryCriteria criteria) {
        return storeSettleRepository.countByStatusAndClosedAndDeleted(criteria.getStatus(), criteria.getClosed(),
                criteria.getDeleted());
    }
}
