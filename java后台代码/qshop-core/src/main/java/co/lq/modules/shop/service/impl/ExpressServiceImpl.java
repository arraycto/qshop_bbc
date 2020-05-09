package co.lq.modules.shop.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.exception.EntityExistException;
import co.lq.modules.shop.domain.Express;
import co.lq.modules.shop.repository.ExpressRepository;
import co.lq.modules.shop.service.ExpressService;
import co.lq.modules.shop.service.dto.ExpressDTO;
import co.lq.modules.shop.service.dto.ExpressQueryCriteria;
import co.lq.modules.shop.service.mapper.ExpressMapper;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author billy
 * @date 2019-12-12
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ExpressServiceImpl implements ExpressService {

    private final ExpressRepository expressRepository;

    private final ExpressMapper     expressMapper;

    public ExpressServiceImpl(ExpressRepository expressRepository, ExpressMapper expressMapper) {
        this.expressRepository = expressRepository;
        this.expressMapper = expressMapper;
    }

    @Override
    public Map<String, Object> queryAll(ExpressQueryCriteria criteria, Pageable pageable) {
        Page<Express> page = expressRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(expressMapper::toDto));
    }

    @Override
    public List<ExpressDTO> queryAll(ExpressQueryCriteria criteria) {
        return expressMapper.toDto(expressRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public ExpressDTO findById(Integer id) {
        Optional<Express> express = expressRepository.findById(id);
        ValidationUtil.isNull(express, "Express", "id", id);
        return expressMapper.toDto(express.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ExpressDTO create(Express resources) {
        if (expressRepository.findByCode(resources.getCode()) != null) {
            throw new EntityExistException(Express.class, "code", resources.getCode());
        }
        return expressMapper.toDto(expressRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Express resources) {
        Optional<Express> optionalExpress = expressRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalExpress, "Express", "id", resources.getId());
        Express express = optionalExpress.get();
        Express express1 = null;
        express1 = expressRepository.findByCode(resources.getCode());
        if (express1 != null && !express1.getId().equals(express.getId())) {
            throw new EntityExistException(Express.class, "code", resources.getCode());
        }
        express.copy(resources);
        expressRepository.save(express);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        expressRepository.deleteById(id);
    }
}
