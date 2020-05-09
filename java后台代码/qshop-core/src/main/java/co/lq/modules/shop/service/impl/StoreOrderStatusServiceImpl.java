package co.lq.modules.shop.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.shop.domain.StoreOrderStatus;
import co.lq.modules.shop.repository.StoreOrderStatusRepository;
import co.lq.modules.shop.service.StoreOrderStatusService;
import co.lq.modules.shop.service.dto.StoreOrderStatusDTO;
import co.lq.modules.shop.service.dto.StoreOrderStatusQueryCriteria;
import co.lq.modules.shop.service.mapper.StoreOrderStatusMapper;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-11-02
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StoreOrderStatusServiceImpl implements StoreOrderStatusService {

    private final StoreOrderStatusRepository storeOrderStatusRepository;

    private final StoreOrderStatusMapper     storeOrderStatusMapper;

    public StoreOrderStatusServiceImpl(StoreOrderStatusRepository storeOrderStatusRepository,
                                       StoreOrderStatusMapper storeOrderStatusMapper) {
        this.storeOrderStatusRepository = storeOrderStatusRepository;
        this.storeOrderStatusMapper = storeOrderStatusMapper;
    }

    @Override
    public Map<String, Object> queryAll(StoreOrderStatusQueryCriteria criteria, Pageable pageable) {
        Page<StoreOrderStatus> page = storeOrderStatusRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(storeOrderStatusMapper::toDto));
    }

    @Override
    public List<StoreOrderStatusDTO> queryAll(StoreOrderStatusQueryCriteria criteria) {
        return storeOrderStatusMapper.toDto(storeOrderStatusRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public StoreOrderStatusDTO findById(Long id) {
        Optional<StoreOrderStatus> storeOrderStatus = storeOrderStatusRepository.findById(id);
        ValidationUtil.isNull(storeOrderStatus, "StoreOrderStatus", "id", id);
        return storeOrderStatusMapper.toDto(storeOrderStatus.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreOrderStatusDTO create(StoreOrderStatus resources) {
        return storeOrderStatusMapper.toDto(storeOrderStatusRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreOrderStatus resources) {
        Optional<StoreOrderStatus> optionalStoreOrderStatus = storeOrderStatusRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalStoreOrderStatus, "StoreOrderStatus", "id", resources.getId());
        StoreOrderStatus storeOrderStatus = optionalStoreOrderStatus.get();
        storeOrderStatus.copy(resources);
        storeOrderStatusRepository.save(storeOrderStatus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        storeOrderStatusRepository.deleteById(id);
    }
}
