package co.lq.modules.shop.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import co.lq.modules.shop.domain.SystemGroupData;
import co.lq.modules.shop.repository.SystemGroupDataRepository;
import co.lq.modules.shop.service.SystemGroupDataService;
import co.lq.modules.shop.service.dto.SystemGroupDataDTO;
import co.lq.modules.shop.service.dto.SystemGroupDataQueryCriteria;
import co.lq.modules.shop.service.mapper.SystemGroupDataMapper;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-10-18
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SystemGroupDataServiceImpl implements SystemGroupDataService {

    private final SystemGroupDataRepository systemGroupDataRepository;

    private final SystemGroupDataMapper     systemGroupDataMapper;

    public SystemGroupDataServiceImpl(SystemGroupDataRepository systemGroupDataRepository,
                                      SystemGroupDataMapper systemGroupDataMapper) {
        this.systemGroupDataRepository = systemGroupDataRepository;
        this.systemGroupDataMapper = systemGroupDataMapper;
    }

    @Override
    public Map<String, Object> queryAll(SystemGroupDataQueryCriteria criteria, Pageable pageable) {
        Page<SystemGroupData> page = systemGroupDataRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        List<SystemGroupDataDTO> systemGroupDataDTOS = new ArrayList<>();
        for (SystemGroupData systemGroupData : page.getContent()) {

            SystemGroupDataDTO systemGroupDataDTO = systemGroupDataMapper.toDto(systemGroupData);
            systemGroupDataDTO.setMap(JSON.parseObject(systemGroupData.getValue()));
            systemGroupDataDTOS.add(systemGroupDataDTO);
        }
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", systemGroupDataDTOS);
        map.put("totalElements", page.getTotalElements());
        return map;
    }

    @Override
    public List<SystemGroupDataDTO> queryAll(SystemGroupDataQueryCriteria criteria) {
        return systemGroupDataMapper.toDto(systemGroupDataRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public SystemGroupDataDTO findById(Long id) {
        Optional<SystemGroupData> systemGroupData = systemGroupDataRepository.findById(id);
        ValidationUtil.isNull(systemGroupData, "SystemGroupData", "id", id);
        return systemGroupDataMapper.toDto(systemGroupData.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SystemGroupDataDTO create(SystemGroupData resources) {
        return systemGroupDataMapper.toDto(systemGroupDataRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SystemGroupData resources) {
        Optional<SystemGroupData> optionalSystemGroupData = systemGroupDataRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalSystemGroupData, "SystemGroupData", "id", resources.getId());
        SystemGroupData systemGroupData = optionalSystemGroupData.get();
        systemGroupData.copy(resources);
        systemGroupDataRepository.save(systemGroupData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        systemGroupDataRepository.deleteById(id);
    }
}
