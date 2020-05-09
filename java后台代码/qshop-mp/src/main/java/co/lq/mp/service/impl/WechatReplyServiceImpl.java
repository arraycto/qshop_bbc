package co.lq.mp.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.lq.exception.EntityExistException;
import co.lq.mp.domain.WechatReply;
import co.lq.mp.repository.WechatReplyRepository;
import co.lq.mp.service.WechatReplyService;
import co.lq.mp.service.dto.WechatReplyDTO;
import co.lq.mp.service.dto.WechatReplyQueryCriteria;
import co.lq.mp.service.mapper.WechatReplyMapper;
import co.lq.utils.PageUtil;
import co.lq.utils.QueryHelp;
import co.lq.utils.ValidationUtil;

/**
 * @author billy
 * @date 2019-10-10
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WechatReplyServiceImpl implements WechatReplyService {

    private final WechatReplyRepository wechatReplyRepository;

    private final WechatReplyMapper     wechatReplyMapper;

    public WechatReplyServiceImpl(WechatReplyRepository wechatReplyRepository, WechatReplyMapper wechatReplyMapper) {
        this.wechatReplyRepository = wechatReplyRepository;
        this.wechatReplyMapper = wechatReplyMapper;
    }

    @Override
    public Map<String, Object> queryAll(WechatReplyQueryCriteria criteria, Pageable pageable) {
        Page<WechatReply> page = wechatReplyRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(wechatReplyMapper::toDto));
    }

    @Override
    public List<WechatReplyDTO> queryAll(WechatReplyQueryCriteria criteria) {
        return wechatReplyMapper.toDto(wechatReplyRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public WechatReplyDTO findById(Integer id) {
        Optional<WechatReply> wechatReply = wechatReplyRepository.findById(id);
        ValidationUtil.isNull(wechatReply, "WechatReply", "id", id);
        return wechatReplyMapper.toDto(wechatReply.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WechatReplyDTO create(WechatReply resources) {
        if (wechatReplyRepository.findByKey(resources.getKey()) != null) {
            throw new EntityExistException(WechatReply.class, "key", resources.getKey());
        }
        return wechatReplyMapper.toDto(wechatReplyRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(WechatReply resources) {
        Optional<WechatReply> optionalWechatReply = wechatReplyRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalWechatReply, "WechatReply", "id", resources.getId());
        WechatReply wechatReply = optionalWechatReply.get();
        WechatReply wechatReply1 = null;
        wechatReply1 = wechatReplyRepository.findByKey(resources.getKey());
        if (wechatReply1 != null && !wechatReply1.getId().equals(wechatReply.getId())) {
            throw new EntityExistException(WechatReply.class, "key", resources.getKey());
        }
        wechatReply.copy(resources);
        wechatReplyRepository.save(wechatReply);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        wechatReplyRepository.deleteById(id);
    }

    @Override
    public WechatReply isExist(String key) {
        WechatReply wechatReply = wechatReplyRepository.findByKey(key);

        return wechatReply;
    }
}
