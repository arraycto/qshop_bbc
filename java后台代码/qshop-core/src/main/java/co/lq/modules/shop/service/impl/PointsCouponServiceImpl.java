package co.lq.modules.shop.service.impl;

import co.lq.modules.shop.domain.PointsCoupon;
import co.lq.utils.ValidationUtil;
import co.lq.utils.FileUtil;
import co.lq.modules.shop.repository.PointsCouponRepository;
import co.lq.modules.shop.service.PointsCouponService;
import co.lq.modules.shop.service.dto.PointsCouponDTO;
import co.lq.modules.shop.service.dto.PointsCouponQueryCriteria;
import co.lq.modules.shop.service.mapper.PointsCouponMapper;
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
public class PointsCouponServiceImpl implements PointsCouponService {

    private final PointsCouponRepository pointsCouponRepository;

    private final PointsCouponMapper pointsCouponMapper;

    public PointsCouponServiceImpl(PointsCouponRepository pointsCouponRepository, PointsCouponMapper pointsCouponMapper) {
        this.pointsCouponRepository = pointsCouponRepository;
        this.pointsCouponMapper = pointsCouponMapper;
    }

    @Override
    public Map<String,Object> queryAll(PointsCouponQueryCriteria criteria, Pageable pageable){
        Page<PointsCoupon> page = pointsCouponRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(pointsCouponMapper::toDto));
    }

    @Override
    public List<PointsCouponDTO> queryAll(PointsCouponQueryCriteria criteria){
        return pointsCouponMapper.toDto(pointsCouponRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public PointsCouponDTO findById(Long id) {
        PointsCoupon pointsCoupon = pointsCouponRepository.findById(id).orElseGet(PointsCoupon::new);
        ValidationUtil.isNull(pointsCoupon.getId(),"PointsCoupon","id",id);
        return pointsCouponMapper.toDto(pointsCoupon);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PointsCouponDTO create(PointsCoupon resources) {
        return pointsCouponMapper.toDto(pointsCouponRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PointsCoupon resources) {
        PointsCoupon pointsCoupon = pointsCouponRepository.findById(resources.getId()).orElseGet(PointsCoupon::new);
        ValidationUtil.isNull( pointsCoupon.getId(),"PointsCoupon","id",resources.getId());
        pointsCoupon.copy(resources);
        pointsCouponRepository.save(pointsCoupon);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            pointsCouponRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<PointsCouponDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PointsCouponDTO pointsCoupon : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("有效期(天)", pointsCoupon.getValidDay());
            map.put("最小积分", pointsCoupon.getMinIntegration());
            map.put("最大积分", pointsCoupon.getMaxIntegration());
            map.put("备注", pointsCoupon.getRemark());
            map.put("创建时间", pointsCoupon.getCreateTime());
            map.put("所属店铺", pointsCoupon.getStoreId());
            map.put("添加时间", pointsCoupon.getAddTime());
            map.put("更新时间", pointsCoupon.getModifyTime());
            map.put("逻辑删除", pointsCoupon.getDeleted());
            map.put(" count",  pointsCoupon.getCount());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}