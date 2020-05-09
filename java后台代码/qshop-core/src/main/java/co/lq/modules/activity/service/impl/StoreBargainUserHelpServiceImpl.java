package co.lq.modules.activity.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.activity.domain.StoreBargainUserHelp;
import co.lq.modules.activity.repository.StoreBargainUserHelpRepository;
import co.lq.modules.activity.service.StoreBargainUserHelpService;
import co.lq.modules.activity.service.dto.StoreBargainUserHelpDTO;
import co.lq.modules.activity.service.dto.StoreBargainUserHelpQueryCriteria;
import co.lq.modules.activity.service.dto.StoreBargainUserQueryCriteria;
import co.lq.modules.activity.service.mapper.StoreBargainUserHelpMapper;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * TODO 类实现描述
 *
 * @author songbin
 * @since 2020年3月22日 下午4:43:01
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StoreBargainUserHelpServiceImpl implements StoreBargainUserHelpService {

    private final StoreBargainUserHelpRepository storeBargainUserHelpRepository;
    private final StoreBargainUserHelpMapper     bargainUserHelpMapper;

    public StoreBargainUserHelpServiceImpl(StoreBargainUserHelpRepository storeBargainUserHelpRepository,
                                           StoreBargainUserHelpMapper bargainUserHelpMapper) {
        this.storeBargainUserHelpRepository = storeBargainUserHelpRepository;
        this.bargainUserHelpMapper = bargainUserHelpMapper;
    }

    @Override
    public Map<String, Object> queryAll(StoreBargainUserHelpQueryCriteria criteria, Pageable pageable) {
        Page<StoreBargainUserHelp> page = storeBargainUserHelpRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(bargainUserHelpMapper::toDto));
    }

    @Override
    public List<StoreBargainUserHelpDTO> queryAll(StoreBargainUserQueryCriteria criteria) {
        return bargainUserHelpMapper.toDto(storeBargainUserHelpRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public StoreBargainUserHelpDTO findById(Long id) {
        Optional<StoreBargainUserHelp> storeBargainUserHelp = storeBargainUserHelpRepository.findById(id);
        ValidationUtil.isNull(storeBargainUserHelp, "StoreBargainUserHelp", "id", id);
        return bargainUserHelpMapper.toDto(storeBargainUserHelp.get());
    }

    @Override
    public StoreBargainUserHelpDTO create(StoreBargainUserHelp resources) {
        return bargainUserHelpMapper.toDto(storeBargainUserHelpRepository.save(resources));
    }

    @Override
    public void update(StoreBargainUserHelp resources) {
        Optional<StoreBargainUserHelp> optionalStoreBargainUserHelp = storeBargainUserHelpRepository
                .findById(resources.getId());
        ValidationUtil.isNull(optionalStoreBargainUserHelp, "StoreBargainUserHelp", "id", resources.getId());
        StoreBargainUserHelp storeBargainUserHelp = optionalStoreBargainUserHelp.get();
        storeBargainUserHelp.copy(resources);
        storeBargainUserHelpRepository.save(storeBargainUserHelp);
    }

    @Override
    public void delete(Long id) {
        storeBargainUserHelpRepository.deleteById(id);
    }
}
