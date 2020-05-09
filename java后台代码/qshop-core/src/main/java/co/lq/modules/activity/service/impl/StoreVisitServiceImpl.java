package co.lq.modules.activity.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.activity.domain.StoreVisit;
import co.lq.modules.activity.repository.StoreVisitRepository;
import co.lq.modules.activity.service.StoreVisitService;
import co.lq.modules.activity.service.dto.StoreVisitDTO;
import co.lq.modules.activity.service.dto.StoreVisitQueryCriteria;
import co.lq.modules.activity.service.mapper.StoreVisitMapper;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-11-18
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StoreVisitServiceImpl implements StoreVisitService {

    private final StoreVisitRepository storeVisitRepository;

    private final StoreVisitMapper     storeVisitMapper;

    public StoreVisitServiceImpl(StoreVisitRepository storeVisitRepository, StoreVisitMapper storeVisitMapper) {
        this.storeVisitRepository = storeVisitRepository;
        this.storeVisitMapper = storeVisitMapper;
    }

    @Override
    public Map<String, Object> queryAll(StoreVisitQueryCriteria criteria, Pageable pageable) {
        Page<StoreVisit> page = storeVisitRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(storeVisitMapper::toDto));
    }

    @Override
    public List<StoreVisitDTO> queryAll(StoreVisitQueryCriteria criteria) {
        return storeVisitMapper.toDto(storeVisitRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public StoreVisitDTO findById(Long id) {
        Optional<StoreVisit> storeVisit = storeVisitRepository.findById(id);
        ValidationUtil.isNull(storeVisit, "StoreVisit", "id", id);
        return storeVisitMapper.toDto(storeVisit.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreVisitDTO create(StoreVisit resources) {
        return storeVisitMapper.toDto(storeVisitRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StoreVisit resources) {
        Optional<StoreVisit> optionalStoreVisit = storeVisitRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalStoreVisit, "StoreVisit", "id", resources.getId());
        StoreVisit storeVisit = optionalStoreVisit.get();
        storeVisit.copy(resources);
        storeVisitRepository.save(storeVisit);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        storeVisitRepository.deleteById(id);
    }
}
