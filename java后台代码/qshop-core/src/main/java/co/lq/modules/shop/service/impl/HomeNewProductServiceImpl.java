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

import co.lq.modules.shop.domain.HomeNewProduct;
import co.lq.modules.shop.domain.StoreProduct;
import co.lq.modules.shop.repository.HomeNewProductRepository;
import co.lq.modules.shop.repository.StoreProductRepository;
import co.lq.modules.shop.service.HomeNewProductService;
import co.lq.modules.shop.service.dto.HomeNewProductDTO;
import co.lq.modules.shop.service.dto.HomeNewProductQueryCriteria;
import co.lq.modules.shop.service.mapper.HomeNewProductMapper;
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
public class HomeNewProductServiceImpl implements HomeNewProductService {

    private final HomeNewProductRepository homeNewProductRepository;

    private final HomeNewProductMapper     homeNewProductMapper;

    private final StoreProductRepository   storeProductRepository;

    public HomeNewProductServiceImpl(HomeNewProductRepository homeNewProductRepository,
                                     StoreProductRepository storeProductRepository,
                                     HomeNewProductMapper homeNewProductMapper) {
        this.homeNewProductRepository = homeNewProductRepository;
        this.homeNewProductMapper = homeNewProductMapper;
        this.storeProductRepository = storeProductRepository;
    }

    @Override
    public Map<String, Object> queryAll(HomeNewProductQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<HomeNewProduct> page = homeNewProductRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(homeNewProductMapper::toDto));
    }

    @Override
    public List<HomeNewProductDTO> queryAll(HomeNewProductQueryCriteria criteria) {
        criteria.setDeleted(0);
        return homeNewProductMapper.toDto(homeNewProductRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public HomeNewProductDTO findById(Long id) {
        HomeNewProduct homeNewProduct = homeNewProductRepository.findById(id).orElseGet(HomeNewProduct::new);
        ValidationUtil.isNull(homeNewProduct.getId(), "HomeNewProduct", "id", id);
        return homeNewProductMapper.toDto(homeNewProduct);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<HomeNewProductDTO> create(List<HomeNewProduct> resources) {
        List<HomeNewProductDTO> homeNewProductDTOList = new ArrayList<>();
        for (HomeNewProduct smsHomeNewProduct : resources) {
            smsHomeNewProduct.setRecommendStatus(1);
            smsHomeNewProduct.setSort(0);
            smsHomeNewProduct.setDeleted(0);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            smsHomeNewProduct.setAddTime(timestamp);
            smsHomeNewProduct.setModifyTime(timestamp);
            StoreProduct storeProduct = storeProductRepository.getOne(smsHomeNewProduct.getProductId());
            smsHomeNewProduct.setProductName(storeProduct.getProductName());
            homeNewProductDTOList.add(homeNewProductMapper.toDto(homeNewProductRepository.save(smsHomeNewProduct)));
        }
        return homeNewProductDTOList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(HomeNewProduct resources) {
        HomeNewProduct homeNewProduct = homeNewProductRepository.findById(resources.getId())
                .orElseGet(HomeNewProduct::new);
        ValidationUtil.isNull(homeNewProduct.getId(), "HomeNewProduct", "id", resources.getId());
        homeNewProduct.copy(resources);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        homeNewProduct.setModifyTime(timestamp);
        homeNewProductRepository.save(homeNewProduct);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            HomeNewProduct homeNewProduct = homeNewProductRepository.findById(id).orElseGet(HomeNewProduct::new);
            ValidationUtil.isNull(homeNewProduct.getId(), "HomeNewProduct", "id", id);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            homeNewProduct.setModifyTime(timestamp);
            homeNewProduct.setDeleted(1);
            homeNewProductRepository.save(homeNewProduct);
        }
    }

    @Override
    public void download(List<HomeNewProductDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (HomeNewProductDTO homeNewProduct : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(" productId", homeNewProduct.getProductId());
            map.put(" productName", homeNewProduct.getProductName());
            map.put(" recommendStatus", homeNewProduct.getRecommendStatus());
            map.put(" sort", homeNewProduct.getSort());
            map.put("添加时间", homeNewProduct.getAddTime());
            map.put("更新时间", homeNewProduct.getModifyTime());
            map.put("逻辑删除", homeNewProduct.getDeleted());
            map.put("所属店铺", homeNewProduct.getStoreId());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
