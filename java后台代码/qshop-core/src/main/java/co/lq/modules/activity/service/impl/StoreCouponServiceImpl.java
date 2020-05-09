package co.lq.modules.activity.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.activity.domain.StoreCoupon;
import co.lq.modules.activity.repository.StoreCouponRepository;
import co.lq.modules.activity.service.StoreCouponService;
import co.lq.modules.activity.service.dto.StoreCouponDTO;
import co.lq.modules.activity.service.dto.StoreCouponQueryCriteria;
import co.lq.modules.activity.service.mapper.StoreCouponMapper;
import co.lq.utils.OrderUtil;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-11-09
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StoreCouponServiceImpl implements StoreCouponService {

    private final StoreCouponRepository storeCouponRepository;

    private final StoreCouponMapper     storeCouponMapper;

    public StoreCouponServiceImpl(StoreCouponRepository storeCouponRepository, StoreCouponMapper storeCouponMapper) {
        this.storeCouponRepository = storeCouponRepository;
        this.storeCouponMapper = storeCouponMapper;
    }

    @Override
    public Map<String, Object> queryAll(StoreCouponQueryCriteria criteria, Pageable pageable) {
        Page<StoreCoupon> page = storeCouponRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(storeCouponMapper::toDto));
    }

    @Override
    public List<StoreCouponDTO> queryAll(StoreCouponQueryCriteria criteria) {
        return storeCouponMapper.toDto(storeCouponRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public StoreCouponDTO findById(Long id) {
        Optional<StoreCoupon> storeCoupon = storeCouponRepository.findById(id);
        ValidationUtil.isNull(storeCoupon, "StoreCoupon", "id", id);
        return storeCouponMapper.toDto(storeCoupon.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreCouponDTO create(StoreCoupon resources) {
        resources.setAddTime(OrderUtil.getSecondTimestampTwo());
        return storeCouponMapper.toDto(storeCouponRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreCoupon resources) {
        Optional<StoreCoupon> optionalStoreCoupon = storeCouponRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalStoreCoupon, "StoreCoupon", "id", resources.getId());
        StoreCoupon storeCoupon = optionalStoreCoupon.get();
        storeCoupon.copy(resources);
        storeCouponRepository.save(storeCoupon);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        storeCouponRepository.deleteById(id);
    }
}
