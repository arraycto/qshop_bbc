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

import co.lq.modules.shop.domain.ProductVertifyRecord;
import co.lq.modules.shop.repository.ProductVertifyRecordRepository;
import co.lq.modules.shop.service.ProductVertifyRecordService;
import co.lq.modules.shop.service.dto.ProductVertifyRecordDTO;
import co.lq.modules.shop.service.dto.ProductVertifyRecordQueryCriteria;
import co.lq.modules.shop.service.mapper.ProductVertifyRecordMapper;
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
 * @date 2020-03-10
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ProductVertifyRecordServiceImpl implements ProductVertifyRecordService {

    private final ProductVertifyRecordRepository productVertifyRecordRepository;

    private final ProductVertifyRecordMapper     productVertifyRecordMapper;

    public ProductVertifyRecordServiceImpl(ProductVertifyRecordRepository productVertifyRecordRepository,
                                           ProductVertifyRecordMapper productVertifyRecordMapper) {
        this.productVertifyRecordRepository = productVertifyRecordRepository;
        this.productVertifyRecordMapper = productVertifyRecordMapper;
    }

    @Override
    public Map<String, Object> queryAll(ProductVertifyRecordQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<ProductVertifyRecord> page = productVertifyRecordRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(productVertifyRecordMapper::toDto));
    }

    @Override
    public List<ProductVertifyRecordDTO> queryAll(ProductVertifyRecordQueryCriteria criteria) {
        criteria.setDeleted(0);
        return productVertifyRecordMapper.toDto(productVertifyRecordRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public ProductVertifyRecordDTO findById(Long id) {
        ProductVertifyRecord productVertifyRecord = productVertifyRecordRepository.findById(id)
                .orElseGet(ProductVertifyRecord::new);
        ValidationUtil.isNull(productVertifyRecord.getId(), "ProductVertifyRecord", "id", id);
        return productVertifyRecordMapper.toDto(productVertifyRecord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductVertifyRecordDTO create(ProductVertifyRecord resources) {
        resources.setDeleted(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setAddTime(timestamp);
        resources.setModifyTime(timestamp);
        return productVertifyRecordMapper.toDto(productVertifyRecordRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProductVertifyRecord resources) {
        ProductVertifyRecord productVertifyRecord = productVertifyRecordRepository.findById(resources.getId())
                .orElseGet(ProductVertifyRecord::new);
        ValidationUtil.isNull(productVertifyRecord.getId(), "ProductVertifyRecord", "id", resources.getId());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setModifyTime(timestamp);
        productVertifyRecord.copy(resources);
        productVertifyRecordRepository.save(productVertifyRecord);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            ProductVertifyRecord productVertifyRecord = productVertifyRecordRepository.findById(id)
                    .orElseGet(ProductVertifyRecord::new);
            productVertifyRecord.setDeleted(1);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            productVertifyRecord.setModifyTime(timestamp);
            productVertifyRecordRepository.save(productVertifyRecord);
        }
    }

    @Override
    public void download(List<ProductVertifyRecordDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ProductVertifyRecordDTO qshopProductVertifyRecord : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("商品id", qshopProductVertifyRecord.getProductId());
            map.put("审核人", qshopProductVertifyRecord.getVertifier());
            map.put("审核状态", qshopProductVertifyRecord.getStatus());
            map.put("反馈详情", qshopProductVertifyRecord.getDetail());
            map.put("所属店铺", qshopProductVertifyRecord.getStoreId());
            map.put("添加时间", qshopProductVertifyRecord.getAddTime());
            map.put("更新时间", qshopProductVertifyRecord.getModifyTime());
            map.put("逻辑删除", qshopProductVertifyRecord.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
