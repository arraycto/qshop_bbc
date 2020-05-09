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

import co.lq.modules.shop.domain.StoreSystemConfig;
import co.lq.modules.shop.repository.StoreSystemConfigRepository;
import co.lq.modules.shop.service.StoreSystemConfigService;
import co.lq.modules.shop.service.dto.StoreSystemConfigDTO;
import co.lq.modules.shop.service.dto.StoreSystemConfigQueryCriteria;
import co.lq.modules.shop.service.mapper.StoreSystemConfigMapper;
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
public class StoreSystemConfigServiceImpl implements StoreSystemConfigService {

    private final StoreSystemConfigRepository storeSystemConfigRepository;

    private final StoreSystemConfigMapper     storeSystemConfigMapper;

    public StoreSystemConfigServiceImpl(StoreSystemConfigRepository storeSystemConfigRepository,
                                        StoreSystemConfigMapper storeSystemConfigMapper) {
        this.storeSystemConfigRepository = storeSystemConfigRepository;
        this.storeSystemConfigMapper = storeSystemConfigMapper;
    }

    @Override
    public Map<String, Object> queryAll(StoreSystemConfigQueryCriteria criteria, Pageable pageable) {
        Page<StoreSystemConfig> page = storeSystemConfigRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(storeSystemConfigMapper::toDto));
    }

    @Override
    public List<StoreSystemConfigDTO> queryAll(StoreSystemConfigQueryCriteria criteria) {
        return storeSystemConfigMapper.toDto(storeSystemConfigRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public StoreSystemConfigDTO findById(Long id) {
        StoreSystemConfig storeSystemConfig = storeSystemConfigRepository.findById(id)
                .orElseGet(StoreSystemConfig::new);
        ValidationUtil.isNull(storeSystemConfig.getId(), "StoreSystemConfig", "id", id);
        return storeSystemConfigMapper.toDto(storeSystemConfig);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreSystemConfigDTO create(StoreSystemConfig resources) {
        resources.setDeleted(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setAddTime(timestamp);
        resources.setModifyTime(timestamp);
        return storeSystemConfigMapper.toDto(storeSystemConfigRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreSystemConfig resources) {
        StoreSystemConfig storeSystemConfig = storeSystemConfigRepository.findById(resources.getId())
                .orElseGet(StoreSystemConfig::new);
        ValidationUtil.isNull(storeSystemConfig.getId(), "StoreSystemConfig", "id", resources.getId());
        storeSystemConfig.copy(resources);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        storeSystemConfig.setModifyTime(timestamp);
        storeSystemConfigRepository.save(storeSystemConfig);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            StoreSystemConfig storeSystemConfig = storeSystemConfigRepository.findById(id)
                    .orElseGet(StoreSystemConfig::new);
            ValidationUtil.isNull(storeSystemConfig.getId(), "StoreSystemConfig", "id", id);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            storeSystemConfig.setModifyTime(timestamp);
            storeSystemConfig.setDeleted(1);
            storeSystemConfigRepository.save(storeSystemConfig);
        }
    }

    @Override
    public void download(List<StoreSystemConfigDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StoreSystemConfigDTO storeSystemConfig : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("字段名称", storeSystemConfig.getMenuName());
            map.put("默认值", storeSystemConfig.getValue());
            map.put("排序", storeSystemConfig.getSort());
            map.put("是否隐藏", storeSystemConfig.getStatus());
            map.put("所属店铺", storeSystemConfig.getStoreId());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public StoreSystemConfig findByKey(String str) {
        return storeSystemConfigRepository.findByMenuName(str);
    }
}
