package co.lq.mp.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.mp.domain.WechatTemplate;
import co.lq.mp.repository.WechatTemplateRepository;
import co.lq.mp.service.WechatTemplateService;
import co.lq.mp.service.dto.WechatTemplateDTO;
import co.lq.mp.service.dto.WechatTemplateQueryCriteria;
import co.lq.mp.service.mapper.WechatTemplateMapper;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-12-10
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WechatTemplateServiceImpl implements WechatTemplateService {

    private final WechatTemplateRepository wechatTemplateRepository;

    private final WechatTemplateMapper     wechatTemplateMapper;

    public WechatTemplateServiceImpl(WechatTemplateRepository wechatTemplateRepository,
                                     WechatTemplateMapper wechatTemplateMapper) {
        this.wechatTemplateRepository = wechatTemplateRepository;
        this.wechatTemplateMapper = wechatTemplateMapper;
    }

    @Override
    public WechatTemplate findByTempkey(String key) {
        return wechatTemplateRepository.findByTempkey(key);
    }

    @Override
    public Map<String, Object> queryAll(WechatTemplateQueryCriteria criteria, Pageable pageable) {
        Page<WechatTemplate> page = wechatTemplateRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(wechatTemplateMapper::toDto));
    }

    @Override
    public List<WechatTemplateDTO> queryAll(WechatTemplateQueryCriteria criteria) {
        return wechatTemplateMapper.toDto(wechatTemplateRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public WechatTemplateDTO findById(Integer id) {
        Optional<WechatTemplate> wechatTemplate = wechatTemplateRepository.findById(id);
        ValidationUtil.isNull(wechatTemplate, "WechatTemplate", "id", id);
        return wechatTemplateMapper.toDto(wechatTemplate.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WechatTemplateDTO create(WechatTemplate resources) {
        return wechatTemplateMapper.toDto(wechatTemplateRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(WechatTemplate resources) {
        Optional<WechatTemplate> optionalWechatTemplate = wechatTemplateRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalWechatTemplate, "WechatTemplate", "id", resources.getId());
        WechatTemplate wechatTemplate = optionalWechatTemplate.get();
        wechatTemplate.copy(resources);
        wechatTemplateRepository.save(wechatTemplate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        wechatTemplateRepository.deleteById(id);
    }
}
