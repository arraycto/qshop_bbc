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

import co.lq.modules.shop.domain.FeightTemplate;
import co.lq.modules.shop.repository.FeightTemplateRepository;
import co.lq.modules.shop.service.FeightTemplateService;
import co.lq.modules.shop.service.dto.FeightTemplateDTO;
import co.lq.modules.shop.service.dto.FeightTemplateQueryCriteria;
import co.lq.modules.shop.service.mapper.FeightTemplateMapper;
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
 * @date 2020-03-27
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FeightTemplateServiceImpl implements FeightTemplateService {

    private final FeightTemplateRepository feightTemplateRepository;

    private final FeightTemplateMapper     feightTemplateMapper;

    public FeightTemplateServiceImpl(FeightTemplateRepository feightTemplateRepository,
                                     FeightTemplateMapper feightTemplateMapper) {
        this.feightTemplateRepository = feightTemplateRepository;
        this.feightTemplateMapper = feightTemplateMapper;
    }

    @Override
    public Map<String, Object> queryAll(FeightTemplateQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<FeightTemplate> page = feightTemplateRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(feightTemplateMapper::toDto));
    }

    @Override
    public List<FeightTemplateDTO> queryAll(FeightTemplateQueryCriteria criteria) {
        criteria.setDeleted(0);
        return feightTemplateMapper.toDto(feightTemplateRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public FeightTemplateDTO findById(Long id) {
        FeightTemplate feightTemplate = feightTemplateRepository.findById(id).orElseGet(FeightTemplate::new);
        ValidationUtil.isNull(feightTemplate.getId(), "FeightTemplate", "id", id);
        return feightTemplateMapper.toDto(feightTemplate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FeightTemplateDTO create(FeightTemplate resources) {
        resources.setDeleted(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setAddTime(timestamp);
        resources.setModifyTime(timestamp);
        return feightTemplateMapper.toDto(feightTemplateRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FeightTemplate resources) {
        FeightTemplate feightTemplate = feightTemplateRepository.findById(resources.getId())
                .orElseGet(FeightTemplate::new);
        ValidationUtil.isNull(feightTemplate.getId(), "FeightTemplate", "id", resources.getId());
        feightTemplate.copy(resources);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        feightTemplate.setModifyTime(timestamp);
        feightTemplateRepository.save(feightTemplate);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            FeightTemplate feightTemplate = feightTemplateRepository.findById(id).orElseGet(FeightTemplate::new);
            ValidationUtil.isNull(feightTemplate.getId(), "FeightTemplate", "id", id);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            feightTemplate.setModifyTime(timestamp);
            feightTemplate.setDeleted(1);
            feightTemplateRepository.save(feightTemplate);
        }
    }

    @Override
    public void download(List<FeightTemplateDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (FeightTemplateDTO feightTemplate : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("名称", feightTemplate.getName());
            map.put("计费类型:0->按重量；1->按件数", feightTemplate.getChargeType());
            map.put("首重kg", feightTemplate.getFirstWeight());
            map.put("首费（元）", feightTemplate.getFirstFee());
            map.put("后重量", feightTemplate.getContinueWeight());
            map.put("后费用", feightTemplate.getContinmeFee());
            map.put("目的地（省、市）", feightTemplate.getDest());
            map.put("所属店铺", feightTemplate.getStoreId());
            map.put("添加时间", feightTemplate.getAddTime());
            map.put("更新时间", feightTemplate.getModifyTime());
            map.put("逻辑删除", feightTemplate.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
