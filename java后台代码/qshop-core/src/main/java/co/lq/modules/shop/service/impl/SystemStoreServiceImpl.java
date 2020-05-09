package co.lq.modules.shop.service.impl;

import java.io.IOException;
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

import co.lq.modules.shop.domain.SystemStore;
import co.lq.modules.shop.repository.SystemStoreRepository;
import co.lq.modules.shop.service.SystemStoreService;
import co.lq.modules.shop.service.dto.SystemStoreDto;
import co.lq.modules.shop.service.dto.SystemStoreQueryCriteria;
import co.lq.modules.shop.service.mapper.SystemStoreMapper;
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
 * @date 2020-03-03
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SystemStoreServiceImpl implements SystemStoreService {

    private final SystemStoreRepository systemStoreRepository;

    private final SystemStoreMapper     systemStoreMapper;

    public SystemStoreServiceImpl(SystemStoreRepository systemStoreRepository, SystemStoreMapper systemStoreMapper) {
        this.systemStoreRepository = systemStoreRepository;
        this.systemStoreMapper = systemStoreMapper;
    }

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(SystemStoreQueryCriteria criteria, Pageable pageable) {
        Page<SystemStore> page = systemStoreRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(systemStoreMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<SystemStoreDto> queryAll(SystemStoreQueryCriteria criteria) {
        return systemStoreMapper.toDto(systemStoreRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public SystemStoreDto findById(Long id) {
        SystemStore systemStore = systemStoreRepository.findById(id).orElseGet(SystemStore::new);
        ValidationUtil.isNull(systemStore.getId(), "SystemStore", "id", id);
        return systemStoreMapper.toDto(systemStore);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public SystemStoreDto create(SystemStore resources) {
        return systemStoreMapper.toDto(systemStoreRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(SystemStore resources) {
        SystemStore systemStore = systemStoreRepository.findById(resources.getId()).orElseGet(SystemStore::new);
        ValidationUtil.isNull(systemStore.getId(), "SystemStore", "id", resources.getId());
        systemStore.copy(resources);
        systemStoreRepository.save(systemStore);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            systemStoreRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<SystemStoreDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SystemStoreDto systemStore : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("门店名称", systemStore.getName());
            map.put("简介", systemStore.getIntroduction());
            map.put("手机号码", systemStore.getPhone());
            map.put("省市区", systemStore.getAddress());
            map.put("详细地址", systemStore.getDetailedAddress());
            map.put("门店logo", systemStore.getImage());
            map.put("纬度", systemStore.getLatitude());
            map.put("经度", systemStore.getLongitude());
            map.put("核销有效日期", systemStore.getValidTime());
            map.put("每日营业开关时间", systemStore.getDayTime());
            map.put("添加时间", systemStore.getAddTime());
            map.put("是否显示", systemStore.getIsShow());
            map.put("是否删除", systemStore.getIsDel());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
