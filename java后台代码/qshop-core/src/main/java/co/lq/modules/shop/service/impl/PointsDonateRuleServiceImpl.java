package co.lq.modules.shop.service.impl;

import co.lq.modules.shop.domain.PointsDonateRule;
import co.lq.utils.ValidationUtil;
import co.lq.utils.FileUtil;
import co.lq.modules.shop.repository.PointsDonateRuleRepository;
import co.lq.modules.shop.service.PointsDonateRuleService;
import co.lq.modules.shop.service.dto.PointsDonateRuleDTO;
import co.lq.modules.shop.service.dto.PointsDonateRuleQueryCriteria;
import co.lq.modules.shop.service.mapper.PointsDonateRuleMapper;
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
public class PointsDonateRuleServiceImpl implements PointsDonateRuleService {

    private final PointsDonateRuleRepository pointsDonateRuleRepository;

    private final PointsDonateRuleMapper pointsDonateRuleMapper;

    public PointsDonateRuleServiceImpl(PointsDonateRuleRepository pointsDonateRuleRepository, PointsDonateRuleMapper pointsDonateRuleMapper) {
        this.pointsDonateRuleRepository = pointsDonateRuleRepository;
        this.pointsDonateRuleMapper = pointsDonateRuleMapper;
    }

    @Override
    public Map<String,Object> queryAll(PointsDonateRuleQueryCriteria criteria, Pageable pageable){
        Page<PointsDonateRule> page = pointsDonateRuleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(pointsDonateRuleMapper::toDto));
    }

    @Override
    public List<PointsDonateRuleDTO> queryAll(PointsDonateRuleQueryCriteria criteria){
        return pointsDonateRuleMapper.toDto(pointsDonateRuleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public PointsDonateRuleDTO findById(Long id) {
        PointsDonateRule pointsDonateRule = pointsDonateRuleRepository.findById(id).orElseGet(PointsDonateRule::new);
        ValidationUtil.isNull(pointsDonateRule.getId(),"PointsDonateRule","id",id);
        return pointsDonateRuleMapper.toDto(pointsDonateRule);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PointsDonateRuleDTO create(PointsDonateRule resources) {
        return pointsDonateRuleMapper.toDto(pointsDonateRuleRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PointsDonateRule resources) {
        PointsDonateRule pointsDonateRule = pointsDonateRuleRepository.findById(resources.getId()).orElseGet(PointsDonateRule::new);
        ValidationUtil.isNull( pointsDonateRule.getId(),"PointsDonateRule","id",resources.getId());
        pointsDonateRule.copy(resources);
        pointsDonateRuleRepository.save(pointsDonateRule);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            pointsDonateRuleRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<PointsDonateRuleDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PointsDonateRuleDTO pointsDonateRule : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("赠送类别", pointsDonateRule.getDonateType());
            map.put("赠送条件", pointsDonateRule.getDonateCondtion());
            map.put("赠送积分", pointsDonateRule.getDonateIntegration());
            map.put("所属店铺", pointsDonateRule.getStoreId());
            map.put("添加时间", pointsDonateRule.getAddTime());
            map.put("更新时间", pointsDonateRule.getModifyTime());
            map.put("逻辑删除", pointsDonateRule.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}