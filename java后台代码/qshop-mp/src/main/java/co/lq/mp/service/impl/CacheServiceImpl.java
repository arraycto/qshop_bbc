package co.lq.mp.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.mp.domain.Cache;
import co.lq.mp.repository.CacheRepository;
import co.lq.mp.service.CacheService;
import co.lq.mp.service.dto.CacheDTO;
import co.lq.mp.service.dto.CacheQueryCriteria;
import co.lq.mp.service.mapper.CacheMapper;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-10-06
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CacheServiceImpl implements CacheService {

    private final CacheRepository cacheRepository;

    private final CacheMapper     cacheMapper;

    public CacheServiceImpl(CacheRepository cacheRepository, CacheMapper cacheMapper) {
        this.cacheRepository = cacheRepository;
        this.cacheMapper = cacheMapper;
    }

    @Override
    public Map<String, Object> queryAll(CacheQueryCriteria criteria, Pageable pageable) {
        Page<Cache> page = cacheRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(cacheMapper::toDto));
    }

    @Override
    public List<CacheDTO> queryAll(CacheQueryCriteria criteria) {
        return cacheMapper.toDto(cacheRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public CacheDTO findById(String key) {
        Optional<Cache> cache = cacheRepository.findById(key);
        ValidationUtil.isNull(cache, "Cache", "key", key);
        return cacheMapper.toDto(cache.get());
    }

    @Override
    public boolean isExist(String key) {
        Optional<Cache> cache = cacheRepository.findById(key);
        if (!cache.isPresent()) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CacheDTO create(Cache resources) {
        //resources.setKey(IdUtil.simpleUUID());
        return cacheMapper.toDto(cacheRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Cache resources) {
        Optional<Cache> optionalCache = cacheRepository.findById(resources.getKey());
        ValidationUtil.isNull(optionalCache, "Cache", "id", resources.getKey());
        Cache cache = optionalCache.get();
        cache.copy(resources);
        cacheRepository.save(cache);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String key) {
        cacheRepository.deleteById(key);
    }
}
