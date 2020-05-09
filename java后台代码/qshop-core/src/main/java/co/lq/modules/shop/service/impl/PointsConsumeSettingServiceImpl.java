package co.lq.modules.shop.service.impl;

import co.lq.modules.shop.domain.PointsConsumeSetting;
import co.lq.utils.ValidationUtil;
import co.lq.utils.FileUtil;
import co.lq.modules.shop.repository.PointsConsumeSettingRepository;
import co.lq.modules.shop.service.PointsConsumeSettingService;
import co.lq.modules.shop.service.dto.PointsConsumeSettingDTO;
import co.lq.modules.shop.service.dto.PointsConsumeSettingQueryCriteria;
import co.lq.modules.shop.service.mapper.PointsConsumeSettingMapper;
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
public class PointsConsumeSettingServiceImpl implements PointsConsumeSettingService {

    private final PointsConsumeSettingRepository pointsConsumeSettingRepository;

    private final PointsConsumeSettingMapper pointsConsumeSettingMapper;

    public PointsConsumeSettingServiceImpl(PointsConsumeSettingRepository pointsConsumeSettingRepository, PointsConsumeSettingMapper pointsConsumeSettingMapper) {
        this.pointsConsumeSettingRepository = pointsConsumeSettingRepository;
        this.pointsConsumeSettingMapper = pointsConsumeSettingMapper;
    }

    @Override
    public Map<String,Object> queryAll(PointsConsumeSettingQueryCriteria criteria, Pageable pageable){
        Page<PointsConsumeSetting> page = pointsConsumeSettingRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(pointsConsumeSettingMapper::toDto));
    }

    @Override
    public List<PointsConsumeSettingDTO> queryAll(PointsConsumeSettingQueryCriteria criteria){
        return pointsConsumeSettingMapper.toDto(pointsConsumeSettingRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public PointsConsumeSettingDTO findById(Long id) {
        PointsConsumeSetting pointsConsumeSetting = pointsConsumeSettingRepository.findById(id).orElseGet(PointsConsumeSetting::new);
        ValidationUtil.isNull(pointsConsumeSetting.getId(),"PointsConsumeSetting","id",id);
        return pointsConsumeSettingMapper.toDto(pointsConsumeSetting);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PointsConsumeSettingDTO create(PointsConsumeSetting resources) {
        return pointsConsumeSettingMapper.toDto(pointsConsumeSettingRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PointsConsumeSetting resources) {
        PointsConsumeSetting pointsConsumeSetting = pointsConsumeSettingRepository.findById(resources.getId()).orElseGet(PointsConsumeSetting::new);
        ValidationUtil.isNull( pointsConsumeSetting.getId(),"PointsConsumeSetting","id",resources.getId());
        pointsConsumeSetting.copy(resources);
        pointsConsumeSettingRepository.save(pointsConsumeSetting);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            pointsConsumeSettingRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<PointsConsumeSettingDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PointsConsumeSettingDTO pointsConsumeSetting : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("每一元需要抵扣的积分数量", pointsConsumeSetting.getDeductionPerAmount());
            map.put("每笔订单最高抵用百分比", pointsConsumeSetting.getMaxPercentPerOrder());
            map.put("每次使用积分最小单位100", pointsConsumeSetting.getUseUnit());
            map.put("是否可以和优惠券同用；0->不可以；1->可以", pointsConsumeSetting.getCouponStatus());
            map.put("登录送积分", pointsConsumeSetting.getLogin());
            map.put("注册送积分", pointsConsumeSetting.getRegister());
            map.put("签到送积分", pointsConsumeSetting.getSign());
            map.put("下单 送积分比例", pointsConsumeSetting.getOrders());
            map.put("所属店铺", pointsConsumeSetting.getStoreId());
            map.put("添加时间", pointsConsumeSetting.getAddTime());
            map.put("更新时间", pointsConsumeSetting.getModifyTime());
            map.put("逻辑删除", pointsConsumeSetting.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}