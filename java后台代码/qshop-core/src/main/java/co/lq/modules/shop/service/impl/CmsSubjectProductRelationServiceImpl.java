package co.lq.modules.shop.service.impl;

import co.lq.modules.shop.domain.CmsSubjectProductRelation;
import co.lq.utils.ValidationUtil;
import co.lq.utils.FileUtil;
import co.lq.modules.shop.repository.CmsSubjectProductRelationRepository;
import co.lq.modules.shop.service.CmsSubjectProductRelationService;
import co.lq.modules.shop.service.dto.CmsSubjectProductRelationDTO;
import co.lq.modules.shop.service.dto.CmsSubjectProductRelationQueryCriteria;
import co.lq.modules.shop.service.mapper.CmsSubjectProductRelationMapper;
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
* @date 2020-03-27
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CmsSubjectProductRelationServiceImpl implements CmsSubjectProductRelationService {

    private final CmsSubjectProductRelationRepository cmsSubjectProductRelationRepository;

    private final CmsSubjectProductRelationMapper cmsSubjectProductRelationMapper;

    public CmsSubjectProductRelationServiceImpl(CmsSubjectProductRelationRepository cmsSubjectProductRelationRepository, CmsSubjectProductRelationMapper cmsSubjectProductRelationMapper) {
        this.cmsSubjectProductRelationRepository = cmsSubjectProductRelationRepository;
        this.cmsSubjectProductRelationMapper = cmsSubjectProductRelationMapper;
    }

    @Override
    public Map<String,Object> queryAll(CmsSubjectProductRelationQueryCriteria criteria, Pageable pageable){
        Page<CmsSubjectProductRelation> page = cmsSubjectProductRelationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(cmsSubjectProductRelationMapper::toDto));
    }

    @Override
    public List<CmsSubjectProductRelationDTO> queryAll(CmsSubjectProductRelationQueryCriteria criteria){
        return cmsSubjectProductRelationMapper.toDto(cmsSubjectProductRelationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public CmsSubjectProductRelationDTO findById(Long id) {
        CmsSubjectProductRelation cmsSubjectProductRelation = cmsSubjectProductRelationRepository.findById(id).orElseGet(CmsSubjectProductRelation::new);
        ValidationUtil.isNull(cmsSubjectProductRelation.getId(),"CmsSubjectProductRelation","id",id);
        return cmsSubjectProductRelationMapper.toDto(cmsSubjectProductRelation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CmsSubjectProductRelationDTO create(CmsSubjectProductRelation resources) {
        return cmsSubjectProductRelationMapper.toDto(cmsSubjectProductRelationRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CmsSubjectProductRelation resources) {
        CmsSubjectProductRelation cmsSubjectProductRelation = cmsSubjectProductRelationRepository.findById(resources.getId()).orElseGet(CmsSubjectProductRelation::new);
        ValidationUtil.isNull( cmsSubjectProductRelation.getId(),"CmsSubjectProductRelation","id",resources.getId());
        cmsSubjectProductRelation.copy(resources);
        cmsSubjectProductRelationRepository.save(cmsSubjectProductRelation);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            cmsSubjectProductRelationRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<CmsSubjectProductRelationDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CmsSubjectProductRelationDTO cmsSubjectProductRelation : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" subjectId",  cmsSubjectProductRelation.getSubjectId());
            map.put(" productId",  cmsSubjectProductRelation.getProductId());
            map.put("所属店铺", cmsSubjectProductRelation.getStoreId());
            map.put("添加时间", cmsSubjectProductRelation.getAddTime());
            map.put("更新时间", cmsSubjectProductRelation.getModifyTime());
            map.put("逻辑删除", cmsSubjectProductRelation.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}