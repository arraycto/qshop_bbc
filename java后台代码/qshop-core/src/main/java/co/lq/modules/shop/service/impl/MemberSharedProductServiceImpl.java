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

import co.lq.modules.shop.domain.MemberSharedProduct;
import co.lq.modules.shop.repository.MemberSharedProductRepository;
import co.lq.modules.shop.service.MemberSharedProductService;
import co.lq.modules.shop.service.dto.MemberSharedProductDTO;
import co.lq.modules.shop.service.dto.MemberSharedProductQueryCriteria;
import co.lq.modules.shop.service.mapper.MemberSharedProductMapper;
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
 * @date 2020-03-11
 */
@Service
//@CacheConfig(cacheNames = "memberSharedProduct")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MemberSharedProductServiceImpl implements MemberSharedProductService {

    private final MemberSharedProductRepository memberSharedProductRepository;

    private final MemberSharedProductMapper     memberSharedProductMapper;

    public MemberSharedProductServiceImpl(MemberSharedProductRepository memberSharedProductRepository,
                                          MemberSharedProductMapper memberSharedProductMapper) {
        this.memberSharedProductRepository = memberSharedProductRepository;
        this.memberSharedProductMapper = memberSharedProductMapper;
    }

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(MemberSharedProductQueryCriteria criteria, Pageable pageable) {
        criteria.setDeleted(0);
        Page<MemberSharedProduct> page = memberSharedProductRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(memberSharedProductMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<MemberSharedProductDTO> queryAll(MemberSharedProductQueryCriteria criteria) {
        criteria.setDeleted(0);
        return memberSharedProductMapper.toDto(memberSharedProductRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public MemberSharedProductDTO findById(Long id) {
        MemberSharedProduct memberSharedProduct = memberSharedProductRepository.findById(id)
                .orElseGet(MemberSharedProduct::new);
        ValidationUtil.isNull(memberSharedProduct.getId(), "MemberSharedProduct", "id", id);
        return memberSharedProductMapper.toDto(memberSharedProduct);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public MemberSharedProductDTO create(MemberSharedProduct resources) {
        resources.setDeleted(0);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setAddTime(timestamp);
        resources.setModifyTime(timestamp);
        return memberSharedProductMapper.toDto(memberSharedProductRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(MemberSharedProduct resources) {
        MemberSharedProduct memberSharedProduct = memberSharedProductRepository.findById(resources.getId())
                .orElseGet(MemberSharedProduct::new);
        ValidationUtil.isNull(memberSharedProduct.getId(), "MemberSharedProduct", "id", resources.getId());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resources.setModifyTime(timestamp);
        memberSharedProduct.copy(resources);
        memberSharedProductRepository.save(memberSharedProduct);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            MemberSharedProduct memberSharedProduct = memberSharedProductRepository.findById(id)
                    .orElseGet(MemberSharedProduct::new);
            memberSharedProduct.setDeleted(1);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            memberSharedProduct.setModifyTime(timestamp);
            memberSharedProductRepository.save(memberSharedProduct);
        }
    }

    @Override
    public void download(List<MemberSharedProductDTO> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MemberSharedProductDTO memberSharedProduct : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("商品id", memberSharedProduct.getMerId());
            map.put("会员价格", memberSharedProduct.getVipPrice());
            map.put("所属店铺", memberSharedProduct.getStoreId());
            map.put("上下架状态;0-下架,1-上架", memberSharedProduct.getStatus());
            map.put("逻辑删除", memberSharedProduct.getDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        MemberSharedProduct memberSharedProduct = memberSharedProductRepository.findById(id)
                .orElseGet(MemberSharedProduct::new);
        memberSharedProduct.setDeleted(1);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        memberSharedProduct.setModifyTime(timestamp);
        memberSharedProductRepository.save(memberSharedProduct);
    }
}
