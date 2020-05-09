package co.lq.modules.shop.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.hutool.core.util.IdUtil;
import co.lq.modules.shop.domain.MaterialGroup;
import co.lq.modules.shop.repository.MaterialGroupRepository;
import co.lq.modules.shop.service.MaterialGroupService;
import co.lq.modules.shop.service.dto.MaterialGroupDto;
import co.lq.modules.shop.service.dto.MaterialGroupQueryCriteria;
import co.lq.modules.shop.service.mapper.MaterialGroupMapper;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

/**
 * @author billy
 * @date 2020-01-09
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MaterialGroupServiceImpl implements MaterialGroupService {

    private final MaterialGroupRepository materialGroupRepository;

    private final MaterialGroupMapper     materialGroupMapper;

    public MaterialGroupServiceImpl(MaterialGroupRepository materialGroupRepository,
                                    MaterialGroupMapper materialGroupMapper) {
        this.materialGroupRepository = materialGroupRepository;
        this.materialGroupMapper = materialGroupMapper;
    }

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(MaterialGroupQueryCriteria criteria, Pageable pageable) {
        Page<MaterialGroup> page = materialGroupRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(materialGroupMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<MaterialGroupDto> queryAll(MaterialGroupQueryCriteria criteria) {
        return materialGroupMapper.toDto(materialGroupRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public MaterialGroupDto findById(String id) {
        MaterialGroup materialGroup = materialGroupRepository.findById(id).orElseGet(MaterialGroup::new);
        ValidationUtil.isNull(materialGroup.getId(), "MaterialGroup", "id", id);
        return materialGroupMapper.toDto(materialGroup);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public MaterialGroupDto create(MaterialGroup resources) {
        resources.setId(IdUtil.simpleUUID());
        return materialGroupMapper.toDto(materialGroupRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(MaterialGroup resources) {
        MaterialGroup materialGroup = materialGroupRepository.findById(resources.getId()).orElseGet(MaterialGroup::new);
        ValidationUtil.isNull(materialGroup.getId(), "MaterialGroup", "id", resources.getId());
        materialGroup.copy(resources);
        materialGroupRepository.save(materialGroup);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(String[] ids) {
        for (String id : ids) {
            materialGroupRepository.deleteById(id);
        }
    }

    @Override
    public void deleteById(String id) {
        materialGroupRepository.deleteById(id);
    }
}
