package co.lq.modules.shop.service.impl;

import co.lq.modules.shop.domain.PointsSignRule;
import co.lq.utils.ValidationUtil;
import co.lq.utils.FileUtil;
import co.lq.modules.shop.repository.PointsSignRuleRepository;
import co.lq.modules.shop.service.PointsSignRuleService;
import co.lq.modules.shop.service.dto.PointsSignRuleDTO;
import co.lq.modules.shop.service.dto.PointsSignRuleQueryCriteria;
import co.lq.modules.shop.service.mapper.PointsSignRuleMapper;
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
public class PointsSignRuleServiceImpl implements PointsSignRuleService {

    private final PointsSignRuleRepository pointsSignRuleRepository;

    private final PointsSignRuleMapper pointsSignRuleMapper;

    public PointsSignRuleServiceImpl(PointsSignRuleRepository pointsSignRuleRepository, PointsSignRuleMapper pointsSignRuleMapper) {
        this.pointsSignRuleRepository = pointsSignRuleRepository;
        this.pointsSignRuleMapper = pointsSignRuleMapper;
    }

    @Override
    public Map<String,Object> queryAll(PointsSignRuleQueryCriteria criteria, Pageable pageable){
        Page<PointsSignRule> page = pointsSignRuleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(pointsSignRuleMapper::toDto));
    }

    @Override
    public List<PointsSignRuleDTO> queryAll(PointsSignRuleQueryCriteria criteria){
        return pointsSignRuleMapper.toDto(pointsSignRuleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public PointsSignRuleDTO findById(Long id) {
        PointsSignRule pointsSignRule = pointsSignRuleRepository.findById(id).orElseGet(PointsSignRule::new);
        ValidationUtil.isNull(pointsSignRule.getId(),"PointsSignRule","id",id);
        return pointsSignRuleMapper.toDto(pointsSignRule);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PointsSignRuleDTO create(PointsSignRule resources) {
        return pointsSignRuleMapper.toDto(pointsSignRuleRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PointsSignRule resources) {
        PointsSignRule pointsSignRule = pointsSignRuleRepository.findById(resources.getId()).orElseGet(PointsSignRule::new);
        ValidationUtil.isNull( pointsSignRule.getId(),"PointsSignRule","id",resources.getId());
        pointsSignRule.copy(resources);
        pointsSignRuleRepository.save(pointsSignRule);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            pointsSignRuleRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<PointsSignRuleDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PointsSignRuleDTO pointsSignRule : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("连续签到", pointsSignRule.getContineuCount());
            map.put("赠送积分", pointsSignRule.getDonateIntegrtion());
            map.put("所属店铺", pointsSignRule.getStoreId());
            map.put("添加时间", pointsSignRule.getAddTime());
            map.put("更新时间", pointsSignRule.getModifyTime());
            map.put("逻辑删除", pointsSignRule.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}