package co.lq.modules.shop.service.impl;

import co.lq.modules.shop.domain.SmsContentMsg;
import co.lq.utils.ValidationUtil;
import co.lq.utils.FileUtil;
import co.lq.modules.shop.repository.SmsContentMsgRepository;
import co.lq.modules.shop.service.SmsContentMsgService;
import co.lq.modules.shop.service.dto.SmsContentMsgDTO;
import co.lq.modules.shop.service.dto.SmsContentMsgQueryCriteria;
import co.lq.modules.shop.service.mapper.SmsContentMsgMapper;
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
public class SmsContentMsgServiceImpl implements SmsContentMsgService {

    private final SmsContentMsgRepository smsContentMsgRepository;

    private final SmsContentMsgMapper smsContentMsgMapper;

    public SmsContentMsgServiceImpl(SmsContentMsgRepository smsContentMsgRepository, SmsContentMsgMapper smsContentMsgMapper) {
        this.smsContentMsgRepository = smsContentMsgRepository;
        this.smsContentMsgMapper = smsContentMsgMapper;
    }

    @Override
    public Map<String,Object> queryAll(SmsContentMsgQueryCriteria criteria, Pageable pageable){
        Page<SmsContentMsg> page = smsContentMsgRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(smsContentMsgMapper::toDto));
    }

    @Override
    public List<SmsContentMsgDTO> queryAll(SmsContentMsgQueryCriteria criteria){
        return smsContentMsgMapper.toDto(smsContentMsgRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public SmsContentMsgDTO findById(Long id) {
        SmsContentMsg smsContentMsg = smsContentMsgRepository.findById(id).orElseGet(SmsContentMsg::new);
        ValidationUtil.isNull(smsContentMsg.getId(),"SmsContentMsg","id",id);
        return smsContentMsgMapper.toDto(smsContentMsg);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SmsContentMsgDTO create(SmsContentMsg resources) {
        return smsContentMsgMapper.toDto(smsContentMsgRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SmsContentMsg resources) {
        SmsContentMsg smsContentMsg = smsContentMsgRepository.findById(resources.getId()).orElseGet(SmsContentMsg::new);
        ValidationUtil.isNull( smsContentMsg.getId(),"SmsContentMsg","id",resources.getId());
        smsContentMsg.copy(resources);
        smsContentMsgRepository.save(smsContentMsg);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            smsContentMsgRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<SmsContentMsgDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SmsContentMsgDTO smsContentMsg : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" phone",  smsContentMsg.getPhone());
            map.put(" status",  smsContentMsg.getStatus());
            map.put(" content",  smsContentMsg.getContent());
            map.put(" result",  smsContentMsg.getResult());
            map.put(" contentId",  smsContentMsg.getContentId());
            map.put(" type",  smsContentMsg.getType());
            map.put("所属店铺", smsContentMsg.getStoreId());
            map.put("添加时间", smsContentMsg.getAddTime());
            map.put("更新时间", smsContentMsg.getModifyTime());
            map.put("逻辑删除", smsContentMsg.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}