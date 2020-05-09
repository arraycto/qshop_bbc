package co.lq.modules.shop.service.impl;

import co.lq.modules.shop.domain.SmsContent;
import co.lq.utils.ValidationUtil;
import co.lq.utils.FileUtil;
import co.lq.modules.shop.repository.SmsContentRepository;
import co.lq.modules.shop.service.SmsContentService;
import co.lq.modules.shop.service.dto.SmsContentDTO;
import co.lq.modules.shop.service.dto.SmsContentQueryCriteria;
import co.lq.modules.shop.service.mapper.SmsContentMapper;
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
public class SmsContentServiceImpl implements SmsContentService {

    private final SmsContentRepository smsContentRepository;

    private final SmsContentMapper smsContentMapper;

    public SmsContentServiceImpl(SmsContentRepository smsContentRepository, SmsContentMapper smsContentMapper) {
        this.smsContentRepository = smsContentRepository;
        this.smsContentMapper = smsContentMapper;
    }

    @Override
    public Map<String,Object> queryAll(SmsContentQueryCriteria criteria, Pageable pageable){
        Page<SmsContent> page = smsContentRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(smsContentMapper::toDto));
    }

    @Override
    public List<SmsContentDTO> queryAll(SmsContentQueryCriteria criteria){
        return smsContentMapper.toDto(smsContentRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public SmsContentDTO findById(Long id) {
        SmsContent smsContent = smsContentRepository.findById(id).orElseGet(SmsContent::new);
        ValidationUtil.isNull(smsContent.getId(),"SmsContent","id",id);
        return smsContentMapper.toDto(smsContent);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SmsContentDTO create(SmsContent resources) {
        return smsContentMapper.toDto(smsContentRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SmsContent resources) {
        SmsContent smsContent = smsContentRepository.findById(resources.getId()).orElseGet(SmsContent::new);
        ValidationUtil.isNull( smsContent.getId(),"SmsContent","id",resources.getId());
        smsContent.copy(resources);
        smsContentRepository.save(smsContent);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            smsContentRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<SmsContentDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SmsContentDTO smsContent : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("内容标题", smsContent.getName());
            map.put("消息类型", smsContent.getType());
            map.put("标签说明", smsContent.getMeno());
            map.put("短信模版CODE", smsContent.getCode());
            map.put("内容模版", smsContent.getRemark());
            map.put("所属店铺", smsContent.getStoreId());
            map.put("添加时间", smsContent.getAddTime());
            map.put("更新时间", smsContent.getModifyTime());
            map.put("逻辑删除", smsContent.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}