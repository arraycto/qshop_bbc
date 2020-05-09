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

import co.lq.modules.shop.domain.StoreBrand;
import co.lq.modules.shop.repository.StoreBrandRepository;
import co.lq.modules.shop.service.StoreBrandService;
import co.lq.modules.shop.service.dto.StoreBrandDTO;
import co.lq.modules.shop.service.dto.StoreBrandQueryCriteria;
import co.lq.modules.shop.service.mapper.StoreBrandMapper;
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
public class StoreBrandServiceImpl implements StoreBrandService {

    private final StoreBrandRepository storeBrandRepository;

    private final StoreBrandMapper     storeBrandMapper;

    public StoreBrandServiceImpl(StoreBrandRepository storeBrandRepository, StoreBrandMapper storeBrandMapper) {
        this.storeBrandRepository = storeBrandRepository;
        this.storeBrandMapper = storeBrandMapper;
    }

    @Override
    public Map<String, Object> queryAll(StoreBrandQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<StoreBrand> page = storeBrandRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(storeBrandMapper::toDto));
    }

    @Override
    public List<StoreBrandDTO> queryAll(StoreBrandQueryCriteria criteria) {
        criteria.setDeleted(0);
        return storeBrandMapper.toDto(storeBrandRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public StoreBrandDTO findById(Long id) {
        StoreBrand storeBrand = storeBrandRepository.findById(id).orElseGet(StoreBrand::new);
        ValidationUtil.isNull(storeBrand.getId(), "StoreBrand", "id", id);
        return storeBrandMapper.toDto(storeBrand);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreBrandDTO create(StoreBrand resources) {
        resources.setDeleted(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setAddTime(timestamp);
        resources.setModifyTime(timestamp);
        return storeBrandMapper.toDto(storeBrandRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreBrand resources) {
        StoreBrand storeBrand = storeBrandRepository.findById(resources.getId()).orElseGet(StoreBrand::new);
        ValidationUtil.isNull(storeBrand.getId(), "StoreBrand", "id", resources.getId());
        storeBrand.copy(resources);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        storeBrand.setModifyTime(timestamp);
        storeBrandRepository.save(storeBrand);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            StoreBrand storeBrand = storeBrandRepository.findById(id).orElseGet(StoreBrand::new);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            storeBrand.setModifyTime(timestamp);
            storeBrand.setDeleted(1);
            storeBrandRepository.save(storeBrand);
        }
    }

    @Override
    public void download(List<StoreBrandDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StoreBrandDTO storeBrand : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("品牌名称", storeBrand.getName());
            map.put("首字母", storeBrand.getFirstLetter());
            map.put("排序", storeBrand.getSort());
            map.put("品牌制造商", storeBrand.getFactory());
            map.put("显示", storeBrand.getShowStatus());
            map.put("产品数量", storeBrand.getProductCount());
            map.put("品牌logo", storeBrand.getLogo());
            map.put("专区大图", storeBrand.getBigPic());
            map.put("品牌故事", storeBrand.getBrandStory());
            map.put("所属店铺", storeBrand.getShopName());
            map.put("添加时间", storeBrand.getAddTime());
            map.put("更新时间", storeBrand.getModifyTime());
            map.put("逻辑删除", storeBrand.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
