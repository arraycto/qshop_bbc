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

import co.lq.modules.shop.domain.HomeAdvertise;
import co.lq.modules.shop.repository.HomeAdvertiseRepository;
import co.lq.modules.shop.service.HomeAdvertiseService;
import co.lq.modules.shop.service.dto.HomeAdvertiseDTO;
import co.lq.modules.shop.service.dto.HomeAdvertiseQueryCriteria;
import co.lq.modules.shop.service.mapper.HomeAdvertiseMapper;
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
 * @date 2020-03-13
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class HomeAdvertiseServiceImpl implements HomeAdvertiseService {

    private final HomeAdvertiseRepository homeAdvertiseRepository;

    private final HomeAdvertiseMapper     homeAdvertiseMapper;

    public HomeAdvertiseServiceImpl(HomeAdvertiseRepository homeAdvertiseRepository,
                                    HomeAdvertiseMapper homeAdvertiseMapper) {
        this.homeAdvertiseRepository = homeAdvertiseRepository;
        this.homeAdvertiseMapper = homeAdvertiseMapper;
    }

    @Override
    public Map<String, Object> queryAll(HomeAdvertiseQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<HomeAdvertise> page = homeAdvertiseRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(homeAdvertiseMapper::toDto));
    }

    @Override
    public List<HomeAdvertiseDTO> queryAll(HomeAdvertiseQueryCriteria criteria) {
        criteria.setDeleted(0);
        return homeAdvertiseMapper.toDto(homeAdvertiseRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public HomeAdvertiseDTO findById(Long id) {
        HomeAdvertise homeAdvertise = homeAdvertiseRepository.findById(id).orElseGet(HomeAdvertise::new);
        ValidationUtil.isNull(homeAdvertise.getId(), "HomeAdvertise", "id", id);
        return homeAdvertiseMapper.toDto(homeAdvertise);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HomeAdvertiseDTO create(HomeAdvertise resources) {
        resources.setDeleted(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setAddTime(timestamp);
        resources.setModifyTime(timestamp);
        return homeAdvertiseMapper.toDto(homeAdvertiseRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(HomeAdvertise resources) {
        HomeAdvertise homeAdvertise = homeAdvertiseRepository.findById(resources.getId()).orElseGet(HomeAdvertise::new);
        ValidationUtil.isNull(homeAdvertise.getId(), "HomeAdvertise", "id", resources.getId());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setModifyTime(timestamp);
        homeAdvertise.copy(resources);
        homeAdvertiseRepository.save(homeAdvertise);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            HomeAdvertise homeAdvertise = homeAdvertiseRepository.findById(id).orElseGet(HomeAdvertise::new);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            homeAdvertise.setModifyTime(timestamp);
            homeAdvertise.setDeleted(1);
            homeAdvertiseRepository.save(homeAdvertise);
        }
    }

    @Override
    public void download(List<HomeAdvertiseDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (HomeAdvertiseDTO homeAdvertise : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(" name", homeAdvertise.getName());
            map.put("轮播位置：0->PC首页轮播；1->app首页轮播, 2-h5、小程序", homeAdvertise.getType());
            map.put(" pic", homeAdvertise.getPic());
            map.put(" startTime", homeAdvertise.getStartTime());
            map.put(" endTime", homeAdvertise.getEndTime());
            map.put("上下线状态：0->下线；1->上线", homeAdvertise.getStatus());
            map.put("点击数", homeAdvertise.getClickCount());
            map.put("下单数", homeAdvertise.getOrderCount());
            map.put("链接地址", homeAdvertise.getUrl());
            map.put("备注", homeAdvertise.getNote());
            map.put("排序", homeAdvertise.getSort());
            map.put("广告所属店铺", homeAdvertise.getStoreId());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
