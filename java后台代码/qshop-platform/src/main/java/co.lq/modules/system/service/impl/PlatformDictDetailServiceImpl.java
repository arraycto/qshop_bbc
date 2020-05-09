package co.lq.modules.system.service.impl;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.modules.system.domain.DictDetail;
import co.lq.modules.system.repository.PlatformDictDetailRepository;
import co.lq.modules.system.service.PlatformDictDetailService;
import co.lq.modules.system.service.dto.DictDetailDTO;
import co.lq.modules.system.service.dto.DictDetailQueryCriteria;
import co.lq.modules.system.service.mapper.DictDetailMapper;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-04-10
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PlatformDictDetailServiceImpl implements PlatformDictDetailService {

    private final PlatformDictDetailRepository platformDictDetailRepository;

    private final DictDetailMapper             dictDetailMapper;

    public PlatformDictDetailServiceImpl(PlatformDictDetailRepository platformDictDetailRepository,
                                         DictDetailMapper dictDetailMapper) {
        this.platformDictDetailRepository = platformDictDetailRepository;
        this.dictDetailMapper = dictDetailMapper;
    }

    @Override
    public Map<String, Object> queryAll(DictDetailQueryCriteria criteria, Pageable pageable) {
        Page<DictDetail> page = platformDictDetailRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(dictDetailMapper::toDto));
    }

    @Override
    public DictDetailDTO findById(Long id) {
        DictDetail dictDetail = platformDictDetailRepository.findById(id).orElseGet(DictDetail::new);
        ValidationUtil.isNull(dictDetail.getId(), "DictDetail", "id", id);
        return dictDetailMapper.toDto(dictDetail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictDetailDTO create(DictDetail resources) {
        return dictDetailMapper.toDto(platformDictDetailRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DictDetail resources) {
        DictDetail dictDetail = platformDictDetailRepository.findById(resources.getId()).orElseGet(DictDetail::new);
        ValidationUtil.isNull(dictDetail.getId(), "DictDetail", "id", resources.getId());
        resources.setId(dictDetail.getId());
        platformDictDetailRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        platformDictDetailRepository.deleteById(id);
    }
}
