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

import co.lq.modules.shop.domain.CompanyAddress;
import co.lq.modules.shop.repository.CompanyAddressRepository;
import co.lq.modules.shop.service.CompanyAddressService;
import co.lq.modules.shop.service.dto.CompanyAddressDTO;
import co.lq.modules.shop.service.dto.CompanyAddressQueryCriteria;
import co.lq.modules.shop.service.mapper.CompanyAddressMapper;
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
public class CompanyAddressServiceImpl implements CompanyAddressService {

    private final CompanyAddressRepository companyAddressRepository;

    private final CompanyAddressMapper     companyAddressMapper;

    public CompanyAddressServiceImpl(CompanyAddressRepository companyAddressRepository,
                                     CompanyAddressMapper companyAddressMapper) {
        this.companyAddressRepository = companyAddressRepository;
        this.companyAddressMapper = companyAddressMapper;
    }

    @Override
    public Map<String, Object> queryAll(CompanyAddressQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<CompanyAddress> page = companyAddressRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(companyAddressMapper::toDto));
    }

    @Override
    public List<CompanyAddressDTO> queryAll(CompanyAddressQueryCriteria criteria) {
        criteria.setDeleted(0);
        return companyAddressMapper.toDto(companyAddressRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public CompanyAddressDTO findById(Long id) {
        CompanyAddress companyAddress = companyAddressRepository.findById(id).orElseGet(CompanyAddress::new);
        ValidationUtil.isNull(companyAddress.getId(), "CompanyAddress", "id", id);
        return companyAddressMapper.toDto(companyAddress);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompanyAddressDTO create(CompanyAddress resources) {
        resources.setDeleted(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setAddTime(timestamp);
        resources.setModifyTime(timestamp);
        return companyAddressMapper.toDto(companyAddressRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CompanyAddress resources) {
        CompanyAddress companyAddress = companyAddressRepository.findById(resources.getId())
                .orElseGet(CompanyAddress::new);
        ValidationUtil.isNull(companyAddress.getId(), "CompanyAddress", "id", resources.getId());
        companyAddress.copy(resources);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setModifyTime(timestamp);
        companyAddressRepository.save(companyAddress);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            CompanyAddress companyAddress = companyAddressRepository.findById(id).orElseGet(CompanyAddress::new);
            ValidationUtil.isNull(companyAddress.getId(), "CompanyAddress", "id", id);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            companyAddress.setModifyTime(timestamp);
            companyAddress.setDeleted(1);
            companyAddressRepository.save(companyAddress);
        }
    }

    @Override
    public void download(List<CompanyAddressDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CompanyAddressDTO companyAddress : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("地址名称", companyAddress.getAddressName());
            map.put("默认发货地址：0->否；1->是", companyAddress.getSendStatus());
            map.put("是否默认收货地址：0->否；1->是", companyAddress.getReceiveStatus());
            map.put("收发货人姓名", companyAddress.getName());
            map.put("收货人电话", companyAddress.getPhone());
            map.put("省/直辖市", companyAddress.getProvince());
            map.put("市", companyAddress.getCity());
            map.put("区", companyAddress.getRegion());
            map.put("详细地址", companyAddress.getDetailAddress());
            map.put("所属店铺", companyAddress.getStoreId());
            map.put("添加时间", companyAddress.getAddTime());
            map.put("更新时间", companyAddress.getModifyTime());
            map.put("逻辑删除", companyAddress.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
