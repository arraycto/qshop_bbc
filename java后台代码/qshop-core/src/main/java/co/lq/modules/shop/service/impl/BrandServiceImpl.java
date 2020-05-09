package co.lq.modules.shop.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.shop.domain.Brand;
import co.lq.modules.shop.domain.Catalog;
import co.lq.modules.shop.repository.BrandRepository;
import co.lq.modules.shop.repository.CatalogRepository;
import co.lq.modules.shop.service.BrandService;
import co.lq.modules.shop.service.dto.BrandDTO;
import co.lq.modules.shop.service.dto.BrandQueryCriteria;
import co.lq.modules.shop.service.mapper.BrandMapper;
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
public class BrandServiceImpl implements BrandService {

    private final BrandRepository   brandRepository;
    private final CatalogRepository catalogRepository;

    private final BrandMapper       brandMapper;

    public BrandServiceImpl(BrandRepository brandRepository, CatalogRepository catalogRepository,
                            BrandMapper brandMapper) {

        this.brandRepository = brandRepository;
        this.catalogRepository = catalogRepository;
        this.brandMapper = brandMapper;
    }

    @Override
    public Map<String, Object> queryAll(BrandQueryCriteria criteria, Pageable pageable) {
        Page<Brand> page = brandRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(brandMapper::toDto));
    }

    @Override
    public List<BrandDTO> queryAll(BrandQueryCriteria criteria) {
        return brandMapper.toDto(brandRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public BrandDTO findById(Long id) {
        Brand brand = brandRepository.findById(id).orElseGet(Brand::new);
        ValidationUtil.isNull(brand.getId(), "Brand", "id", id);
        BrandDTO brandDTO = brandMapper.toDto(brand);
        List<Long> catalogIdList = new ArrayList<>();
        if (brandDTO.getCatalogId() != null && brandDTO.getCatalogId() > 0) {
            catalogIdList.add(brandDTO.getCatalogId());
            Optional<Catalog> optional = catalogRepository.findById(brandDTO.getCatalogId());
            Catalog catalog = optional.get();
            while (catalog.getPid() > 0) {
                catalogIdList.add(catalog.getPid());
                optional = catalogRepository.findById(brandDTO.getCatalogId());
                catalog = optional.get();
            }
            Collections.reverse(catalogIdList);
        }
        brandDTO.setCatalogIdList(catalogIdList);

        return brandDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BrandDTO create(Brand resources) {
        resources.setDeleted(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setAddTime(timestamp);
        resources.setModifyTime(timestamp);
        return brandMapper.toDto(brandRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Brand resources) {
        Brand brand = brandRepository.findById(resources.getId()).orElseGet(Brand::new);
        ValidationUtil.isNull(brand.getId(), "Brand", "id", resources.getId());
        brand.copy(resources);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        brand.setModifyTime(timestamp);
        brandRepository.save(brand);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            Brand Brand = brandRepository.findById(id).orElseGet(Brand::new);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Brand.setModifyTime(timestamp);
            brandRepository.save(Brand);
        }
    }

    @Override
    public void download(List<BrandDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (BrandDTO brand : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("品牌名称", brand.getName());
            map.put("首字母", brand.getFirstLetter());
            map.put("排序", brand.getSort());
            map.put("品牌制造商", brand.getFactory());
            map.put("显示", brand.getShowStatus());
            map.put("产品数量", brand.getProductCount());
            map.put("品牌logo", brand.getLogo());
            map.put("专区大图", brand.getBigPic());
            map.put("品牌故事", brand.getBrandStory());
            map.put("添加时间", brand.getAddTime());
            map.put("更新时间", brand.getModifyTime());
            map.put("逻辑删除", brand.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
