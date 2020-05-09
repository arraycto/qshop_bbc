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

import co.lq.modules.shop.domain.HomeRecommendProduct;
import co.lq.modules.shop.domain.StoreProduct;
import co.lq.modules.shop.repository.HomeRecommendProductRepository;
import co.lq.modules.shop.repository.StoreProductRepository;
import co.lq.modules.shop.service.HomeRecommendProductService;
import co.lq.modules.shop.service.dto.HomeRecommendProductDTO;
import co.lq.modules.shop.service.dto.HomeRecommendProductQueryCriteria;
import co.lq.modules.shop.service.mapper.HomeRecommendProductMapper;
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
public class HomeRecommendProductServiceImpl implements HomeRecommendProductService {

    private final HomeRecommendProductRepository homeRecommendProductRepository;

    private final HomeRecommendProductMapper     homeRecommendProductMapper;

    private final StoreProductRepository         storeProductRepository;

    public HomeRecommendProductServiceImpl(HomeRecommendProductRepository homeRecommendProductRepository,
                                           HomeRecommendProductMapper homeRecommendProductMapper,
                                           StoreProductRepository storeProductRepository) {
        this.homeRecommendProductRepository = homeRecommendProductRepository;
        this.homeRecommendProductMapper = homeRecommendProductMapper;
        this.storeProductRepository = storeProductRepository;
    }

    @Override
    public Map<String, Object> queryAll(HomeRecommendProductQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<HomeRecommendProduct> page = homeRecommendProductRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(homeRecommendProductMapper::toDto));
    }

    @Override
    public List<HomeRecommendProductDTO> queryAll(HomeRecommendProductQueryCriteria criteria) {
        criteria.setDeleted(0);
        return homeRecommendProductMapper.toDto(homeRecommendProductRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public HomeRecommendProductDTO findById(Long id) {
        HomeRecommendProduct homeRecommendProduct = homeRecommendProductRepository.findById(id)
                .orElseGet(HomeRecommendProduct::new);
        ValidationUtil.isNull(homeRecommendProduct.getId(), "HomeRecommendProduct", "id", id);
        return homeRecommendProductMapper.toDto(homeRecommendProduct);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<HomeRecommendProductDTO> create(List<HomeRecommendProduct> resources) {
        List<HomeRecommendProductDTO> homeNewProductDTOList = new ArrayList<>();
        for (HomeRecommendProduct smsHomeNewProduct : resources) {
            smsHomeNewProduct.setRecommendStatus(1);
            smsHomeNewProduct.setSort(0);
            smsHomeNewProduct.setDeleted(0);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            smsHomeNewProduct.setAddTime(timestamp);
            smsHomeNewProduct.setModifyTime(timestamp);
            StoreProduct storeProduct = storeProductRepository.getOne(smsHomeNewProduct.getProductId());
            smsHomeNewProduct.setProductName(storeProduct.getProductName());
            homeNewProductDTOList
                    .add(homeRecommendProductMapper.toDto(homeRecommendProductRepository.save(smsHomeNewProduct)));
        }
        return homeNewProductDTOList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(HomeRecommendProduct resources) {
        HomeRecommendProduct homeRecommendProduct = homeRecommendProductRepository.findById(resources.getId())
                .orElseGet(HomeRecommendProduct::new);
        ValidationUtil.isNull(homeRecommendProduct.getId(), "HomeRecommendProduct", "id", resources.getId());
        homeRecommendProduct.copy(resources);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        homeRecommendProduct.setModifyTime(timestamp);
        homeRecommendProductRepository.save(homeRecommendProduct);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            HomeRecommendProduct homeRecommendProduct = homeRecommendProductRepository.findById(id)
                    .orElseGet(HomeRecommendProduct::new);
            ValidationUtil.isNull(homeRecommendProduct.getId(), "HomeRecommendProduct", "id", id);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            homeRecommendProduct.setModifyTime(timestamp);
            homeRecommendProduct.setDeleted(1);
            homeRecommendProductRepository.save(homeRecommendProduct);
        }
    }

    @Override
    public void download(List<HomeRecommendProductDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (HomeRecommendProductDTO homeRecommendProduct : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(" productId", homeRecommendProduct.getProductId());
            map.put(" productName", homeRecommendProduct.getProductName());
            map.put(" recommendStatus", homeRecommendProduct.getRecommendStatus());
            map.put(" sort", homeRecommendProduct.getSort());
            map.put("所属店铺", homeRecommendProduct.getStoreId());
            map.put("添加时间", homeRecommendProduct.getAddTime());
            map.put("更新时间", homeRecommendProduct.getModifyTime());
            map.put("逻辑删除", homeRecommendProduct.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
