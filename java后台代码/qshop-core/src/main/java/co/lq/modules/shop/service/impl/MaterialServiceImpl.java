package co.lq.modules.shop.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.hutool.core.util.IdUtil;
import co.lq.modules.shop.domain.Material;
import co.lq.modules.shop.repository.MaterialRepository;
import co.lq.modules.shop.service.MaterialService;
import co.lq.modules.shop.service.dto.MaterialDto;
import co.lq.modules.shop.service.dto.MaterialQueryCriteria;
import co.lq.modules.shop.service.mapper.MaterialMapper;
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
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;

    private final MaterialMapper     materialMapper;

    public MaterialServiceImpl(MaterialRepository materialRepository, MaterialMapper materialMapper) {
        this.materialRepository = materialRepository;
        this.materialMapper = materialMapper;
    }

    @Override
    public Map<String, Object> queryAll(MaterialQueryCriteria criteria, Pageable pageable) {
        Page<Material> page = materialRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(materialMapper::toDto));
    }

    @Override
    public List<MaterialDto> queryAll(MaterialQueryCriteria criteria) {
        return materialMapper.toDto(materialRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public MaterialDto findById(String id) {
        Material material = materialRepository.findById(id).orElseGet(Material::new);
        ValidationUtil.isNull(material.getId(), "Material", "id", id);
        return materialMapper.toDto(material);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MaterialDto create(Material resources) {
        resources.setId(IdUtil.simpleUUID());
        return materialMapper.toDto(materialRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Material resources) {
        Material material = materialRepository.findById(resources.getId()).orElseGet(Material::new);
        ValidationUtil.isNull(material.getId(), "Material", "id", resources.getId());
        material.copy(resources);
        materialRepository.save(material);
    }

    @Override
    public void deleteAll(String[] ids) {
        for (String id : ids) {
            materialRepository.deleteById(id);
        }
    }

    @Override
    public void deleteById(String id) {
        materialRepository.deleteById(id);
    }
}
