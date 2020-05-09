package co.lq.modules.shop.service.impl;

import co.lq.modules.shop.domain.StoreCatalogRelation;
import co.lq.utils.ValidationUtil;
import co.lq.utils.FileUtil;
import co.lq.modules.shop.repository.StoreCatalogRelationRepository;
import co.lq.modules.shop.service.StoreCatalogRelationService;
import co.lq.modules.shop.service.dto.StoreCatalogRelationDTO;
import co.lq.modules.shop.service.dto.StoreCatalogRelationQueryCriteria;
import co.lq.modules.shop.service.mapper.StoreCatalogRelationMapper;
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
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @author billy
* @date 2020-04-23
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StoreCatalogRelationServiceImpl implements StoreCatalogRelationService {

    private final StoreCatalogRelationRepository storeCatalogRelationRepository;

    private final StoreCatalogRelationMapper storeCatalogRelationMapper;

    public StoreCatalogRelationServiceImpl(StoreCatalogRelationRepository storeCatalogRelationRepository, StoreCatalogRelationMapper storeCatalogRelationMapper) {
        this.storeCatalogRelationRepository = storeCatalogRelationRepository;
        this.storeCatalogRelationMapper = storeCatalogRelationMapper;
    }

    @Override
    public Map<String,Object> queryAll(StoreCatalogRelationQueryCriteria criteria, Pageable pageable){
        Page<StoreCatalogRelation> page = storeCatalogRelationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(storeCatalogRelationMapper::toDto));
    }

    @Override
    public List<StoreCatalogRelationDTO> queryAll(StoreCatalogRelationQueryCriteria criteria){
        return storeCatalogRelationMapper.toDto(storeCatalogRelationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public StoreCatalogRelationDTO findById(Long id) {
        StoreCatalogRelation storeCatalogRelation = storeCatalogRelationRepository.findById(id).orElseGet(StoreCatalogRelation::new);
        ValidationUtil.isNull(storeCatalogRelation.getId(),"StoreCatalogRelation","id",id);
        return storeCatalogRelationMapper.toDto(storeCatalogRelation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreCatalogRelationDTO create(StoreCatalogRelation resources) {
        return storeCatalogRelationMapper.toDto(storeCatalogRelationRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreCatalogRelation resources) {
        StoreCatalogRelation storeCatalogRelation = storeCatalogRelationRepository.findById(resources.getId()).orElseGet(StoreCatalogRelation::new);
        ValidationUtil.isNull( storeCatalogRelation.getId(),"StoreCatalogRelation","id",resources.getId());
        storeCatalogRelation.copy(resources);
        storeCatalogRelationRepository.save(storeCatalogRelation);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            storeCatalogRelationRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<StoreCatalogRelationDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StoreCatalogRelationDTO storeCatalogRelation : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("所属类目", storeCatalogRelation.getCatalogId());
            map.put("添加时间", storeCatalogRelation.getAddTime());
            map.put("更新时间", storeCatalogRelation.getModifyTime());
            map.put("逻辑删除", storeCatalogRelation.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}