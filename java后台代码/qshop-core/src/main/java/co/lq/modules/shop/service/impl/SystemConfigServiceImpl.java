package co.lq.modules.shop.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.shop.domain.SystemConfig;
import co.lq.modules.shop.repository.SystemConfigRepository;
import co.lq.modules.shop.service.SystemConfigService;
import co.lq.modules.shop.service.dto.SystemConfigDTO;
import co.lq.modules.shop.service.dto.SystemConfigQueryCriteria;
import co.lq.modules.shop.service.mapper.SystemConfigMapper;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-10-10
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SystemConfigServiceImpl implements SystemConfigService {

    private final SystemConfigRepository systemConfigRepository;

    private final SystemConfigMapper     systemConfigMapper;

    public SystemConfigServiceImpl(SystemConfigRepository systemConfigRepository,
                                   SystemConfigMapper systemConfigMapper) {
        this.systemConfigRepository = systemConfigRepository;
        this.systemConfigMapper = systemConfigMapper;
    }

    @Override
    public Map<String, Object> queryAll(SystemConfigQueryCriteria criteria, Pageable pageable) {
        Page<SystemConfig> page = systemConfigRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(systemConfigMapper::toDto));
    }

    @Override
    public List<SystemConfigDTO> queryAll(SystemConfigQueryCriteria criteria) {
        return systemConfigMapper.toDto(systemConfigRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public SystemConfigDTO findById(Integer id) {
        Optional<SystemConfig> systemConfig = systemConfigRepository.findById(id);
        ValidationUtil.isNull(systemConfig, "SystemConfig", "id", id);
        return systemConfigMapper.toDto(systemConfig.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SystemConfigDTO create(SystemConfig resources) {
        return systemConfigMapper.toDto(systemConfigRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SystemConfig resources) {
        Optional<SystemConfig> optionalSystemConfig = systemConfigRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalSystemConfig, "SystemConfig", "id", resources.getId());
        SystemConfig systemConfig = optionalSystemConfig.get();
        systemConfig.copy(resources);
        systemConfigRepository.save(systemConfig);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        systemConfigRepository.deleteById(id);
    }

    @Override
    public SystemConfig findByKey(String str) {
        return systemConfigRepository.findByMenuName(str);
    }

    @Override
    public String getData(String name) {
        SystemConfig systemConfig = systemConfigRepository.findByMenuName(name);
        if (systemConfig == null) {
            return "";
        }
        return systemConfig.getValue();
    }
}
